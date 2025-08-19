package com.kabadiwala.dto.request;

import lombok.Data;

@Data
public class VerifyOtpRequestDto {
    private String contactNumber;
    private String otp;
}
