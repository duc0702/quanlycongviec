package com.bkacad.thuchanh.service_quanly_nguoidung.controller;

import com.bkacad.thuchanh.service_quanly_nguoidung.dto.request.AuthenticationRequest;
import com.bkacad.thuchanh.service_quanly_nguoidung.dto.request.IntrospectRequest;
import com.bkacad.thuchanh.service_quanly_nguoidung.dto.response.ApiResponse;
import com.bkacad.thuchanh.service_quanly_nguoidung.dto.response.AuthencationResponse;
import com.bkacad.thuchanh.service_quanly_nguoidung.dto.response.IntrospectResponse;
import com.bkacad.thuchanh.service_quanly_nguoidung.service.AuthenticationService;
import com.nimbusds.jose.JOSEException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {
    AuthenticationService authenticationService;
    @PostMapping("/token")
    public ApiResponse<AuthencationResponse> login( @RequestBody  AuthenticationRequest authenticationRequest) throws JOSEException {
        AuthencationResponse result =  authenticationService.authentication(authenticationRequest);
        return ApiResponse.<AuthencationResponse>builder()
                .data(result)
                .build();
    }

    @PostMapping("/introspect")
    public ApiResponse<IntrospectResponse> introspect(@RequestBody IntrospectRequest request) throws JOSEException, ParseException {
        IntrospectResponse result =  authenticationService.introspect(request);
        return ApiResponse.<IntrospectResponse>builder()
                .data(result)
                .build();
    }


}
