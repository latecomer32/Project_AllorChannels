package com.AllorChannels.Repository.mybatis;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.AllorChannels.Dto.Board;
import com.AllorChannels.Dto.Channel;
import com.AllorChannels.Dto.ChannelCategory;
import com.AllorChannels.Repository.BoardRepository;
import com.AllorChannels.Repository.ChannelRepository;

@Repository
public class MybatisChannelRepository implements ChannelRepository {

	private ChannelRepository mapper;
	
	@Autowired
	public MybatisChannelRepository(SqlSession sqlSession) {
		mapper = sqlSession.getMapper(ChannelRepository.class);
	}


	@Override
	public List<Channel> getChannelList(int offset, int size, String query) {
		return mapper.getChannelList(offset, size, query);
	}


	@Override
	public int getChannelCount(String field, String query) {
		return mapper.getChannelCount(field, query);
	}
	


}
