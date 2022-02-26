package com.AllorChannels.Controller;

import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.yaml.snakeyaml.util.UriEncoder;

import com.AllorChannels.Config.auth.PrincipalDetail;
import com.AllorChannels.Dto.Board;
import com.AllorChannels.Dto.Category;
import com.AllorChannels.Dto.Channel;
import com.AllorChannels.Dto.ChannelCategory;
import com.AllorChannels.Service.BoardService;
import com.AllorChannels.Service.ChannelService;
import com.AllorChannels.Service.HeaderService;
import com.AllorChannels.Service.LeftService;

@Controller
public class BoardController {

	@Autowired
	BoardService boardService;

	@Autowired
	HeaderService headerService;

	@Autowired
	LeftService leftService;

	@Autowired
	ChannelService channelService;

	/* Board 부분 메소드가 다름 */
	@RequestMapping("/index/channels")
	public String Channels(
			@PathVariable(required = false) Integer no,
			@PathVariable(required = false) String channelName,
			@RequestParam(name = "c", required = false, defaultValue = "") String categoryName,
			@RequestParam(name = "p", required = false, defaultValue = "1") int page,
			@RequestParam(name = "f", required = false, defaultValue = "title") String field,
			@RequestParam(name = "q", required = false, defaultValue = "") String query,
			@RequestParam(name = "r", required = false, defaultValue = "15") int size,
			@RequestParam(name = "desc", required = false, defaultValue = "DESC") String desc,
			@RequestParam(name = "order", required = false, defaultValue = "date") String order, Model model,
			Board board, Channel channel, @AuthenticationPrincipal PrincipalDetail principal,
			HttpServletRequest request) {

		boolean pub = true;
		String nickName;
		String UserId = null;
		boolean loginCheck;
		String Uri = request.getRequestURI();
		String encodeUri = UriEncoder.decode(Uri);
		model.addAttribute("Uri", encodeUri);
	
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

		if (channelName == null) {
			channelName = "";
		}

		/* /index/channel/board/detail/{no} */
		if (no != null) {
			model.addAttribute("board", boardService.getWritingDetail(no));
		}

		model.addAttribute("channelName", channelName);

		/* header */
		String getChannelName = headerService.getChannelName(nickName);
		if (getChannelName != null) {
			
			model.addAttribute("getChannelName", getChannelName);
			/*채널 삭제 후에도 채널 세션 유지되는 문제 있음.
			 * HttpSession session = request.getSession();
			 * session.setAttribute("getChannelName", getChannelName);
			 */
		}else {model.addAttribute("getChannelName", null);}

		/* left */
		List<Category> getCategoryList = leftService.getCategoryList(nickName);
		model.addAttribute("getCategoryList", getCategoryList);
		
		/* Channel */
		List<Channel> getChannelList = channelService.getChannelList(page, size, query);
		int getChannelCount = channelService.getChannelCount(field,
				query); /* field값 쓰면 db컬럼에 content값이 없어 에러 뜨므로 매퍼에 'title'로 값 고정해둠 나중에 삭제요망 */
		
		model.addAttribute("getChannelList", getChannelList);
		model.addAttribute("getChannelCount", getChannelCount);
		
		
		/* Board */
		List<Board> getChannelWritingList = null;
		if (getChannelWritingList != null) /* null처리 안해주면 채널이 없을 경우 에러 뜸 */
		{
		getChannelWritingList = boardService.getChannelWritingList(5, getChannelList);
		model.addAttribute("getChannelWritingList", getChannelWritingList);}

		/* 오늘 날짜 생성하여 Str타입으로 jsp에 전달 */
		Date today_date = new java.util.Date();
		DateFormat dateFormat_year = new SimpleDateFormat("yy/MM/dd");
		String today_str_year = dateFormat_year.format(today_date);
		model.addAttribute("today_str_year", today_str_year);

		return "root.mid_allChannerList";
	}

