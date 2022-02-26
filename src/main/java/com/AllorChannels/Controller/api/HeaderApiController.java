package com.AllorChannels.Controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.AllorChannels.Config.auth.PrincipalDetail;
import com.AllorChannels.Dto.ResponseDto;
import com.AllorChannels.Dto.UserDto;
import com.AllorChannels.Service.HeaderService;

@RestController
public class HeaderApiController {

	@Autowired
	private HeaderService headerService;

	@PostMapping("/auth/joinForm")
	public ResponseDto<Integer> join(@RequestBody UserDto user) {
	
		headerService.join(user);
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
	}
	
	@PostMapping("/header/save/channel")
	public ResponseDto<Integer> saveChannel(@RequestBody String channelName,
			@AuthenticationPrincipal PrincipalDetail principal) {
		headerService.saveChannel(channelName, principal.getUsername() );
		
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
	}
	
	@PostMapping("/header/cancel/membership")
	public ResponseDto<Integer> cancelMembership(
		
			@AuthenticationPrincipal PrincipalDetail principal
		) {
		headerService.cancelMembership(principal.getNickName());
		
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
	}
}
