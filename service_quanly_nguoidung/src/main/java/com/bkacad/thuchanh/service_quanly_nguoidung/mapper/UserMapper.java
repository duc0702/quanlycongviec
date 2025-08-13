package com.bkacad.thuchanh.service_quanly_nguoidung.mapper;
import com.bkacad.thuchanh.service_quanly_nguoidung.dto.request.UserCreateRequest;
import com.bkacad.thuchanh.service_quanly_nguoidung.entity.User;
import org.mapstruct.Mapper;
@Mapper(componentModel = "spring")
public interface UserMapper {

    User toUser (UserCreateRequest userCreateRequest);
}
