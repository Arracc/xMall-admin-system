package com.demo.xMallAdminSystem.vo;

import java.util.HashMap;
import lombok.Data;
import lombok.Setter;

@Data
public class ResponseVo {
  private int status;
  private HashMap<String,Object> data = new HashMap<String,Object>();

  private ResponseVo(){}

  public static ResponseVo ok(){
    ResponseVo responseVo = new ResponseVo();
    responseVo.setStatus(200);
    return responseVo;
  }

  public ResponseVo data(String key,Object value){
    data.put(key,value);
    return this;
  }

}