	@RequestMapping({ "/index", "/index/category",
		"/index/channels/{channelName}",
		"/index/category/detail/{no}",
		"/index/board/detail/{no}",
			"/index/channels/detail/{channelName}/{no}"
			})
	public String channelName(@PathVariable(required = false) Integer no,
			@PathVariable(required = false) String channelName,
			@RequestParam(name = "n", required = false, defaultValue = "") Integer number,
			@RequestParam(name = "c", required = false, defaultValue = "") String categoryName,
			@RequestParam(name = "p", required = false, defaultValue = "1") int page,
			@RequestParam(name = "f", required = false, defaultValue = "title") String field,
			@RequestParam(name = "q", required = false, defaultValue = "") String query,
			@RequestParam(name = "r", required = false, defaultValue = "15") int size,
			@RequestParam(name = "desc", required = false, defaultValue = "DESC") String desc,
			@RequestParam(name = "order", required = false, defaultValue = "date") String order, Model model,
			Board board, Channel channel, @AuthenticationPrincipal PrincipalDetail principal,
			HttpServletRequest request) throws UnsupportedEncodingException {
		System.out.println("board controller");
		boolean pub = true;
		String nickName;
		String UserId = null;
		boolean loginCheck;
		String Uri = request.getRequestURI();
		String encodeUri = UriEncoder.decode(Uri);
		model.addAttribute("Uri", encodeUri);
		/* <c:if>문에서 아래 내용이 먹히질 않아서 여기서 변수로 만들어 EL문으로 전송보냄 */
		String indexChannelsChannel = "/index/channels/" + channelName;
		model.addAttribute("indexChannelsChannel", indexChannelsChannel);
		String indexChannelsChannelNo = "/index/channels/" + channelName + "/" + no;
		model.addAttribute("indexChannelsChannelNo", indexChannelsChannelNo);
		
		model.addAttribute("categoryName", categoryName);

		if (channelName == null) {
			channelName = "";
		}

		model.addAttribute("channelName", channelName);

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
			
			model.addAttribute("nickName", nickName);
		}

		/* /index/board/detail/{no} */
		if (no != null) {
			model.addAttribute("board", boardService.getWritingDetail(no));
		}

		/* header */
		String getChannelName = headerService.getChannelName(nickName);
if (getChannelName != null) {
			
			model.addAttribute("getChannelName", getChannelName);
			/*채널 삭제 후에도 채널 세션 유지되는 문제 있음.
			 * HttpSession session = request.getSession();
			 * session.setAttribute("getChannelName", getChannelName);
			 */
		}


		/* left */
		List<Category> getCategoryList = leftService.getCategoryList(nickName);
		model.addAttribute("getCategoryList", getCategoryList);
		List<ChannelCategory> getChannelCategoryList = leftService.getChannelCategoryList(channelName);
		model.addAttribute("getChannelCategoryList", getChannelCategoryList);
		
		/* Board */
		List<Board> getWritingList = boardService.getWritingList(page, field, query, pub, size, order, desc,
				categoryName, nickName, loginCheck, encodeUri, channelName, no);
		int getWritingCount = boardService.getWritingCount(field, query);

		model.addAttribute("getWritingList", getWritingList);
		model.addAttribute("getWritingCount", getWritingCount);

		
		
		
		/* 오늘 날짜 생성하여 Str타입으로 jsp에 전달 */
		Date today_date = new java.util.Date();
		DateFormat dateFormat_year = new SimpleDateFormat("yy/MM/dd");
		String today_str_year = dateFormat_year.format(today_date);
		model.addAttribute("today_str_year", today_str_year);

