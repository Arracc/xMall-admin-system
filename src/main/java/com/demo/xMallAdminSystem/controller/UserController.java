package com.demo.xMallAdminSystem.controller;

import com.demo.xMallAdminSystem.exception.ExceptionEnum;
import com.demo.xMallAdminSystem.exception.XMallException;
import com.demo.xMallAdminSystem.pojo.AdminUser;
import com.demo.xMallAdminSystem.service.UserService;
import com.demo.xMallAdminSystem.vo.UserVo;
import java.util.HashMap;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:8082",allowCredentials = "true")
@RestController
public class UserController {

  @Autowired
  UserService userService;

  @PostMapping("/vue-admin-template/user/login")
    public ResponseEntity<HashMap<String,Object>> login(@RequestBody UserVo userVo) {
    String token = userService.queryUserWithUsernameAndPassword(userVo);
    HashMap<String, Object> responseMap = new HashMap<>();
    HashMap<String, String> responseDataMap = new HashMap<>();
    responseDataMap.put("token", token);
    responseMap.put("code", 20000);
    responseMap.put("data", responseDataMap);
    return ResponseEntity.ok(responseMap);
  }

  @GetMapping("/vue-admin-template/user/info")
  public ResponseEntity<HashMap<String,Object>> getUserInfo(@CookieValue(name = "vue_admin_template_token") String token) {
    if(StringUtils.isBlank(token)){
      throw new XMallException(ExceptionEnum.UNAUTHORIZED_VISIT);
    }
    AdminUser adminUser = userService.queryUserWithToken(token);
    HashMap<String,Object> responseMap = new HashMap<String,Object>();
    HashMap<String, String> responseDataMap = new HashMap<>();
    responseDataMap.put("name",adminUser.getUsername());
    responseDataMap.put("avatar","");
    responseMap.put("code",20000);
    responseMap.put("data",responseDataMap);
    return ResponseEntity.ok(responseMap);
  }



}
