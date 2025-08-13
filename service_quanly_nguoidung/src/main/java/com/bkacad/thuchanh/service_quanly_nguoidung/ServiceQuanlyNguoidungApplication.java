package com.bkacad.thuchanh.service_quanly_nguoidung;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication(scanBasePackages = "com.bkacad.thuchanh.service_quanly_nguoidung")

@EnableDiscoveryClient
public class ServiceQuanlyNguoidungApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceQuanlyNguoidungApplication.class, args);
	}

}
