package com.tairanchina.teval.service.crypto.com.interceptor;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.ecfront.dew.common.$;
import com.ecfront.dew.common.Resp;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 说明:
 *
 * @author 薛文毅
 *         Created by 薛文毅 on 2018/8/11.
 */
@Component
public class IdentifyInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {

        if(o instanceof HandlerMethod) {
            HandlerMethod method = (HandlerMethod) o;

            if(method.hasMethodAnnotation(Identify.class)) {
                String token = httpServletRequest.getHeader("Authorization");
                token = token.replaceFirst("^Bearer\\s+(?<token>.+)$", "${token}");
                if(StringUtils.isEmpty(token)) {
                    httpServletResponse.setHeader("Content-Type", MediaType.APPLICATION_JSON_UTF8_VALUE);
                    httpServletResponse.sendError(HttpServletResponse.SC_OK,
                            $.json.toJsonString(Resp.unAuthorized("Permission denied")));
                    return false;
                }
                DecodedJWT decodedToken = JWT.decode(token);
            }

            return true;
        }

        return false;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
