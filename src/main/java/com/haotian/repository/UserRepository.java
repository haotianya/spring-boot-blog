package com.haotian.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.haotian.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {
      public Page<User> findByNameLike(String name,Pageable pageable);
      public User findByUsername(String username);
}
