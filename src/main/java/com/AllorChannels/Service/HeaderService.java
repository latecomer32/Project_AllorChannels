package com.AllorChannels.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.AllorChannels.Dto.UserDto;


public interface HeaderService {

	void join(UserDto user);

	List<UserDto> getMemberList(Integer page, String field, String query, boolean pub, Integer rowNum, String order,
			String desc);

	int getMemberCount(String field, String query);

	int saveChannel(String channelName, String nickName);

	String getChannelName(String nickName);

	void cancelMembership(String nickName);

}
