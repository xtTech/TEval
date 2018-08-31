package com.tairanchina.teval.service.admin.intercept;

import com.ecfront.dew.common.$;
import com.ecfront.dew.common.Resp;
import com.tairanchina.teval.common.constant.Global;
import com.tairanchina.teval.common.context.UserStatusContext;
import com.tairanchina.teval.common.domain.core.ext.entity.AccountExt;
import com.tairanchina.teval.service.admin.dto.ValidateTokenResponseDTO;
import com.tairanchina.teval.service.admin.service.AccountService;
import com.tairanchina.teval.service.admin.service.AccountTokenService;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 描述:
 *
 * @author hzds
 * @Create 2018-08 : 28 15:34
 */
public class ValidPreInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(ValidPreInterceptor.class);
    @Autowired
    private AccountTokenService tokenService;
    @Autowired
    private AccountService accountService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        if (o instanceof HandlerMethod) {
            HandlerMethod method = (HandlerMethod) o;
            if (method.hasMethodAnnotation(Exposed.class)) {
                return true;
            }
            String token = request.getHeader(Global.HttpHeader.AUTHORIZATION);
            if (StringUtils.isEmpty(token)) {
                returnError(response, Resp.error(new Resp() {{
                    setCode(HttpStatus.SC_FORBIDDEN + "");
                    setMessage("The token is not existed");
                }}));
                return false;
            }
            Resp<ValidateTokenResponseDTO> valideTokenResp = tokenService.validateToken(token);
            if (!valideTokenResp.ok()) {
                returnError(response, valideTokenResp);
                return false;
            }
            ValidateTokenResponseDTO tokenDTO = valideTokenResp.getBody();
            Resp<AccountExt> extResp = accountService.findExtByPrimaryKey(tokenDTO.getAccountId());
            if (!extResp.ok()) {
                returnError(response, extResp);
                return false;
            }
            AccountExt accountExt = extResp.getBody();
            UserStatusContext.instance().setAccount(accountExt);
            UserStatusContext.instance().setIdentity(accountExt.getIdentity());
            return true;
        }
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object o, Exception e) throws Exception {
        UserStatusContext.clear();
    }

    private void returnError(HttpServletResponse response, Resp errorResp) {
        logger.info($.json.toJsonString(errorResp));
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=utf-8");
        PrintWriter writer = null;
        try {
            writer = response.getWriter();
            writer.print($.json.toJsonString(errorResp));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) writer.close();
        }

    }
}
