package com.tairanchina.teval.service.admin.service.impl;

import com.ecfront.dew.common.$;
import com.ecfront.dew.common.Resp;
import com.tairanchina.csp.dew.Dew;
import com.tairanchina.teval.common.constant.Global;
import com.tairanchina.teval.service.admin.dto.AccountTokenRequestDTO;
import com.tairanchina.teval.service.admin.dto.ValidateTokenResponseDTO;
import com.tairanchina.teval.service.admin.service.AccountTokenService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.Base64Codec;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;

/**
 * 描述:
 *
 * @author hzds
 * @Create 2018-08 : 24 18:01
 */
@Service
public class AccountTokenServiceImpl implements AccountTokenService {

    @Override
    public Resp<String> createAccountToken(AccountTokenRequestDTO requestDTO) {

        //设置过期时间
        Date expDateAt = new Date(requestDTO.getExpMinutes() * 60 * 1000 + System.currentTimeMillis());
        String signKey = Global.LOGIN_SECRET_KEY;
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(signKey);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
        JwtBuilder builder = Jwts.builder().setClaims(new HashMap<String, Object>() {{
            put(Global.ClaimsKey.EMAIL, requestDTO.getEmail());
            put(Global.ClaimsKey.ACCOUNT, requestDTO.getAccountId());
            put(Global.ClaimsKey.IDENT, requestDTO.getIdentId());
            put(Global.ClaimsKey.PASSWORD, requestDTO.getEncPassword());
        }}).setExpiration(expDateAt).setIssuedAt(new Date());
        //缓存登录用户信息
        Dew.cluster.cache.setex(Global.T_LOGIN_TOKEN_IDENT + requestDTO.getEmail(), $.json.toJsonString(requestDTO), requestDTO.getExpMinutes() * 60);
        JwtBuilder jwtBuilder = builder.signWith(signatureAlgorithm, signingKey);
        return Resp.success(jwtBuilder.compact());
    }

    @Override
    public Resp<ValidateTokenResponseDTO> validateToken(String token) {
        if (token.split("\\.").length != 3) {
            return Global.RespError.BAD_ARGUMENT.detail("无效的token").get();
        }
        String header = token.split("\\.")[0];
        String sign = token.split("\\.")[2];
        String payload = token.split("\\.")[1];
        String headerNew = getToken(Base64Codec.BASE64URL.decodeToString(payload)).split("\\.")[0];
        String signNew = getToken(Base64Codec.BASE64URL.decodeToString(payload)).split("\\.")[2];
        //进行安全校验（头部和签名）
        if (!header.equals(headerNew) || !sign.equals(signNew)) {
            return Global.RespError.BAD_ARGUMENT.detail("token不合法").get();
        }
        Claims claims = null;
        try {
            claims = Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(Global.LOGIN_SECRET_KEY)).parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException e) {
            return Global.RespError.BAD_ARGUMENT.detail("token已失效").get();
        }
        if (claims == null) {
            return Global.RespError.BAD_ARGUMENT.detail("token已损坏").get();
        }
        String jsonObj = Dew.cluster.cache.get(Global.T_LOGIN_TOKEN_IDENT + claims.get(Global.ClaimsKey.EMAIL));
        if (jsonObj == null || jsonObj == "") {
            return Global.RespError.BAD_ARGUMENT.detail("token已失效").get();
        }
        AccountTokenRequestDTO tokentDTO = $.json.toObject(jsonObj, AccountTokenRequestDTO.class);

        String email = (String) claims.get(Global.ClaimsKey.EMAIL);
        String password = (String) claims.get(Global.ClaimsKey.PASSWORD);

        if (!tokentDTO.getEmail().equals(email) || !tokentDTO.getEncPassword().equals(password)) {
            return Global.RespError.BAD_ARGUMENT.detail("token验证失败").get();
        }
        return Resp.success(new ValidateTokenResponseDTO()
                .setAccountId(tokentDTO.getAccountId())
                .setIdentId((Integer)claims.get(Global.ClaimsKey.IDENT))
                .setIssuedAt(claims.getIssuedAt())
                .setExpiresAt(claims.getExpiration()));
    }

    private String getToken(String jsonStr) {
        String signKey = Global.LOGIN_SECRET_KEY;
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(signKey);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
        JwtBuilder builder = Jwts.builder()
                .setPayload(jsonStr)
                .signWith(signatureAlgorithm, signingKey);
        return builder.compact();
    }

}
