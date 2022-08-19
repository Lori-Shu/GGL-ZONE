// package com.ggl.cloud.controller;

// import com.ggl.cloud.domain.CommonResult;
// import org.springframework.security.core.Authentication;
// import org.springframework.stereotype.Controller;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestMapping;

// import javax.servlet.http.HttpServletRequest;
// import javax.servlet.http.HttpServletResponse;

// @Controller
// @RequestMapping("oauth")
// public class UserController {
//     @GetMapping("user/getCurrentUser")
//     public Object getCurrentUser(Authentication authentication) {
//         return authentication.getPrincipal();
//     }

//     @PostMapping("loginSuccess")
//     public CommonResult loginSuccess(HttpServletRequest request, HttpServletResponse response) {
//         return new CommonResult(200, "登录成功", null);
//     }

//     @GetMapping("login")
//     public String login() {
//         return "redirect:http://localhost:8080";
//     }

// }
