package com.example.shopstore.config.handler;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component("logoutHandler")
public class LogoutHandler implements org.springframework.security.web.authentication.logout.LogoutSuccessHandler {

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        request.getSession().removeAttribute("currentUser");
        response.sendRedirect(request.getContextPath() + "/login?logout");
    }
}
