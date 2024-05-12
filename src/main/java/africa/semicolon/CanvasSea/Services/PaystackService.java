package africa.semicolon.CanvasSea.Services;

import africa.semicolon.CanvasSea.DTOs.Request.InitializePaymentRequest;
import africa.semicolon.CanvasSea.DTOs.Response.CreatePlanResponse;
import africa.semicolon.CanvasSea.DTOs.Response.InitializePaymentResponse;
import africa.semicolon.CanvasSea.DTOs.Response.PaymentVerificationResponse;
import africa.semicolon.CanvasSea.DTOs.Request.CreatePlanRequest;

public interface PaystackService {
    CreatePlanResponse createPlanResponse(CreatePlanRequest createPlanRequest) throws RuntimeException;
    PaymentVerificationResponse paymentVerificationResponse(String reference, String plan, String id) throws RuntimeException;

    InitializePaymentResponse initializePaymentResponse(InitializePaymentRequest initializePaymentRequest);

    PaymentVerificationResponse paymentVerificationResponse(String reference, String plan, String id, Object buyer) throws RuntimeException
            ;
}
