package com.tairanchina.teval.service.admin.util;

import com.tairanchina.teval.common.constant.Global;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.Base64Codec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;

/**
 * 描述:
 *
 * @author hzds
 * @Create 2018-08 : 15 11:45
 */
public class TokenUtils {
    private static Logger logger = LoggerFactory.getLogger(TokenUtils.class);

    public static String getToken(String jsonObj){
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(Global.LOGIN_SECRET_KEY);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes,signatureAlgorithm.getJcaName());
        JwtBuilder builder = Jwts.builder()
                .setPayload(jsonObj)
                .signWith(signatureAlgorithm, signingKey);
        return builder.compact();

    }
    public static String isLogin(String jwt){
        String params="";
        if(jwt.split("\\.").length == 3){
            String header = jwt.split("\\.")[0];
            String payload = jwt.split("\\.")[1];
            String sign = jwt.split("\\.")[2];

            String headerNew = getToken(Base64Codec.BASE64URL.decodeToString(payload)).split("\\.")[0];
            String signNew = getToken(Base64Codec.BASE64URL.decodeToString(payload)).split("\\.")[2];

            System.out.println("新的token："+getToken(Base64Codec.BASE64URL.decodeToString(payload)));
            System.out.println("旧的token："+jwt);

            if(header.equals(headerNew) && sign.equals(signNew)){//进行安全校验（头部和签名）

                Claims claims = Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(Global.LOGIN_SECRET_KEY)).parseClaimsJws(jwt).getBody();
                if(claims!=null){
                    long expdate = (Long) claims.get("expDate");
                    long nowMillis = System.currentTimeMillis();
                    if(expdate>nowMillis){//判断token有效性
                        String username = (String) claims.get("username");
                        String password = (String) claims.get("password");
                        String name = "username";//从本地读取用户名
                        String pword = "password";//从本地读取密码
                        if(name.equals(username) && pword.equals(password)){
                            params="success";//校验成功，有此用户
                        }else{
                            params="用户名或密码错误---faile";
                        }
                    }else{
                        params="timeout";
                    }
                }
            }else{
                params="修改数据--faile";
            }
        }
        return params;
    }
//    public static void main(String[] args) {
//        Map<String,Object> json_py = new HashMap<>();
//        long nowMillis = System.currentTimeMillis();
//        long expMillis = nowMillis + Integer.parseInt("10");//一分钟过期
//        json_py.put("username", "admin1");
//        json_py.put("password", "123456");
//        json_py.put("expDate", expMillis);
//        String token  = TokenUtil.getToken($.json.toJson(json_py).toString());
//        System.out.println(token);
//        String params=TokenUtils.isLogin("eyJhbGciOiJIUzI1NiJ9.eyJwYXNzd29yZCI6IjEyMzQ1NiIsImV4cERhdGUiOjE1MzUwOTQ0MTAzMDcsInVzZXJuYW1lIjoiYWRtaW4xIn0.k1dfe4aqX2zATrAMkOP5C5EEnoxbZKWi-7O9vXLsSsE");
//        System.out.println("返回参数params："+params);
//    }
}
