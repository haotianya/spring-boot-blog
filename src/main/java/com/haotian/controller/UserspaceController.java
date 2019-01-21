package com.haotian.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.haotian.service.UserService;

@RestController
@RequestMapping("/u")
public class UserspaceController {

	@Autowired
	private UserService userService;
	
	@GetMapping("/{username}")
	public ModelAndView toBlog(@PathVariable("username")String username){
		return new ModelAndView("userspace/blog.html"); 
	}
}
