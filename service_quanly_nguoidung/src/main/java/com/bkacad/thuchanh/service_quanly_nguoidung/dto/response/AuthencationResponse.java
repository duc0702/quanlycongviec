package com.bkacad.thuchanh.service_quanly_nguoidung.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@Data
public class AuthencationResponse {
    String token;
    boolean authenticated;
}
