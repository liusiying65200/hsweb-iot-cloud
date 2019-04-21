package net.hualisheng.shiro.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("/user")
public class ShiroController {
    @PostMapping("/login")
    public String login(String username, String passwd, Model model){
        if (StringUtils.isEmpty(username)){
            model.addAttribute("msg","用户名称不能为空" );
            return "err";
        }
        if (StringUtils.isEmpty(passwd)){
            model.addAttribute("msg","密码不能为空" );
            return "err";
        }
        Subject subject = SecurityUtils.getSubject();
        try {
            UsernamePasswordToken token=new UsernamePasswordToken(username,passwd);
            token.setRememberMe(true);
            subject.login(token);
        }catch (UnknownAccountException e){
            model.addAttribute("msg", "账户不存在");
            return "err";
        }catch (IncorrectCredentialsException e){
            model.addAttribute("msg","密码不正确" );
            return "err";
        }
        return "home";
    }
    @GetMapping("/goLogin")
    public String goLoginPage(){
        return "login";
    }
    @GetMapping("/logout")
    public String logout(){
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()){
            subject.logout();
        }
        return "home";
    }
    @GetMapping("/goAdmin")
    public String goAdminPage(){
        return "admin";
    }
    @GetMapping("/error")
    public String error(Model model){
        model.addAttribute("msg", "没有权限");
        return "error";
    }
    @GetMapping("index")
    public void index(HttpServletRequest httpServletRequest, HttpServletResponse response){
        try {
            response.sendRedirect("http://localhost:8080");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
