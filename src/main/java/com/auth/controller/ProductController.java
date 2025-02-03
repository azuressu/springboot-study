package com.auth.controller;

import com.auth.entity.User;
import com.auth.security.UserDetailsImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api")
public class ProductController {

    @GetMapping("/products")
    public String getProducts(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        // Authentication의 Principal
        User user = userDetails.getUser();
        System.out.println("user.getUsername() = " + user.getUsername());

        return "redirect:/";
    }
}
