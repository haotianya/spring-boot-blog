package com.haotian.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.haotian.domain.Authority;
import com.haotian.domain.User;
import com.haotian.service.AuthorityService;
import com.haotian.service.UserService;
import com.haotian.util.ConstraintViolationExceptionHandler;
import com.haotian.vo.Result;

/**
 * 用户控制器
 * @author 龚昊天
 *
 */
@Controller
@RequestMapping(value="/users")
public class UserController {
    
	@Autowired
	private UserService userService;
	
	@Autowired
	private AuthorityService authorityService;
	/*
	 * 来到查询所有用户页面
	 */
	@GetMapping
	public ModelAndView list(@RequestParam(value="async",required=false)boolean async,
			                 @RequestParam(value="pageIndex",required=false,defaultValue="0")Integer pageIndex,
			                 @RequestParam(value="pageSize",required=false,defaultValue="10")Integer pageSize,
			                 @RequestParam(value="name",required=false,defaultValue="")String name,Model model){
        Pageable pageable=new PageRequest(pageIndex,pageSize);
        Page<User> page=userService.findByNameLike(name, pageable);
		model.addAttribute("page", page);
		model.addAttribute("userList", page.getContent());
		for(User user:page.getContent()){
			System.out.println(user);
		}
        return new ModelAndView(async==true?"user/list::#mainContainerRepleace":"users/list","userModel",model);
	}
	
	@GetMapping("/add")
	public String toAddPage(){
		return "users/addOrEdit";  
	}
	
	@GetMapping("/edit/{id}")
	public String toUpdatePage(@PathVariable("id")Long id,Model model){
		User user=null;
		try{
		user=userService.findById(id);
		}catch(ConstraintViolationException e){
			e.printStackTrace();
		}
		user.setPassword(null);
		model.addAttribute("user", user);
		System.out.println("aaaaaaaaaaaaaaaaaaaaa");
		return "users/addOrEdit";
	}
	
	@PostMapping("/user")
	public ModelAndView addUser(User user,Long authorityId){
		List<Authority> authorities=new ArrayList<>();  
		authorities.add(authorityService.findById(authorityId));
		user.setAuthorities(authorities);
		
		PasswordEncoder passwordEncoder=new BCryptPasswordEncoder();
		String encodePassword=passwordEncoder.encode(user.getPassword());
		user.setPassword(encodePassword);
		try{
			userService.saveUser(user);
		}catch(ConstraintViolationException e){
			e.printStackTrace();
			
		}
		return new ModelAndView("redirect:/users");
	}
	@PutMapping("/user")
	public ModelAndView updateUser(User user,Long authorityId){
		Authority authority=authorityService.findById(authorityId);
		List<Authority> list=new ArrayList<>();
		list.add(authority);
		user.setAuthorities(list);
		
		String updatePassword=user.getPassword();
		PasswordEncoder passwordEncoder=new BCryptPasswordEncoder();
		String updateEncoderPassword=passwordEncoder.encode(updatePassword);
		User originUser=userService.findById(user.getId());
		if(!originUser.getPassword().equals(updateEncoderPassword)){
			user.setPassword(updateEncoderPassword);
		}
		try{
		userService.saveUser(user);
		}catch(ConstraintViolationException e){
			return new ModelAndView("redirect:/users","error",Result.error(ConstraintViolationExceptionHandler.getMessage(e)));
		}
		return new ModelAndView("redirect:/users");
	}
	
	@DeleteMapping(value="/{id}")
	public String deleteId(@PathVariable("id")Long id){
		userService.removeUser(id);
	    return "redirect:/users";
	}
}