		return "root.mid_allBoardList";
	}



	@RequestMapping({ "/channels/saveTheWritingForm/{channelName}", "/board/saveTheWritingForm" })
	public String saveTheWriting(@PathVariable(required = false) String channelName, Model model,
			HttpServletRequest request,
			@AuthenticationPrincipal PrincipalDetail principal
			) {
		
		String Uri = request.getRequestURI();
		String encodeUri = UriEncoder.decode(Uri);
		model.addAttribute("Uri", encodeUri);
		
		/* board */
		model.addAttribute("channelName", channelName);

		/* left */
		List<Category> getCategoryList = leftService.getCategoryList(principal.getNickName());
		model.addAttribute("getCategoryList", getCategoryList);
		List<ChannelCategory> getChannelCategoryList = leftService.getChannelCategoryList(channelName);
		model.addAttribute("getChannelCategoryList", getChannelCategoryList);
		
		return "root.mid_saveTheWritingForm";
	}
	
	@RequestMapping({
		"/index/category/detail/{no}/update",
		"/index/board/detail/{no}/update",
			"/index/channels/detail/{channelName}/{no}/update"
			})
	public String updateWriting(@PathVariable(required = false) Integer no,
			@PathVariable(required = false) String channelName,
			@RequestParam(name = "n", required = false, defaultValue = "") Integer number,
			@RequestParam(name = "c", required = false, defaultValue = "") String categoryName,
			@RequestParam(name = "p", required = false, defaultValue = "1") int page,
			@RequestParam(name = "f", required = false, defaultValue = "title") String field,
			@RequestParam(name = "q", required = false, defaultValue = "") String query,
			@RequestParam(name = "r", required = false, defaultValue = "15") int size,
			@RequestParam(name = "desc", required = false, defaultValue = "DESC") String desc,
			@RequestParam(name = "order", required = false, defaultValue = "date") String order, Model model,
			Board board, Channel channel, @AuthenticationPrincipal PrincipalDetail principal,
			HttpServletRequest request) throws UnsupportedEncodingException {
		System.out.println("board controller");
		boolean pub = true;
		String nickName;
		String UserId = null;
		boolean loginCheck;
		String Uri = request.getRequestURI();
		String encodeUri = UriEncoder.decode(Uri);
		model.addAttribute("Uri", encodeUri);
	
		
		model.addAttribute("categoryName", categoryName);

		if (channelName == null) {
			channelName = "";
		}

		model.addAttribute("channelName", channelName);

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
			
			model.addAttribute("nickName", nickName);
		}

		/* /index/board/detail/{no} */
		if (no != null) {
			model.addAttribute("board", boardService.getWritingDetail(no));
		}

		/* header */
		String getChannelName = headerService.getChannelName(nickName);
if (getChannelName != null) {
			
			model.addAttribute("getChannelName", getChannelName);
			/*채널 삭제 후에도 채널 세션 유지되는 문제 있음.
			 * HttpSession session = request.getSession();
			 * session.setAttribute("getChannelName", getChannelName);
			 */
		}


		/* left */
		List<Category> getCategoryList = leftService.getCategoryList(nickName);
		model.addAttribute("getCategoryList", getCategoryList);
		List<ChannelCategory> getChannelCategoryList = leftService.getChannelCategoryList(channelName);
		model.addAttribute("getChannelCategoryList", getChannelCategoryList);
		
	
		

		return "root.mid_updateTheWritingForm";
	}

	
	/*
	 * @GetMapping("/index/channel/board/detail/{no}") public String
	 * channelfindByNo(@PathVariable int no, Model model, @AuthenticationPrincipal
	 * PrincipalDetail principal) {
	 * 
	 * boolean pub = true; String nickName; String UserId = null;
	 * 
	 * 로그인 전 if (principal == null) nickName = ""; 로그인 후 else { nickName =
	 * principal.getNickName(); UserId = principal.getUsername(); }
	 * 
	 * "/index/channel/board/detail/{no}" board model.addAttribute("board",
	 * boardService.getWritingDetail(no));
	 * 
	 * left List<Category> getCategoryList = leftService.getCategoryList(nickName);
	 * model.addAttribute("getCategoryList", getCategoryList);
	 * 
	 * 오늘 날짜 생성하여 Str타입으로 jsp에 전달 Date today_date = new java.util.Date(); DateFormat
	 * dateFormat_year = new SimpleDateFormat("yy/MM/dd"); String
	 * today_str_year=dateFormat_year.format(today_date);
	 * model.addAttribute("today_str_year", today_str_year);
	 * 
	 * return "root.mid_detailChannel"; }
	 */

	/*
	 * @GetMapping("/index/board/detail/{no}") public String findByNo(@PathVariable
	 * int no, Model model, @AuthenticationPrincipal PrincipalDetail principal) {
	 * 
	 * boolean pub = true; String nickName; String UserId = null;
	 * 
	 * 로그인 전 if (principal == null) nickName = ""; 로그인 후 else { nickName =
	 * principal.getNickName(); UserId = principal.getUsername(); }
	 * 
	 * board model.addAttribute("board", boardService.getWritingDetail(no));
	 * 
	 * left List<Category> getCategoryList = leftService.getCategoryList(nickName);
	 * model.addAttribute("getCategoryList", getCategoryList);
	 * 
	 * 오늘 날짜 생성하여 Str타입으로 jsp에 전달 Date today_date = new java.util.Date(); DateFormat
	 * dateFormat_year = new SimpleDateFormat("yy/MM/dd"); String today_str_year =
	 * dateFormat_year.format(today_date); model.addAttribute("today_str_year",
	 * today_str_year);
	 * 
	 * return "root.mid_detail";
	 * 
	 * }
	 */

}
