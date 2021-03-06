package com.demo.xMallAdminSystem.mapper;

import com.demo.xMallAdminSystem.pojo.AdminUser;
import com.demo.xMallAdminSystem.pojo.UserInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
  AdminUser selectWithUsername(String username);
  UserInfo getUserInfo(AdminUser adminUser);
}
