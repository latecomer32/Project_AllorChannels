package com.AllorChannels.Service;

import java.util.List;

import com.AllorChannels.Dto.Category;
import com.AllorChannels.Dto.ChannelCategory;
import com.AllorChannels.Dto.UserCategoryView;
import com.AllorChannels.Dto.UserDto;

public interface LeftService {

	void saveCategoryName(String nickName, String string);
	
	void deleteCategoryName(String[] deleteCategory);
	
	List<Category> getCategoryList(String nickName);


	void saveChannelCategoryName(String categoryName, String title);

	void deleteChannelCategoryName(String[] deleteChannelCategoryNoArray);

	List<ChannelCategory> getChannelCategoryList(String channelName);

	void deleteChannelName(String deleteChannelName);

}
