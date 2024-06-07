//package com.example.project.authentication;
//
//import org.springframework.security.core.Authentication;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
//import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
//import org.springframework.web.bind.annotation.CrossOrigin;
//
//@CrossOrigin(origins = "http://localhost:3000") // Allow requests from the React app
//public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {
//
//    @Override
//    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
//        System.out.println("Logout successful. Authentication: " + authentication);
//        response.sendRedirect("/login?logout");
//    }
//
//}