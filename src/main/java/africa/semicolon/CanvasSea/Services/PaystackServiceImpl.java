package africa.semicolon.CanvasSea.Services;

import africa.semicolon.CanvasSea.DTOs.Request.CreatePlanRequest;
import africa.semicolon.CanvasSea.DTOs.Request.InitializePaymentRequest;
import africa.semicolon.CanvasSea.DTOs.Response.CreatePlanResponse;
import africa.semicolon.CanvasSea.DTOs.Response.InitializePaymentResponse;
import africa.semicolon.CanvasSea.DTOs.Response.PaymentVerificationResponse;
import africa.semicolon.CanvasSea.Data.Model.PaymentPaystack;
import africa.semicolon.CanvasSea.Data.Model.PricingPlanType;
import africa.semicolon.CanvasSea.Data.Repository.ArtistRepository;
import africa.semicolon.CanvasSea.Data.Repository.PaystackPaymentRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Date;

@AllArgsConstructor
@Builder
public class PaystackServiceImpl implements PaystackService {

    private static final String PAYSTACK_INIT = "https://api.paystack.co/plan";
    private static final String PAYSTACK_INITIALIZE_PAY = "https://api.paystack.co/transaction/initialize";
    private static final String PAYSTACK_VERIFY = "https://api.paystack.co/transaction/verify/";
    private static final int STATUS_CODE_CREATED = 201;
    private static final int STATUS_CODE_OK = 200;

    private String paystackSecretKey;

    private ArtistRepository artistRepository;

    private PaystackPaymentRepository paystackPaymentRepository;

    public void PaystackService(String paystackSecretKey) {
        this.paystackSecretKey = paystackSecretKey;
    }

    public CreatePlanResponse createPlanResponse(CreatePlanRequest createPlanRequest) throws RuntimeException {
        CreatePlanResponse createPlanResponse = null;

        try {
            Gson gson = new Gson();
            StringEntity postingString = new StringEntity(gson.toJson(createPlanRequest));
            HttpClient client = HttpClientBuilder.create().build();
            HttpPost post = new HttpPost(PAYSTACK_INIT);
            post.setEntity(postingString);
            post.addHeader("Content-type", "application/json");
            post.addHeader("Authorization", "Bearer " + paystackSecretKey);
            StringBuilder result = new StringBuilder();
            HttpResponse response = client.execute(post);

            if (response.getStatusLine().getStatusCode() == STATUS_CODE_CREATED) {
                BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

                String line;
                while ((line = rd.readLine())!= null) {
                    result.append(line);
                }
            } else {
                throw new Exception("Paystack is unable to process payment at the moment " +
                        "or something wrong with request");
            }

            ObjectMapper mapper = new ObjectMapper();
            createPlanResponse = mapper.readValue(result.toString(), CreatePlanResponse.class);
        } catch (Throwable ex) {
            ex.printStackTrace();
        }

        return createPlanResponse;
    }

    @Override
    public PaymentVerificationResponse paymentVerificationResponse(String reference, String plan, String id) throws RuntimeException {
        return null;
    }


    @Override
    public InitializePaymentResponse initializePaymentResponse(InitializePaymentRequest initializePaymentRequest) {
        InitializePaymentResponse initializePaymentResponse = null;

        try {
            Gson gson = new Gson();
            StringEntity postingString = new StringEntity(gson.toJson(initializePaymentRequest));
            HttpClient client = HttpClientBuilder.create().build();
            HttpPost post = new HttpPost(PAYSTACK_INITIALIZE_PAY);
            post.setEntity(postingString);
            post.addHeader("Content-type", "application/json");
            post.addHeader("Authorization", "Bearer " + paystackSecretKey);
            StringBuilder result = new StringBuilder();
            HttpResponse response = client.execute(post);

            if (response.getStatusLine().getStatusCode() == STATUS_CODE_OK) {
                BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

                String line;
                while ((line = rd.readLine())!= null) {
                    result.append(line);
                }
            } else {
                throw new RuntimeException("Paystack is unable to initialize payment at the moment");
            }

            ObjectMapper mapper = new ObjectMapper();
            initializePaymentResponse = mapper.readValue(result.toString(), InitializePaymentResponse.class);
        } catch (Throwable ex) {
            ex.printStackTrace();
        }

        return initializePaymentResponse;
    }

    @Override
    public PaymentVerificationResponse paymentVerificationResponse(String reference, String plan, String id, Object buyer) throws RuntimeException {
        PaymentVerificationResponse paymentVerificationResponse;
        PaymentPaystack paymentPaystack = null;

        try {
            HttpClient client = HttpClientBuilder.create().build();
            HttpGet request = new HttpGet(PAYSTACK_VERIFY + reference);
            request.addHeader("Content-type", "application/json");
            request.addHeader("Authorization", "Bearer " + paystackSecretKey);
            StringBuilder result = new StringBuilder();
            HttpResponse response = client.execute(request);

            if (response.getStatusLine().getStatusCode() == STATUS_CODE_OK) {
                BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
                String line;

                while ((line = rd.readLine()) != null) {
                    result.append(line);
                }
            } else {
                throw new Exception("Paystack is unable to verify payment at the moment");
            }

            ObjectMapper mapper = new ObjectMapper();
            paymentVerificationResponse = mapper.readValue(result.toString(), PaymentVerificationResponse.class);

            if (paymentVerificationResponse == null || "false".equals(paymentVerificationResponse.getStatus())) {
                throw new Exception("An error occurred during payment verification");
            } else if ("success".equals(paymentVerificationResponse.getData().getStatus())) {
                PricingPlanType pricingPlanType = PricingPlanType.valueOf(plan.toUpperCase());

                paymentPaystack = PaymentPaystack.builder()
                        .artist(paymentVerificationResponse.getData().getArtist())
                        .reference(paymentVerificationResponse.getData().getReference())
                        .amount(paymentVerificationResponse.getData().getAmount())
                        .gatewayResponse(paymentVerificationResponse.getData().getGatewayResponse())
                        .paidAt(paymentVerificationResponse.getData().getPaidAt())
                        .createdAt(paymentVerificationResponse.getData().getCreatedAt())
                        .channel(paymentVerificationResponse.getData().getChannel())
                        .currency(paymentVerificationResponse.getData().getCurrency())
                        .ipAddress(paymentVerificationResponse.getData().getIpAddress())
                        .planType(pricingPlanType)
                        .createdOn(new Date())
                        .build();
            }
        } catch (Exception ex) {
            throw new RuntimeException("Error processing payment with Paystack");
        }
        assert paymentPaystack != null;
        paystackPaymentRepository.save(paymentPaystack);
        return paymentVerificationResponse;
    }
}