package com.bkacad.thuchanh.service_quanly_nguoidung.service;

import com.bkacad.thuchanh.service_quanly_nguoidung.dto.request.UserCreateRequest;
import com.bkacad.thuchanh.service_quanly_nguoidung.dto.request.UserUpdateRequest;
import com.bkacad.thuchanh.service_quanly_nguoidung.dto.response.PagedResponse;
import com.bkacad.thuchanh.service_quanly_nguoidung.dto.response.UserResponse;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
String createUser(UserCreateRequest userCreateRequest);
    PagedResponse<UserResponse> findEmployees(String search, int page, int limit, String sortBy, String sortOrder);
    UserResponse updateUser(Integer id, UserUpdateRequest request);
    void deleteEmployee(Integer id);
    UserResponse findById(Integer id);
}
