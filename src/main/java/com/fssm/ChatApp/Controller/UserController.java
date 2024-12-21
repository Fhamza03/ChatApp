package com.fssm.ChatApp.Controller;

import com.fssm.ChatApp.Configuration.CustomUser;
import com.fssm.ChatApp.Model.User;
import com.fssm.ChatApp.Repository.UserRepository;
import com.fssm.ChatApp.Service.EmailService;
import com.fssm.ChatApp.Service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
public class UserController {
    private static final Logger LOGGER = Logger.getLogger(UserController.class.getName());
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    EmailService emailService;

    @PostMapping("/signup")
    public ResponseEntity<String> SignUp(@RequestBody User user) {
        try {
            // Encode password
            user.setPassword(passwordEncoder.encode(user.getPassword()));

            // Generate verification token
            String verificationToken = UUID.randomUUID().toString();
            user.setVerificationToken(verificationToken);
            user.setVerified(false); // Mark user as unverified initially

            // Save user
            userService.CreateUser(user);

            // Get the ngrok public URL (update this dynamically if needed)
            String ngrokUrl = "https://40d2-105-67-132-168.ngrok-free.app";  // Replace with dynamic ngrok URL
            String verificationLink = ngrokUrl + "/verify?token=" + verificationToken;

            // Send styled HTML email with the verification link
            emailService.sendVerificationEmail(user.getEmail(), user.getUserName(), verificationLink);

            // Styled confirmation message in HTML format
            String confirmationMessage = "<html>" +
                    "<head><style>" +
                    "body {font-family: 'Arial', sans-serif; color: #112D4E; padding: 20px;}" +
                    "h1 {color: #3F72AF; font-size: 24px;}" +
                    "p {font-size: 16px;}" +
                    ".highlight {color: #3F72AF; font-weight: bold;}" +
                    "</style></head>" +
                    "<body>" +
                    "<h1>Welcome to ChatHub, " + user.getUserName() + "!</h1>" +
                    "<p>You've successfully created your account! 🎉</p>" +
                    "<p>Please check your inbox for a verification email. To fully activate your account and start chatting with friends, simply click the verification link inside the email.</p>" +
                    "<p>Once verified, you'll be able to access all ChatHub features, including:</p>" +
                    "<ul>" +
                    "<li><p>Creating your profile and customizing it with your details.</p></li>" +
                    "<li><p>Chatting with friends in private or group chats.</p></li>" +
                    "<li><p>Joining interesting groups and discussions.</p></li>" +
                    "</ul>" +
                    "<p>If you did not sign up for ChatHub, please ignore this email or contact us at <a href='mailto:support@chathub.com'>support@chathub.com</a>.</p>" +
                    "<p>We’re excited to have you on board and can’t wait to chat with you! 😎</p>" +
                    "</body>" +
                    "</html>";
            return ResponseEntity.status(HttpStatus.CREATED)
                    .contentType(MediaType.TEXT_HTML)
                    .body(confirmationMessage);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error occurred while signing up user", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error during sign up: " + e.getMessage());
        }
    }




    @GetMapping("/verify")
    public ResponseEntity<String> verifyEmail(@RequestParam String token) {
        try {
            Optional<User> optionalUser = userRepository.findByVerificationToken(token);

            if (optionalUser.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid or expired verification token.");
            }

            User user = optionalUser.get();
            user.setVerified(true);
            user.setVerificationToken(null);
            userRepository.save(user);

            return ResponseEntity.ok("Email verified successfully!");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error during email verification", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error during email verification: " + e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody User user, HttpServletRequest request) {
        try {
            UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUserName());

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            HttpSession session = request.getSession();
            session.setAttribute("username", userDetails.getUsername());
            session.setAttribute("userId", user.getUserId());

            LOGGER.info("User logged in: " + userDetails.getUsername());

            Map<String, String> responseBody = new HashMap<>();
            responseBody.put("userId", ((CustomUser) userDetails).getUserId());

            return ResponseEntity.ok(responseBody);
        } catch (UsernameNotFoundException e) {
            LOGGER.warning("User not found: " + user.getUserName());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Collections.singletonMap("error", "Invalid username or password"));
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error occurred during login", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.singletonMap("error", "Error occurred during login" + e.getMessage()));
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<String> Logout(HttpServletRequest request, HttpServletResponse response) {
        try {
            HttpSession session = request.getSession(false);
            if (session != null) {
                session.invalidate();
            }
            LOGGER.info("User logged out");
            return ResponseEntity.ok("Logout successful");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred during logout");
        }
    }

    @GetMapping("/AllUsers/{userId}")
    public ResponseEntity<List<Map<String, Object>>> getUsersWithLimitedFields(@PathVariable Integer userId) {
        List<Map<String, Object>> users = userService.findUsersWithStatus(userId);
        return ResponseEntity.ok(users);
    }
}
