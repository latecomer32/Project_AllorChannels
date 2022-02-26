package com.AllorChannels.Repository;

import java.util.List;

import com.AllorChannels.Dto.Channel;
import com.AllorChannels.Dto.ChannelCategory;

public interface ChannelRepository {

	List<Channel> getChannelList(int offset, int size, String query);

	int getChannelCount(String field, String query);


}
