package com.haotian.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.haotian.domain.User;
import com.haotian.repository.UserRepository;
import com.haotian.service.UserService;
import com.haotian.util.MyPasswordEncoder;
import com.haotian.vo.Result;

@Service
public class UserServiceImpl implements UserService,UserDetailsService{
    
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public User findById(Long id) {
        User user=userRepository.findOne(id);
		return user;
	}

	@Override
	public Page<User> findByNameLike(String name, Pageable pageable) {
		name="%"+name+"%";
        Page<User> users=userRepository.findByNameLike(name, pageable);
		return users;
	}


	@Override
	@Transactional
	public void removeUser(Long id) {
        userRepository.delete(id);
	}

	@Override
	@Transactional
	public User saveUser(User user) {
        return userRepository.save(user);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return userRepository.findByUsername(username);
	}

	@Override
	public Result login(User user) {
        User rightUser=userRepository.findByUsername(user.getUsername());
		if(user.getUsername()==null||"".equals(user.getPassword())){
			return Result.error("用户名不能为空");
		}
		if(user.getPassword()==null||"".equals(user.getPassword())){
			return Result.error("密码不能为空");
		}
		if(rightUser==null){
			return Result.error("用户名或者密码错误");
		}else{
			String encodePassword=MyPasswordEncoder.encode(user.getPassword());
			if(!encodePassword.equals(rightUser.getPassword())){
				return Result.error("用户名或者密码错误");
			}
		}
        return Result.ok();
	}

}
