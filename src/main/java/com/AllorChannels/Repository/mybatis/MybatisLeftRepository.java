package com.AllorChannels.Repository.mybatis;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.AllorChannels.Dto.Category;
import com.AllorChannels.Dto.ChannelCategory;
import com.AllorChannels.Dto.UserCategoryView;
import com.AllorChannels.Repository.LeftRepository;

@Repository
public class MybatisLeftRepository implements LeftRepository {

	private LeftRepository mapper;

	@Autowired
	public MybatisLeftRepository(SqlSession sqlSession) {
		mapper = sqlSession.getMapper(LeftRepository.class);
	}

	@Override
	public List<Category> getCategoryList(String nickName) {
		return mapper.getCategoryList(nickName);
	}

	@Override
	public void saveCategoryName(String nickName, String categoryName) {
		mapper.saveCategoryName(nickName, categoryName);
	}

	@Override
	public void deleteCategoryName(String[] deleteCategory) {
		mapper.deleteCategoryName(deleteCategory);
	}

	@Override
	public List<ChannelCategory> getChannelCategoryList(String channelName) {
		return mapper.getChannelCategoryList(channelName);
	}

	@Override
	public void saveChannelCategoryName(String categoryName, String title) {
		mapper.saveChannelCategoryName(categoryName, title);
	}

	@Override
	public void deleteChannelCategoryName(String[] deleteChannelCategoryNoArray) {
		mapper.deleteChannelCategoryName(deleteChannelCategoryNoArray);
	}

	@Override
	public void deleteChannelName(String deleteChannelName) {
		mapper.deleteChannelName(deleteChannelName);
		
	}
}
