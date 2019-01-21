package com.haotian.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haotian.domain.Authority;
import com.haotian.repository.AuthorityRepository;
import com.haotian.service.AuthorityService;

@Service
public class AuthorityServiceImpl implements AuthorityService {

	@Autowired
	private AuthorityRepository authorityRespository;
	
	@Override
	@Transactional
	public Authority findById(Long id) {
	    return authorityRespository.findOne(id);
	}

}
