package africa.semicolon.CanvasSea.Services;

import africa.semicolon.CanvasSea.DTOs.Request.EmailRequest;
import africa.semicolon.CanvasSea.DTOs.Response.EmailResponse;

public interface EmailService {
    EmailResponse sendMailMessage(EmailRequest emailRequest);
}
