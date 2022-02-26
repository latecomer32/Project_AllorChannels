package com.AllorChannels.Repository;

import java.util.List;
import java.util.Map;

import com.AllorChannels.Dto.Board;
import com.AllorChannels.Dto.Channel;

public interface BoardRepository {

	void saveTheWriting(Board board);

	List<Board> getWritingList(int offset, String field, String query, boolean pub, int size, String order, String desc, String categoryName, String nickName, boolean loginCheck, String uri, String channelName, Integer no);

	int getWritingCount(String field, String query);

	int deleteWritingList(int no);

	Board getWritingDetail(int no);

	List<Board> getChannelWritingList(int size, List<Channel> getChannelList);

	int updateWritingList(Board board);
}