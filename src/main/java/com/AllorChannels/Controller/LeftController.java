
package com.AllorChannels.Controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.AllorChannels.Config.auth.PrincipalDetail;
import com.AllorChannels.Dto.Board;
import com.AllorChannels.Dto.Category;
import com.AllorChannels.Service.BoardService;
import com.AllorChannels.Service.HeaderService;
import com.AllorChannels.Service.LeftService;


@Controller
public class LeftController {

	@Autowired
	BoardService boardService;

	@Autowired
	public LeftService leftService;
	
	@Autowired
	HeaderService headerService;

	
	public String saveCategoryName(
			@PathVariable(required = false) Integer no,
			@RequestParam(name = "c", required = false, defaultValue = "") String categoryName,
			@RequestParam(name = "p", required = false, defaultValue = "1") int page,
			@RequestParam(name = "f", required = false, defaultValue = "title") String field,
			@RequestParam(name = "q", required = false, defaultValue = "") String query,
			@RequestParam(name = "r", required = false, defaultValue = "15") int size,
			@RequestParam(name = "desc", required = false, defaultValue = "DESC") String desc,
			@RequestParam(name = "order", required = false, defaultValue = "date") String order,
			@RequestParam(name = "n", required = false, defaultValue = "") String nickName,
			@AuthenticationPrincipal PrincipalDetail principal, Model model,
			Board board,
			HttpServletRequest request
			) {

		boolean pub = true;
		String UserId = null;
		String channelName="";
		boolean loginCheck;
		String Uri = request.getRequestURI();
		String uri = "/index/channels/" + channelName;
		model.addAttribute("Uri", Uri);
		model.addAttribute("uri", uri);
		/* uri 안 쓰이는 듯하다 나중에 삭제 정리하자 */

		/* 로그인 전 */
		if (principal == null) {
			loginCheck = false;
			nickName = "";
		}

		/* 로그인 후 */
		else {
			loginCheck = true;
			nickName = principal.getNickName();
			UserId = principal.getUsername();
		}
		
		/* /index/board/detail/{no} */
		if (no != null) {
			model.addAttribute("board", boardService.getWritingDetail(no));
		}
		
		
		
		
			List<Category> getCategoryList = leftService.getCategoryList(principal.getNickName());
			model.addAttribute("getCategoryList",getCategoryList);
		
			/* header */
			String getChannelName = headerService.getChannelName(principal.getUsername());
			if(getChannelName !=null)
			{
				model.addAttribute("getChannelName",getChannelName);
			}
		

		
		List<Board> getWritingList = boardService.getWritingList(page, field, query, pub, size, order, desc,
				categoryName, nickName, loginCheck, Uri, channelName, no);
		 	System.out.println("/category_getWritingList:"
				+"\n page:"+page+
				"\n field:"+field+
				"\n query:"+query+
				"\n rowNum:"+size+
				"\n order:"+order+
				"\n categoryName:"+categoryName+
				"\n nickName:"+principal.getNickName());
		
		
		
		int getWritingCount = boardService.getWritingCount(field, query);
		
		 model.addAttribute("getWritingList", getWritingList);
		 model.addAttribute("getWritingCount", getWritingCount);
		 return "root.mid_allBoardList";
	}
	

	public String getCategoryPage(
			@PathVariable(required = false) Integer no,
			@PathVariable String categoryName,
			@RequestParam(name = "p", required = false, defaultValue = "1") int page,
			@RequestParam(name = "f", required = false, defaultValue = "title") String field,
			@RequestParam(name = "q", required = false, defaultValue = "") String query,
			@RequestParam(name = "r", required = false, defaultValue = "15") int size,
			@RequestParam(name = "desc", required = false, defaultValue = "DESC") String desc,
			@RequestParam(name = "order", required = false, defaultValue = "date") String order,
			@RequestParam(name = "n", required = false, defaultValue = "") String nickName,
			@AuthenticationPrincipal PrincipalDetail principal,
			Model model,
			HttpServletRequest request) {
		

		boolean pub = true;
		String UserId = null;
		boolean loginCheck;
		String Uri = request.getRequestURI();
		String channelName="";
		String uri = "/index/channels/" + channelName;
		model.addAttribute("Uri", Uri);
		model.addAttribute("uri", uri);
		/* uri 안 쓰이는 듯하다 나중에 삭제 정리하자 */

		/* 로그인 전 */
		if (principal == null) {
			loginCheck = false;
			nickName = "";
		}

		/* 로그인 후 */
		else {
			loginCheck = true;
			nickName = principal.getNickName();
			UserId = principal.getUsername();
		}
		
		/* /index/board/detail/{no} */
		if (no != null) {
			model.addAttribute("board", boardService.getWritingDetail(no));
		}
		
		
		List<Board> getWritingList = boardService.getWritingList(page, field, query, pub, size, order, desc,
				categoryName, nickName, loginCheck, Uri, channelName, no);

		int getWritingCount = boardService.getWritingCount(field, query);
		/*
		 * model.addAttribute("getWritingList", getWritingList);
		 * model.addAttribute("getWritingCount", getWritingCount);
		 */
		
		return "root.mid_allBoardList";
	}
}
