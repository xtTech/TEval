package com.tairanchina.teval.common.constant;

import com.tairanchina.teval.common.model.WrappedResp;

import java.util.regex.Pattern;

/**
 * 描述:
 *
 * @author hzds
 * @Create 2018-08 : 14 10:56
 */
public class Global {
    /**
     * 验证码标识缓存
     */
    public static final String P_SIMPLE_CAPTCHA_ID = "cpt:sp:";
    public static final String V_REGISTER_CODE_ID = "rgs:vp:";
    public static final String V_RESETPWD_CODE_ID = "rst:vp:";
    public static final String T_LOGIN_TOKEN_IDENT = "tk:";

    /**
     * 邮件激活URL接口地址
     */
    public static final String ACTIVE_ACCOUNT_URL = "http://127.0.0.1:8080/register/active";
    /**
     * 密码找回URL接口地址
     */
    public static final String RESET_PASSWORD_URL = "http://127.0.0.1:8080/password/reset";

    /**
     * 系统登录秘钥
     */
    public static final String LOGIN_SECRET_KEY = "RaQ4WLAa";

    /**
     * RSA加密算法
     */
    public static final String ALGORITHM_RSA = "RSA";

    /**
     * 系统管理员ID
     */
    public static final String SYSTEM_CORE_ID = "2953508b98b54b2c8a9db45a2df73d4a";
    public static class Cookie {
        public static final String CAPTCHA = "sid_cpt";
    }

    /**
     * 错误结果定义(Resp结构)
     */
    public static class RespError {
        /**
         * Used in Controller Layer
         */
        public static final WrappedResp ARGUMENT_NOT_SET = WrappedResp.create("201", "参数未设置");
        public static final WrappedResp ILLEGAL_ARGUMENT = WrappedResp.create("202", "非法参数");

        /**
         * Used in Service Layer
         */
        public static final WrappedResp SYSTEM_ERROR = WrappedResp.create("203", "系统错误");
        public static final WrappedResp EMPTY_VALUE = WrappedResp.create("204", "空值");
        public static final WrappedResp BAD_ARGUMENT = WrappedResp.create("205", "问题参数");

        public static final WrappedResp DATA_BROKEN = WrappedResp.create("206", "数据已损坏");
    }

    public static class Regex {
        public static final Pattern EMAIL_REGEX = Pattern.compile(RegexString.EMAIL_REGEX);
    }

    public static class RegexString {
        public static final String EMAIL_REGEX = "^[a-zA-Z0-9_.-]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*\\.[a-zA-Z0-9]{2,6}$";
        public static final String PASSWORD_REGEX = "^(?![\\d]+$)(?![a-zA-Z]+$)(?![^\\da-zA-Z]+$).{6,20}$";
    }

    public static class ClaimsKey{
        public static final String EMAIL = "email";
        public static final String PASSWORD ="enc_password";
        public static final String ACCOUNT = "account_id";
        public static final String IDENT = "identity_id";

     }

    /**
     * 验证码相关参数
     */
    public static class CodeParams{
        public static final Integer CODE_LENGTH = 4;
        public static final Integer CODE_ACTIVE_MINUTES = 1;
        public static final Integer CODE_WIDTH = 100;
        public static final Integer CODE_HEIGHT = 40;
     }

    /**
     * 请求头信息
     */
    public static class HttpHeader {
        public static final String AUTHORIZATION = "Authorization";
    }
}
