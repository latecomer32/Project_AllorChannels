package com.AllorChannels.Config;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.web.filter.CharacterEncodingFilter;

import com.AllorChannels.Config.auth.PrincipalDetailService;

import lombok.RequiredArgsConstructor;
@RequiredArgsConstructor
@Configuration //빈등록(IOC관리)
@EnableWebSecurity // 시큐리티 필터가 등록 된다
@EnableGlobalMethodSecurity(prePostEnabled = true) // 특정 주소로 접근을 하면 권한 및 인증을 미리 체크
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	

	/* 로그인 실패 핸들러 의존성 주입 */ 
	private final AuthenticationFailureHandler customFailurHandler;

	   
	@Autowired
	private PrincipalDetailService principalDetailService;

	

	@Bean // IOC가 됨, new BCryptPasswordEncoder()이 값을 스프링이 관리하게 됨
	public BCryptPasswordEncoder encodePWD() { //encodePWD() 호출하면 new BCryptPasswordEncoder() 생성
		return new BCryptPasswordEncoder(); //시큐리티가 들고 있는 암호화 함수
	}


	 @Override // js, css, image 설정은 보안 설정의 영향 밖에 있도록 만들어주는 설정.
	 	public void configure(WebSecurity web) throws Exception {
	        web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
	        
	 }
		/* .antMatchers("/favicon.ico", "/resources/**", "/error") */
	 
	 //configure(AuthenticationManagerBuilder auth) : 해쉬로 암호화를 하게 되면
	 //DB에는 암호화된 password,
	 //로그인할떄에는 암호화되지 않은 password가 입력이 되기 떄문에
	 //이 두 값을 비교하여 맞는지 아닌지를 확인해주는 메서드이다.
	
		  @Override protected void configure(AuthenticationManagerBuilder auth) throws Exception {
			  auth.userDetailsService(principalDetailService)
		  .passwordEncoder(encodePWD()); }
		 
	
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http
		.csrf().disable() //csrf 토큰 비활성화
		.authorizeRequests()
		.antMatchers("/","/fontawesome-free-5.15.4-web/**","/logout", "/auth/**", "/js/**", "/css/**", "/image/**", "/resources/**", "/error", "/index/**")
		.permitAll()
		.anyRequest()
		.authenticated()
	.and()
		.formLogin()
		.usernameParameter("loginUserId")//.JSP에서 전송오는 name="loginUserId"이면 default값인 username과 다르므로 해당 설정해줘야함.
		.passwordParameter("loginPpassword")
		.loginPage("/index") // 인증이 필요한 곳으로 요청오면 해당 주소로 이동
		.loginProcessingUrl("/auth/loginProc") //해당 주소에서 로그인 처리, 해당 페이지 안만들어도 시큐리티가 가로채서 username 확인하게된다.
		.failureHandler(customFailurHandler) // 실패 핸들러
		.defaultSuccessUrl("/index");
	
	}
}
