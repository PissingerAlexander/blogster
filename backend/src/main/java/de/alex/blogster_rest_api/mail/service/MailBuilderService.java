package de.alex.blogster_rest_api.mail.service;

import de.alex.blogster_rest_api.mail.config.MailConfiguration;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
public class MailBuilderService {
    private final MailConfiguration mailConfiguration;

    public MailBuilderService(MailConfiguration mailConfiguration) {
        this.mailConfiguration = mailConfiguration;
    }

    public SimpleMailMessage buildSimpleMailMessage(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(mailConfiguration.getUsername());
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        return message;
    }
}
