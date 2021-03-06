package com.demo.xMallAdminSystem.pojo;

import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Data
@Table(name="admin_user")
public class AdminUser {
  @Id
  private Long id;
  private String username;
  private String password;
  private String role;
  private String salt;
}
