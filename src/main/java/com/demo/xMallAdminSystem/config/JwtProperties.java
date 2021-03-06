package com.demo.xMallAdminSystem.config;

import com.demo.xMallAdminSystem.utils.RsaUtils;
import java.io.File;
import java.security.PrivateKey;
import java.security.PublicKey;
import javax.annotation.PostConstruct;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "jwt")
@Data
public class JwtProperties {
  private String secret;
  private String pubKeyPath;
  private String priKeyPath;
  private String expire;
  private String cookieName;

  private PublicKey publicKey;
  private PrivateKey privateKey;

  @PostConstruct
  private void init() throws Exception {
    File pubKey = new File(pubKeyPath);
    File priKey = new File(priKeyPath);
    if(!pubKey.exists() || !priKey.exists()){
      RsaUtils.generateKey(this.pubKeyPath,this.priKeyPath,this.secret);
    }
    this.publicKey = RsaUtils.getPublicKey(pubKeyPath);
    this.privateKey = RsaUtils.getPrivateKey(priKeyPath);
  }

}
