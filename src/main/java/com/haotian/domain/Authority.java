package com.haotian.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.validator.constraints.NotEmpty;

import lombok.Data;

/**
 * 权限
 * @author 龚昊天
 *
 */
@Entity
@Data
public class Authority implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)//自增长策略
	private Long id;//用户的唯一标识
	
	@NotEmpty
	private String name;
}
