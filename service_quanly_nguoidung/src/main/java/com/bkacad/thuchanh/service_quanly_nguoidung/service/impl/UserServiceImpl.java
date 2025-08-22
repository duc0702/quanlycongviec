package com.bkacad.thuchanh.service_quanly_nguoidung.service.impl;

import com.bkacad.thuchanh.service_quanly_nguoidung.dto.request.UserCreateRequest;
import com.bkacad.thuchanh.service_quanly_nguoidung.dto.request.UserUpdateRequest;
import com.bkacad.thuchanh.service_quanly_nguoidung.dto.response.PagedResponse;
import com.bkacad.thuchanh.service_quanly_nguoidung.dto.response.UserResponse;
import com.bkacad.thuchanh.service_quanly_nguoidung.entity.User;
import com.bkacad.thuchanh.service_quanly_nguoidung.enums.UserRole;
import com.bkacad.thuchanh.service_quanly_nguoidung.enums.UserStatus;
import com.bkacad.thuchanh.service_quanly_nguoidung.exception.AppException;
import com.bkacad.thuchanh.service_quanly_nguoidung.exception.ErrorCode;
import com.bkacad.thuchanh.service_quanly_nguoidung.mapper.UserMapper;
import com.bkacad.thuchanh.service_quanly_nguoidung.repository.UserRepository;
import com.bkacad.thuchanh.service_quanly_nguoidung.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
private final UserRepository userRepository;
private final UserMapper userMapper;


    @Override
    public String createUser(UserCreateRequest userCreateRequest) {
        if (userRepository.existsByUsername(userCreateRequest.getUsername())) {

            throw new AppException(ErrorCode.USERNAME_EXISTED);
        }

        if (userRepository.existsByEmail(userCreateRequest.getEmail())) {
            throw new AppException(ErrorCode.EMAIL_EXISTED);
        }
        User user = userMapper.toUser(userCreateRequest);
        PasswordEncoder passwordEncode= new BCryptPasswordEncoder(10);
        user.setPassword(passwordEncode.encode(user.getPassword()));
        user.setRole(UserRole.EMPLOYEE);
        user.setStatus(UserStatus.ACTIVE);
        user.setCreateAt(LocalDateTime.now());
        userRepository.save(user);
        return "User created successfully name: " + user.getFullName();
    }
    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public PagedResponse<UserResponse> findEmployees(String search, int page, int limit, String sortBy, String sortOrder) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortOrder), sortBy);
        Pageable pageable = PageRequest.of(page - 1, limit, sort);
        Specification<User> spec = (root, query, criteriaBuilder) -> {
            if (!StringUtils.hasText(search)) {
                return criteriaBuilder.conjunction();
            }
            String pattern = "%" + search.toLowerCase() + "%";
            return criteriaBuilder.or(
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("fullName")), pattern),
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("email")), pattern)
            );
        };

        Page<User> userPage = userRepository.findAll(spec, pageable);

        List<UserResponse> userDtos = userPage.getContent().stream()
                .map(UserResponse::fromEntity)
                .collect(Collectors.toList());

        return new PagedResponse<>(
                userDtos,
                userPage.getTotalElements(),
                userPage.getTotalPages(),
                userPage.getNumber() + 1
        );
    }
    @Override
    @Transactional
    public UserResponse updateUser(Integer id, UserUpdateRequest request) {

        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        Optional<User> userByNewEmail = userRepository.findByEmail(request.getEmail());
        if (userByNewEmail.isPresent() && !userByNewEmail.get().getId().equals(id)) {
            throw new AppException(ErrorCode.EMAIL_EXISTED);
        }
        Optional<User> userByNewUsername = userRepository.findByUsername(request.getUsername());
        if (userByNewUsername.isPresent() && !userByNewUsername.get().getId().equals(id)) {
            throw new AppException(ErrorCode.USERNAME_EXISTED);
        }
        existingUser.setFullName(request.getFullName());
        existingUser.setEmail(request.getEmail());
        existingUser.setPhone(request.getPhone());
        existingUser.setStatus(UserStatus.valueOf(request.getStatus()));
        User updatedUser = userRepository.save(existingUser);
        return UserResponse.fromEntity(updatedUser);
    }

    @Override
    public void deleteEmployee(Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        user.setStatus(UserStatus.INACTIVE);
        userRepository.save(user);
    }

    @Override
    @PostAuthorize("returnObject.username== authentication.name ")
    public UserResponse findById(Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        return UserResponse.fromEntity(user);

    }
}


