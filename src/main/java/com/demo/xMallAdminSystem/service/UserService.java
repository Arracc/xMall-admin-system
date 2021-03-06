package com.demo.xMallAdminSystem.service;

import com.demo.xMallAdminSystem.pojo.AdminUser;
import com.demo.xMallAdminSystem.vo.UserVo;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
  String queryUserWithUsernameAndPassword(UserVo userVo);

  AdminUser queryUserWithToken(String token);
}
