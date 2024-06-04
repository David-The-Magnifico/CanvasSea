package africa.semicolon.CanvasSea.Controller;

import africa.semicolon.CanvasSea.DTOs.Request.CreatePlanRequest;
import africa.semicolon.CanvasSea.DTOs.Request.InitializePaymentRequest;
import africa.semicolon.CanvasSea.DTOs.Response.CreatePlanResponse;
import africa.semicolon.CanvasSea.DTOs.Response.InitializePaymentResponse;
import africa.semicolon.CanvasSea.DTOs.Response.PaymentVerificationResponse;
import africa.semicolon.CanvasSea.Services.PaystackService;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(
        value = "/paystack",
        produces = MediaType. APPLICATION_JSON_VALUE
)
public class PaystackController {

    private final PaystackService paystackService;

    public PaystackController(PaystackService paystackService) {
        this.paystackService = paystackService;
    }

    @PostMapping(value = "/createPlan", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE,
        headers = "Authorization=Bearer {sk_test_cef58d6f6decf4e073b7ed15b014cfcc493f17cb}")
public CreatePlanResponse createPlan (@Validated @RequestBody CreatePlanRequest createPlanRequest) throws RuntimeException {
    return paystackService.createPlanResponse(createPlanRequest);
}

    @PostMapping("/initializePayment")
    public InitializePaymentResponse initializePayment(@Validated @RequestBody InitializePaymentRequest initializePaymentRequest) throws RuntimeException {
        return paystackService.initializePaymentResponse(initializePaymentRequest);
    }

    @GetMapping("/verifyPayment/{reference}/{plan}/{id}")
    public PaymentVerificationResponse paymentVerification(@PathVariable(value = "reference") String reference,
                                                           @PathVariable (value = "plan") String plan,
                                                           @PathVariable(value = "id") String id) throws RuntimeException {
        if (reference.isEmpty() || plan.isEmpty()) {
            throw new RuntimeException("reference, plan and id must be provided in path");
        }
        return paystackService.paymentVerificationResponse(reference, plan, String.valueOf(id));
    }
}

