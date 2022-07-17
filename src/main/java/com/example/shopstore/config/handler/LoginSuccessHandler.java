package com.example.shopstore.config.handler;

import com.example.shopstore.common.RoleCommon;
import com.example.shopstore.dto.UserDTO;
import com.example.shopstore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    @Autowired
    private UserService userDetailsService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        UserDTO userDTO = this.userDetailsService.getUserByUsername(authentication.getName());
        request.getSession().setAttribute("currentUser", userDTO);
        if(userDTO.getRole().equals(RoleCommon.ROLE_ADMIN)) {
            response.sendRedirect(request.getContextPath() + "/admin");
        } else {
            response.sendRedirect(request.getContextPath() + "/");
        }
    }

}
