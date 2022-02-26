package com.AllorChannels.Service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.AllorChannels.Dto.Board;
import com.AllorChannels.Dto.Channel;
import com.AllorChannels.Repository.BoardRepository;

@Service
public class BoardServiceImp implements BoardService {

	@Autowired
	BoardRepository boardRepository;

	@Override
	public void saveTheWriting(Board board, String username) {
		System.out.println("@Service\r\n" + "public class BoardServiceImp implements BoardService{\r\n" + "");
		board.setNickName(username);
		boardRepository.saveTheWriting(board);

	}

	@Override
	public List<Board> getWritingList(int page, String field, String query, boolean pub, int size, String order,
			String desc, String categoryName, String nickName, boolean loginCheck, String uri, String channelName, Integer no) {

		int offset = 0 + (page - 1) * size;
		return boardRepository.getWritingList(offset, field, query, pub, size, order, desc, categoryName, nickName,
				loginCheck, uri, channelName, no);
	}

	@Override
	public void deleteWritingList(int no) {
		boardRepository.deleteWritingList(no);

	}

	@Override
	public int getWritingCount(String field, String query) {
		return boardRepository.getWritingCount(field, query);
	}

	@Override
	public Board getWritingDetail(int no) {
		return boardRepository.getWritingDetail(no);

	}

	@Override
	public List<Board> getChannelWritingList(int size, List<Channel> getChannelList) {
		// TODO Auto-generated method stub
		return boardRepository.getChannelWritingList(size, getChannelList);
	}

	@Override
	public void updateWritingList(Board board) {
		 boardRepository.updateWritingList(board);
	}

}
