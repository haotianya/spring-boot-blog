package com.haotian.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Getter
@Setter
@Entity
@Validated
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain=true)
@ToString(exclude="authorities")
public class User implements Serializable,UserDetails {
	
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)//自增策略
	private Long id;                                 //用户唯一标识
     
    @Column(nullable=false,length=50)
    @NotEmpty(message="用户名不能为空")
    @Size(min=2,max=50)
    private String name;                             //用户姓名
    
    @Email
    @NotEmpty(message="邮箱不能为空")
    @Column(nullable=false,length=50,unique=true)
    private String email;                            //用户邮箱
    
    @NotEmpty(message="账号不能为空")
    @Column(nullable=false,length=50,unique=true)
    private String username;                         //用户账号
 
    
    @NotEmpty(message="密码不能为空")
	@Column(nullable=false,length=50)
    private String password;                         //用户密码
    
    @Column(length=50)
    private String avator;                           //用户图像地址
    
	@ManyToMany(cascade=CascadeType.DETACH,fetch=FetchType.EAGER)
	@JoinTable(name="user_authority",joinColumns=@JoinColumn(name = "user_id", referencedColumnName = "id"),inverseJoinColumns=@JoinColumn(name = "authority_id", referencedColumnName = "id"))
    private List<Authority> authorities;	

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		//  需将 List<Authority> 转成 List<SimpleGrantedAuthority>，否则前端拿不到角色列表名称
        List<SimpleGrantedAuthority> simpleGrantedAuthorities=new ArrayList<>();
        for(Authority s:this.authorities){
        	simpleGrantedAuthorities.add(new SimpleGrantedAuthority(s.getName()));
        }
		return simpleGrantedAuthorities;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
}
