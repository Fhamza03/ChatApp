package com.fssm.ChatApp.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.core.io.ClassPathResource;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendVerificationEmail(String toEmail, String userName, String verificationLink) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom("chathub.reply@gmail.com");
            helper.setTo(toEmail);
            helper.setSubject("Verify Your Email Address and Join ChatHub");

            // HTML body content with enhanced styling
            String emailBody = "<html>" +
                    "<head>" +
                    "<style>" +
                    "body {" +
                    "    font-family: 'Arial', sans-serif;" +
                    "    background-color: #F9F7F7;" +
                    "    color: #112D4E;" +
                    "    padding: 20px;" +
                    "    margin: 0;" +
                    "    text-align: center;" +
                    "}" +
                    "h1 {" +
                    "    color: #3F72AF;" +
                    "    font-size: 28px;" +
                    "    margin-bottom: 20px;" +
                    "}" +
                    "p {" +
                    "    font-size: 16px;" +
                    "    line-height: 1.6;" +
                    "    margin-bottom: 20px;" +
                    "}" +
                    ".button {" +
                    "    background-color: #3F72AF;" +
                    "    color: white;" +
                    "    padding: 15px 30px;" +
                    "    text-decoration: none;" +
                    "    border-radius: 5px;" +
                    "    font-size: 18px;" +
                    "    transition: background-color 0.3s ease;" +
                    "}" +
                    ".button:hover {" +
                    "    background-color: #2B4C87;" +
                    "}" +
                    "footer {" +
                    "    margin-top: 30px;" +
                    "    font-size: 12px;" +
                    "    color: #DBE2EF;" +
                    "    text-align: center;" +
                    "    line-height: 1.4;" +
                    "}" +
                    ".highlight {" +
                    "    color: #3F72AF;" +
                    "    font-weight: bold;" +
                    "}" +
                    "</style>" +
                    "</head>" +
                    "<body>" +
                    "<div>" +
                    "    <h1>Welcome to ChatHub, " + userName + "!</h1>" +
                    "    <p>We’re excited to have you on board! You’ve just signed up for ChatHub, the best place to chat and stay connected with your friends.</p>" +
                    "    <p>To start using ChatHub and begin chatting with your friends, please verify your email address by clicking the button below:</p>" +
                    "    <p><a href='" + verificationLink + "' class='button'>Verify Your Email</a></p>" +
                    "    <p>Once you verify your email, you'll be able to:</p>" +
                    "    <ul style='list-style-type: none; padding: 0;'>" +
                    "        <li><p><span class='highlight'>Create your profile</span> and customize it with your name, photo, and other details.</p></li>" +
                    "        <li><p><span class='highlight'>Chat with your friends</span> in private or group chats.</p></li>" +
                    "        <li><p><span class='highlight'>Join interesting groups</span> and interact with like-minded people.</p></li>" +
                    "    </ul>" +
                    "    <p>It’s fast, easy, and completely free!</p>" +
                    "    <footer>" +
                    "        <p>If you did not sign up for ChatHub, please ignore this email or <a href='mailto:support@chathub.com' style='color: #3F72AF;'>contact support</a>.</p>" +
                    "    </footer>" +
                    "</div>" +
                    "</body>" +
                    "</html>";

            helper.setText(emailBody, true);  // true indicates HTML content
            helper.addInline("ChatHub", new ClassPathResource("/static/images/ChatHub.png"));

            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
            System.out.println("Error sending verification email: " + e.getMessage());
        }
    }
}
