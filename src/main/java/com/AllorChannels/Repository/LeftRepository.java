package com.AllorChannels.Repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.AllorChannels.Dto.Board;
import com.AllorChannels.Dto.Category;
import com.AllorChannels.Dto.ChannelCategory;
import com.AllorChannels.Dto.UserCategoryView;
import com.AllorChannels.Dto.UserDto;

public interface LeftRepository {

	List<Category> getCategoryList(String nickName);

	void saveCategoryName(String nickName,String categoryName);

	void deleteCategoryName(String[] deleteCategory);

	List<ChannelCategory> getChannelCategoryList(String channelName);

	void saveChannelCategoryName(String categoryName, String title);

	void deleteChannelCategoryName(String[] deleteChannelCategoryNoArray);

	void deleteChannelName(String deleteChannelName);

}
