package com.oxad.admin_api.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendGymPassword(
            String gymName,
            String gymId,
            String password) {

        SimpleMailMessage message =
                new SimpleMailMessage();

        message.setFrom("oxadmedia@gmail.com");

        message.setTo("oxadmedia@gmail.com");

        message.setSubject(
                gymName + " Registration Details"
        );

        message.setText(
                "Hello,\n\n" +
                "A new gym has been registered successfully.\n\n" +
                "Gym Name: " + gymName + "\n" +
                "Gym ID: " + gymId + "\n" +
                "Password: " + password + "\n\n" +
                "Please keep these credentials secure."
        );

        mailSender.send(message);
    }
}