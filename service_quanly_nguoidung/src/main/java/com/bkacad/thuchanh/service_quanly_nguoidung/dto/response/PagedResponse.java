package com.bkacad.thuchanh.service_quanly_nguoidung.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
public class PagedResponse <T>{
    @JsonProperty("employees") // Đổi tên thuộc tính thành "employees" trong JSON
    private List<T> items;
    private long totalItems;
    private int totalPages;
    private int currentPage;
}
