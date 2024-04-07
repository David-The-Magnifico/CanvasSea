package africa.semicolon.CanvasSea.Services;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import africa.semicolon.CanvasSea.DTOs.Request.EmailRequest;
import africa.semicolon.CanvasSea.DTOs.Response.EmailResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    @Autowired
    private final JavaMailSender javaMailSender;

    @Override
    public EmailResponse sendMailMessage(EmailRequest emailRequest) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        try {
            simpleMailMessage.setFrom(emailRequest.getSenderEmail());
            simpleMailMessage.setTo(emailRequest.getReceiverEmail());
            simpleMailMessage.setSubject(emailRequest.getTitle());
            simpleMailMessage.setText(emailRequest.getMessage());
            javaMailSender.send(simpleMailMessage);
            return new EmailResponse("Mail sent successfully");
        } catch (Exception exception) {
            return new EmailResponse("Mail sent out wrongly");
        }
    }
}
