package com.bkacad.thuchanh.service_quanly_nguoidung.dto.response;

import com.bkacad.thuchanh.service_quanly_nguoidung.entity.User;
import com.bkacad.thuchanh.service_quanly_nguoidung.enums.UserRole;
import com.bkacad.thuchanh.service_quanly_nguoidung.enums.UserStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponse {
    private Integer id;
    private String username;
    private String fullName;
    private String email;
    private String phone;
    private UserRole role;
    private UserStatus status;
    public static UserResponse fromEntity(User user) {
        UserResponse dto = new UserResponse();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setFullName(user.getFullName());
        dto.setEmail(user.getEmail());
        dto.setPhone(user.getPhone());
        dto.setRole(user.getRole());
        dto.setStatus(user.getStatus());
        return dto;
    }
}
