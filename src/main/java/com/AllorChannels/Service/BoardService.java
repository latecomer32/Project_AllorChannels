package com.AllorChannels.Service;

import java.util.List;
import java.util.Map;

import com.AllorChannels.Dto.Board;
import com.AllorChannels.Dto.Channel;


public interface BoardService {



	void saveTheWriting(Board board, String username);

	List<Board> getWritingList(int page, String field, String query, boolean pub, int size, String order, String desc, String categoryName, String nickName, boolean loginCheck, String uri, String channelName, Integer no);

	void deleteWritingList(int no);

	int getWritingCount(String field, String query);

	Board getWritingDetail(int no);

	List<Board> getChannelWritingList(int size, List<Channel> getChannelList);

	void updateWritingList(Board board);

}
