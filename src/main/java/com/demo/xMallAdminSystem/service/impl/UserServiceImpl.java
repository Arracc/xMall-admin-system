package com.demo.xMallAdminSystem.service.impl;

import com.demo.xMallAdminSystem.config.JwtProperties;
import com.demo.xMallAdminSystem.exception.ExceptionEnum;
import com.demo.xMallAdminSystem.exception.XMallException;
import com.demo.xMallAdminSystem.mapper.UserMapper;
import com.demo.xMallAdminSystem.pojo.AdminUser;
import com.demo.xMallAdminSystem.pojo.UserInfo;
import com.demo.xMallAdminSystem.service.UserService;
import com.demo.xMallAdminSystem.utils.CodecUtils;
import com.demo.xMallAdminSystem.utils.JwtUtils;
import com.demo.xMallAdminSystem.utils.RsaUtils;
import com.demo.xMallAdminSystem.vo.UserVo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import java.util.Date;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

@EnableConfigurationProperties(JwtProperties.class)
@Service
public class UserServiceImpl implements UserService {

  @Autowired
  UserMapper userMapper;

  @Autowired
  JwtProperties jwtProperties;

  @Override
  public String queryUserWithUsernameAndPassword(UserVo userVo) {
    // 根据用户名获取用户信息
    AdminUser adminUser = userMapper.selectWithUsername(userVo.getUsername());
    if(adminUser == null){
      throw new XMallException(ExceptionEnum.USER_NOT_FOUND);
    }
    // 对请求的密码进行加密后校验
    String password = CodecUtils.md5Hex(userVo.getPassword(),adminUser.getSalt());
    if(!password.equals(adminUser.getPassword())){
      throw new XMallException(ExceptionEnum.USER_NOT_FOUND);
    }else{
      // 用户名密码正确，生成TOKEN并返回
      UserInfo userInfo = new UserInfo(adminUser.getId(),adminUser.getUsername());
      try {
        return JwtUtils.generateToken(userInfo, RsaUtils.getPrivateKey(jwtProperties.getPriKeyPath()),30);
      } catch (Exception e) {
        throw new XMallException(ExceptionEnum.TOKEN_CREATE_FAILED);
      }
    }
  }

  @Override
  public AdminUser queryUserWithToken(String token) {
    String username;
    AdminUser adminUser;
    Jws<Claims> claimsJws;
    // 校验有效期
    try {
      claimsJws = JwtUtils
          .parserToken(token, RsaUtils.getPublicKey(jwtProperties.getPubKeyPath()));
    } catch (Exception e) {
      throw new XMallException(ExceptionEnum.TOKEN_PARSE_FAILED);
    }
    if(claimsJws.getBody().getExpiration().before(new Date())){
      throw new XMallException(ExceptionEnum.UNAUTHORIZED_VISIT);
    }
    // 校验用户名属性存在
    try {
      username = JwtUtils
          .getInfoFromToken(token, RsaUtils.getPublicKey(jwtProperties.getPubKeyPath())).getUsername();
    } catch (Exception e) {
      throw new XMallException(ExceptionEnum.TOKEN_PARSE_FAILED);
    }
    // 校验用户名非空
    if(!"".equals(username)){
      adminUser = userMapper.selectWithUsername(username);
    }else{
      throw new XMallException(ExceptionEnum.TOKEN_PARSE_FAILED);
    }
    // 校验用户存在
    if(adminUser == null){
      throw new XMallException(ExceptionEnum.UNAUTHORIZED_VISIT);
    }
    return adminUser;
  }
}
