package com.haotian.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.haotian.domain.User;
import com.haotian.vo.Result;

public interface UserService {
    public User findById(Long id);
    public Page<User> findByNameLike(String name,Pageable pageabl);
    public void removeUser(Long id);
    public User saveUser(User user);
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
    public Result login(User user);
}
