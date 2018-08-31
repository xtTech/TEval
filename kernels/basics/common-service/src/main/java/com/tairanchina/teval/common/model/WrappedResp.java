package com.tairanchina.teval.common.model;

import com.ecfront.dew.common.Resp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 描述:
 *
 * @author hzds
 * @Create 2018-08 : 15 14:10
 */
public class WrappedResp {
    private String code;
    private String message;
    private static Logger logger = LoggerFactory.getLogger(WrappedResp.class);


    private WrappedResp(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public static WrappedResp create(String code, String message) {
        return new WrappedResp(code, message);
    }

    public static <T> Resp<T> cast(Resp resp) {
        return Resp.customFail(resp.getCode(), resp.getMessage());
    }

    public boolean equalTo(Resp resp) {
        return resp.getCode() != null && code != null && resp.getCode().substring(0, 3).equals(code.substring(0, 3));
    }

    public <T> Resp<T> get() {
        return Resp.customFail(code, message);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public WrappedResp e(Throwable e) {
        return new WrappedResp(code, message + " [" + e.getMessage() + "]");
    }

    public WrappedResp detail(String description, Object... values) {
        StringBuilder descriptionBuilder = new StringBuilder(description);
        for(Object value : values) {
            if(descriptionBuilder.toString().contains("%v")) {
                descriptionBuilder = new StringBuilder(descriptionBuilder.toString().replaceFirst("%v", value == null ? "" : value.toString()));
            } else {
                descriptionBuilder.append(" [").append(value).append("]");
            }
        }

        this.code = this.code.substring(0,3) + toMD5(description);
        this.message = descriptionBuilder.toString();
        return this;
    }

    private static MessageDigest md = null;

    {
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            logger.error("发生异常", e);
        }
    }

    public static String toMD5(String sourceStr) {
        String result = "";
        md.update(sourceStr.getBytes());
        byte b[] = md.digest();
        int i;
        StringBuffer buf = new StringBuffer("");
        for (int offset = 0; offset < b.length; offset++) {
            i = b[offset];
            if (i < 0){
                i += 256;
            }
            if (i < 16) {
                buf.append("0");
            }
            buf.append(Long.toHexString(i));
        }
        result = buf.toString().substring(8, 24);
        return result;
    }
}
