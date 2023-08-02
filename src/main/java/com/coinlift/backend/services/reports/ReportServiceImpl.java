package com.coinlift.backend.services.reports;

import com.coinlift.backend.dtos.reports.ReportRequestDto;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ReportServiceImpl implements ReportService {

    private final JavaMailSender mailSender;

    public ReportServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void sendPostReport(ReportRequestDto reportMsg, String type, UUID id) {
        String emailContent = createEmailContent(type, id, reportMsg);
        sendEmail(emailContent);
    }

    private void sendEmail(String content) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(System.getenv("MAIL_USERNAME"));
            helper.setSubject("Report Notification");
            helper.setText(content, true);

            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    private String createEmailContent(String type, UUID id, ReportRequestDto reportMsg) {
        return String.format(
                "<html><head>" +
                        "<style>" +
                        "body { font-family: Arial, sans-serif; }" +
                        "h2 { color: #007bff; }" +
                        "p { margin-bottom: 10px; }" +
                        ".container { border: 1px solid #ddd; padding: 20px; }" +
                        "</style>" +
                        "</head><body>" +
                        "<div class=\"container\">" +
                        "<h2>Report Notification</h2>" +
                        "<p><strong>Type:</strong> %s</p>" +
                        "<p><strong>ID:</strong> %s</p>" +
                        "<p><strong>Message:</strong> %s</p>" +
                        "</div>" +
                        "</body></html>",
                type, id, reportMsg.reportMsg()
        );
    }
}
