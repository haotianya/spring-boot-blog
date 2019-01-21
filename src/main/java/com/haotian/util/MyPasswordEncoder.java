package com.haotian.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class MyPasswordEncoder {
      public static String encode(String password){
    	  PasswordEncoder passwordEncoder=new BCryptPasswordEncoder();
    	  return passwordEncoder.encode(password);
      }
}
