package com.AllorChannels.Repository.mybatis;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.AllorChannels.Dto.UserDto;
import com.AllorChannels.Repository.HeaderRepository;


@Repository
public class MybatisHeaderRepository implements HeaderRepository {

	private HeaderRepository mapper;
	
	
	@Autowired
	public MybatisHeaderRepository(SqlSession sqlSession) {
		mapper = sqlSession.getMapper(HeaderRepository.class);
	}
	
	@Override
	public void insert(UserDto user) {
		mapper.insert(user);
	}

	
	@Override
	public Optional<UserDto> findByUserId(String userId) {
		//SELECT * FROM user WHERE username = 1?;
			return mapper.findByUserId(userId);
	}

	@Override
	public List<UserDto> getMemberList(int offset, String field, String query, boolean pub, Integer rowNum,
			String order, String desc) {
		return mapper.getMemberList(offset, field, query, pub, rowNum, order, desc);
	}

	@Override
	public int getMemberCount(String field, String query) {
		return mapper.getMemberCount(field, query);
	}

	@Override
	public int saveChannel(String channelName, String nickName) {
		return mapper.saveChannel(channelName, nickName);
	}

	@Override
	public String getChannelName(String nickName) {
		return mapper.getChannelName(nickName);
	}

	@Override
	public void cancelMembership(String nickName) {
		mapper.cancelMembership(nickName);
		
	}
}
