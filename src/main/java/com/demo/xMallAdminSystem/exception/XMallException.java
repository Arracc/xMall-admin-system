package com.demo.xMallAdminSystem.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class XMallException extends RuntimeException{
  private ExceptionEnum exceptionEnum;
}
