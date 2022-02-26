package com.AllorChannels.Controller.api;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.AllorChannels.Config.auth.PrincipalDetail;
import com.AllorChannels.Dto.Board;
import com.AllorChannels.Dto.ResponseDto;
import com.AllorChannels.Service.BoardService;

@RestController
public class BoardApiController {

	@Autowired
	BoardService boardService;

	@PostMapping("/board/saveTheWritingForm")
	public ResponseDto<Integer> saveTheWriting(@RequestBody Board board,
			@AuthenticationPrincipal PrincipalDetail principal) {

		boardService.saveTheWriting(board, principal.getNickName());
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
	}

	@DeleteMapping("/index/board/detail/{no}")
	public ResponseDto<Integer> deleteWriting(@PathVariable int no) {
		System.out.println("numbers"+no);
		boardService.deleteWritingList(no);
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
	}
	
	@PutMapping("/index/board/detail/update")
	public ResponseDto<Integer> upadeWriting(
			@RequestBody Board board) {

		boardService.updateWritingList(board);
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
	}
}
