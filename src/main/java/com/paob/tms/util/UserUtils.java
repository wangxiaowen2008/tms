package com.paob.tms.util;

import com.paob.tms.model.User;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class UserUtils {
    public static HttpServletRequest getCurrentHttpServletRequest() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            return attributes.getRequest();
        }
        return null;
    }

    public static User getUser() {
        User user = new User();
        try {
            HttpServletRequest request = getCurrentHttpServletRequest();
            String token = request.getHeader("Authorization");
            token = token.substring(7);
            String tokenStr = token.split("\\.")[0];
//            Claims claims = JwtUtil.parseJWT(token);
//            tokenStr = claims.getSubject();
            String[] split = tokenStr.split("#PAOB#");
            user.setId(Integer.valueOf(split[0]));
            user.setUmNo(split[1]);
            user.setUserName(split[2]);
        } catch (Exception e) {
            user.setUmNo("system");
        }
        return user;
    }
}