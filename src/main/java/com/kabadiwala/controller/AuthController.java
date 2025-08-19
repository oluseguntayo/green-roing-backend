package com.kabadiwala.controller;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kabadiwala.dto.request.OtpRequestDto;
import com.kabadiwala.dto.request.VerifyOtpRequestDto;
import com.kabadiwala.dto.response.CommonResponseDto;
import com.kabadiwala.entity.User;
import com.kabadiwala.enums.Role;
import com.kabadiwala.enums.Status;
import com.kabadiwala.security.CustomUserDetails;
import com.kabadiwala.security.JwtUtil;
import com.kabadiwala.service.UserService;
import com.kabadiwala.utils.ResponseBuilder;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    private final UserService userService;
    // You would inject services for sending SMS and generating JWTs
    // private final SmsService smsService;
    // private final JwtService jwtService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/send-otp")
    public ResponseEntity<CommonResponseDto<User>> sendOtp(@RequestBody OtpRequestDto otpRequestDto) {
        String contactNumber = otpRequestDto.getContactNumber();
        logger.info("OTP request received for contact number: {}", contactNumber);

        // 1. Find user or create a temporary one if they don't exist
        User user = userService.findByContactNumber(contactNumber)
                .orElseGet(() -> User.builder()
                        .contactNumber(contactNumber)
                        .fullName("New User") // Temporary name
                        .role(Role.USER) // Default as User
                        .status(Status.NEW)
                        .build());

        // 2. Generate OTP
        // String otp = String.valueOf(ThreadLocalRandom.current().nextInt(100000,
        // 1000000));
        String otp = "123456";

        // 3. Set OTP and expiry on the user object
        user.setOtp(otp);
        user.setOtpExpiry(LocalDateTime.now().plusMinutes(5)); // OTP is valid for 5 minutes
        userService.saveUser(user);

        // 4. Send OTP via SMS (this is where you'd use a real SMS service)
        // smsService.sendSms(contactNumber, "Your OTP is: " + otp);
        logger.info("Generated OTP {} for contact number {}", otp, contactNumber); // In a real app, DO NOT log the OTP
                                                                                   // itself.
        return ResponseBuilder.success(HttpStatus.OK, "OTP sent successfully.", null);
        // return ResponseEntity.ok("OTP sent successfully.");
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<?> verifyOtp(@RequestBody VerifyOtpRequestDto verifyOtpRequestDto) {
        String contactNumber = verifyOtpRequestDto.getContactNumber();
        String otp = verifyOtpRequestDto.getOtp();
        logger.info("OTP verification request for contact number: {}", contactNumber);

        Optional<User> userOptional = userService.findByContactNumber(contactNumber);

        // Check if user exists
        if (userOptional.isEmpty()) {
            return ResponseBuilder.failure(HttpStatus.BAD_REQUEST,
                    "User not found with contact no. = " + contactNumber);
        }
        User user = userOptional.get();
        logger.info("{}", user);

        // Check if OTP is valid and not expired
        if (user.getOtp() == null || !user.getOtp().equals(otp) || user.getOtpExpiry().isBefore(LocalDateTime.now())) {
            return ResponseBuilder.failure(HttpStatus.BAD_REQUEST, "Invalid or expired OTP.");
        }

        // OTP is valid, clear it for security
        user.setOtp(null);
        user.setOtpExpiry(null);

        CustomUserDetails userDetails = new CustomUserDetails(user);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails,
                null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        String accessToken = jwtUtil.generateToken(authenticationToken);
        Date tokenExpiry = jwtUtil.getTokenExpiry(accessToken);
        user.setAccessToken(accessToken);
        user.setAccessTokenExpiry(tokenExpiry.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime());

        userService.saveUser(user);

        logger.info("OTP verification successful for user: {}", user.getId());

        // Generate a JWT token for the authenticated user
        // String token = jwtService.generateToken(user);
        // String token = "dummy-jwt-token-for-" + user.getId(); // Placeholder for JWT
        // token

        return ResponseBuilder.success(HttpStatus.OK, "Login successful", user);
    }

    @GetMapping("/ping")
    public ResponseEntity<?> getMethodName() {
        return ResponseBuilder.success(HttpStatus.OK, "Server is up.", null);
    }

}
