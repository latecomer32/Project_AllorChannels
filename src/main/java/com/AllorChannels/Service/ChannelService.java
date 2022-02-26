package com.AllorChannels.Service;

import java.util.List;

import com.AllorChannels.Dto.Channel;

public interface ChannelService {

	List<Channel> getChannelList(int page, int size, String query);

	int getChannelCount(String field, String query);


}
