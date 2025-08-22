package com.bkacad.thuchanh.service_quanly_nguoidung.service;

import com.bkacad.thuchanh.service_quanly_nguoidung.dto.request.AuthenticationRequest;
import com.bkacad.thuchanh.service_quanly_nguoidung.dto.request.IntrospectRequest;
import com.bkacad.thuchanh.service_quanly_nguoidung.dto.response.AuthencationResponse;
import com.bkacad.thuchanh.service_quanly_nguoidung.dto.response.IntrospectResponse;
import com.bkacad.thuchanh.service_quanly_nguoidung.entity.User;
import com.bkacad.thuchanh.service_quanly_nguoidung.exception.AppException;
import com.bkacad.thuchanh.service_quanly_nguoidung.exception.ErrorCode;
import com.bkacad.thuchanh.service_quanly_nguoidung.repository.UserRepository;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PACKAGE, makeFinal = true)
public class AuthenticationService {
    @NonFinal
    @Value("${jwt.signing-key}")
     String SECRET_KEY;

    UserRepository userRepository;

    public IntrospectResponse introspect(IntrospectRequest request) throws JOSEException, ParseException {
        String token = request.getToken();
        JWSVerifier verifier = new MACVerifier(SECRET_KEY.getBytes());

        SignedJWT signedJWT = SignedJWT.parse(token);
        Date expiration = signedJWT.getJWTClaimsSet().getExpirationTime();

       boolean verified= signedJWT.verify(verifier);
       return IntrospectResponse.builder()
               .valid(verified&& expiration.after(new Date()))
               .build();

    }

    public AuthencationResponse authentication(AuthenticationRequest request) throws JOSEException {
      User user = userRepository.findByUsername(request.getUsername()).orElseThrow(()
                -> new AppException(ErrorCode.USER_NOT_FOUND));
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);

        boolean authentication= passwordEncoder.matches(request.getPassword(),user.getPassword());

       if (!authentication) {
            throw new AppException(ErrorCode.UNAUTHENTICATION);
        }
       String token = generateToken(user);
        return AuthencationResponse.builder()
                .token(token)
                .authenticated(true)
                .build();
    }
    public String generateToken(User user) throws JOSEException {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getUsername())
                .issuer("qlcv")
                .issueTime(new Date())
                .expirationTime(new Date(Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()))
                .claim("scope", user.getRole())
                .build();
        Payload payload = new Payload(jwtClaimsSet.toJSONObject());
        JWSObject jwsObject = new JWSObject(header, payload);
        try {
            jwsObject.sign(new MACSigner(SECRET_KEY.getBytes()));
            return jwsObject.serialize();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }


    }


}
