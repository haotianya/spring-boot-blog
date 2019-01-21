package com.haotian.vo;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    private String msg;
    private Integer code;
    private Object body;
    public static Result ok(){
    	return new Result("成功",200,null);
    }
    public static Result ok(String msg,Object obj){
    	return new Result(msg,200,obj);
    }
    
    public static Result error(){
    	return new Result("失败",400,null);
    }
    public static Result error(String msg){
    	return new Result(msg,400,null);
    }
}
