package com.AllorChannels.Config.auth;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.AllorChannels.Dto.UserDto;

//스프링 시큐리티가 로그인 요청을 가로채서 로그인을 진행하고 완료가 되면 UserDetails 타입의 오브젝트를
//스프링 시큐리티의 고유한 세션저장소에 저장을 해둔다.

public class PrincipalDetail implements UserDetails {

	private UserDto user; // 객체를 품고 있는걸 콤포지션이라 한다.

	public PrincipalDetail(UserDto user) {
		this.user = user;

	}
	
	public String getNickName() {
		// TODO Auto-generated method stub
		return user.getNickName();
	}
	
	
	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return user.getUserId();
	}

	// 계정이 만료되지 않았는지 리턴한다. (true:만료안됨)
	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	// 계정이 잠겨있지 않았는지 리턴한다. (true:잠기지 않음)
	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	// 비밀번호가 만료되지 않았는지 리턴한다.(true:만료안됨)
	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	// 계정이 활성화(사용가능)인지 리턴한다.(true:활성화)
	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

	// 계정이 갖고있는 권한 목록을 리턴한다. (권한이 여러개 있을 수 있어서 루프를 돌아야 하는데 우리는 한개만)
	// 권한이 여러개면 for문을 돌려야 함.
	// Collection<? extends GrantedAuthority> 콜렉션 타입에 GrantedAuthority를 상속받아야한다.
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {

		Collection<GrantedAuthority> collectors = new ArrayList<>();
		collectors.add(() -> {

			return "ROLE_" + user.getRole(); // 스프링에서 롤 받을때 ROLE_USER라고 정해진 규칙이다.
		});

		return collectors;
	}

}
