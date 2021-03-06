package com.demo.xMallAdminSystem.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum ExceptionEnum {
  TOKEN_CREATE_FAILED(500,"TOKEN生成失败"),
  TOKEN_PARSE_FAILED(500,"TOKEN解析失败"),
  UNAUTHORIZED_VISIT(403,"未授权的访问"),
  USER_NOT_FOUND(400,"用户名或密码错误"),
  NULL_POINT_EXCEPTION(500,"空指针异常");

  private int status;
  private String msg;


}
