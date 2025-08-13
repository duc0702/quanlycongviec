package com.bkacad.thuchanh.service_quanly_nguoidung.exception;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AppException extends RuntimeException{
     private ErrorCode errorCode;

     public AppException(ErrorCode errorCode) {
          super(errorCode.getErrorMessage());
          this.errorCode = errorCode;
     }
}
