package com.haotian.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.haotian.domain.Authority;
import com.haotian.domain.User;
import com.haotian.service.AuthorityService;
import com.haotian.service.UserService;
import com.haotian.vo.Result;
/**
 * 主控制器
 * @author 龚昊天
 *
 */
@Controller
public class MainController {
    
	private static final Long ROLE_ID=2L;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AuthorityService authorityService;
	
	@GetMapping(value="/")
	public String main(){
		return "index";
	}
	
	@GetMapping(value="/index")
	public String index(){
		return "redirect:/index";
	}
	
	@GetMapping(value="/login")
	public String tpLoginPage(){
		return "login";
	}
	
    @PostMapping(value="/login")
    public String login(User user){
    	Result result=userService.login(user);
        if(result.getCode()!=200){
        	return "/login-error";
        }
        return "/index";
    }
	
	@GetMapping(value="/login-error")
	public String loginError(Model model){
		model.addAttribute("loginError", true);
		model.addAttribute("errorMsg", "用户登陆失败，用户名或密码错误");
		return "login";
	}
	
	@GetMapping(value="/register")
	public String register(){
		return "register";
	}
	@PostMapping(value="/register")
	public String addUser(User user){
		PasswordEncoder passwordEncoder=new BCryptPasswordEncoder();
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		List<Authority> list=new ArrayList<>();
		list.add(authorityService.findById(ROLE_ID));
		user.setAuthorities(list);
		userService.saveUser(user);
		return "redirect:/users";
	}
	
	@GetMapping(value="/search")
	public String search(){
		return "search";
	}
	
}
