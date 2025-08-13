package com.bkacad.thuchanh.service_quanly_nguoidung.controller;


import com.bkacad.thuchanh.service_quanly_nguoidung.dto.request.UserCreateRequest;
import com.bkacad.thuchanh.service_quanly_nguoidung.dto.request.UserUpdateRequest;
import com.bkacad.thuchanh.service_quanly_nguoidung.dto.response.ApiResponse;
import com.bkacad.thuchanh.service_quanly_nguoidung.dto.response.PagedResponse;
import com.bkacad.thuchanh.service_quanly_nguoidung.dto.response.UserResponse;
import com.bkacad.thuchanh.service_quanly_nguoidung.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
//
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/employees/")
public class UserController {
    private final UserService userService;
    @PostMapping("")
    public ResponseEntity<ApiResponse<String>> createUser(
            @RequestBody @Valid UserCreateRequest userCreateRequest) {

        String fullName = userService.createUser(userCreateRequest);
        ApiResponse<String> response = new ApiResponse<String>(200, "Tạo người dùng thành công", fullName);
        return ResponseEntity.ok(response);
    }
    @GetMapping
    public ResponseEntity<ApiResponse<PagedResponse<UserResponse>>> getEmployees(
            @RequestParam(name = "search", required = false, defaultValue = "") String search,
            @RequestParam(name = "page", required = false, defaultValue = "1") int page,
            @RequestParam(name = "limit", required = false, defaultValue = "10") int limit,
            @RequestParam(name = "sortBy", required = false, defaultValue = "createAt") String sortBy,
            @RequestParam(name = "sortOrder", required = false, defaultValue = "desc") String sortOrder
    ) {
        PagedResponse<UserResponse> response = userService.findEmployees(search, page, limit, sortBy, sortOrder);
        ApiResponse <PagedResponse<UserResponse>> apiResponse = new ApiResponse<>(
                200, "Lấy danh sách người dùng thành công", response);
        return ResponseEntity.ok(apiResponse);
    }
    // Cập nhật thông tin người dùng

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> updateEmployee(
            @PathVariable Integer id,
            @Valid @RequestBody UserUpdateRequest request
    ) {
        UserResponse updatedUser = userService.updateUser(id, request);
        ApiResponse<UserResponse> apiResponse= new ApiResponse<UserResponse>(200, "Cập nhật người dùng thành công", updatedUser);
        return ResponseEntity.ok(apiResponse);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable Integer id) {
        userService.deleteEmployee(id);
        ApiResponse<String> response = new ApiResponse<>(200, "Xoá người dùng thành công", null);
        return ResponseEntity.ok(response);
    }
}
