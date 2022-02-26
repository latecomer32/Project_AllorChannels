package com.AllorChannels.Controller.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.AllorChannels.Config.auth.PrincipalDetail;
import com.AllorChannels.Dto.Category;
import com.AllorChannels.Dto.ChannelCategory;
import com.AllorChannels.Dto.ResponseDto;
import com.AllorChannels.Dto.UserCategoryView;
import com.AllorChannels.Service.LeftService;

@RestController
public class LeftApiController {

	@Autowired
	LeftService leftService;

	@PostMapping("/category/saveCategoryName")
	public ResponseDto<Integer> saveCategoryName(@RequestBody Category categoryName,
			@AuthenticationPrincipal PrincipalDetail principal) {

		leftService.saveCategoryName(principal.getNickName(), categoryName.getCategoryName());

		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
	}

	@PostMapping("/category/deleteCategoryName")
	public ResponseDto<Integer> deleteCategoryName(@RequestBody String[] deleteCategoryNoArray) {

		leftService.deleteCategoryName(deleteCategoryNoArray);

		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
	}
	
	@PostMapping("/channel/saveChannelCategoryName")
	public ResponseDto<Integer> channelsaveCategoryName(@RequestBody ChannelCategory channelCategory) {

		leftService.saveChannelCategoryName(channelCategory.getCategoryName(), channelCategory.getTitle());

		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
	}

	@PostMapping("/channel/deleteChannelCategoryName")
	public ResponseDto<Integer> channeldeleteCategoryName(@RequestBody String[] deleteChannelCategoryNoArray) {

		leftService.deleteChannelCategoryName(deleteChannelCategoryNoArray);

		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
	}
	
	@PostMapping("/left/delete/channel")
	public String channeldeleteCategoryName(@RequestBody String deleteChannelName) {

		leftService.deleteChannelName(deleteChannelName);

		return"";
	}

}
