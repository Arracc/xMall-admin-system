package com.demo.xMallAdminSystem.exception;

import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {

  private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

  /**
   * 处理自定义的业务异常
   *
   * @param req
   * @param e
   * @return
   */
  @ExceptionHandler(value = XMallException.class)
  @ResponseBody
  public ResponseEntity<String> xMallExceptionHandler(HttpServletRequest req, XMallException e) {
    logger.error("发生业务异常！原因是：{}", e.getExceptionEnum().getMsg());
    return new ResponseEntity(e.getExceptionEnum().getMsg(),
        HttpStatus.valueOf(e.getExceptionEnum().getStatus()));
  }

  /**
   * 处理空指针的异常
   *
   * @param req
   * @param e
   * @return
   */
  @ExceptionHandler(value = NullPointerException.class)
  @ResponseBody
  public ResponseEntity<String> exceptionHandler(HttpServletRequest req, NullPointerException e) {
    logger.error("发生空指针异常！原因是:", e);
    return new ResponseEntity(e.getMessage(),
        HttpStatus.valueOf(500));
  }


  /**
   * 处理其他异常
   *
   * @param req
   * @param e
   * @return
   */
  @ExceptionHandler(value = Exception.class)
  @ResponseBody
  public ResponseEntity<String> exceptionHandler(HttpServletRequest req, Exception e) {
    logger.error("未知异常！原因是:", e);
    return new ResponseEntity(e.getMessage(),
        HttpStatus.valueOf(500));
  }
}