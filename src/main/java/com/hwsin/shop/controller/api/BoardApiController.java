package com.hwsin.shop.controller.api;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hwsin.shop.dto.ReplySaveRequestDto;
import com.hwsin.shop.dto.ResponseDto;
import com.hwsin.shop.model.Board;
import com.hwsin.shop.model.RoleType;
import com.hwsin.shop.model.Users;
import com.hwsin.shop.service.BoardService;
import com.hwsin.shop.service.UserService;
import com.hwsin.shop.config.auth.PrincipalDetail;

@RestController
public class BoardApiController {

	@Autowired
	private BoardService boardService;
	
	@GetMapping("/auth/board/pagination")
	public Page<Board> getPaging(Model model,
			@PageableDefault(size = 3, sort = "id", direction = Sort.Direction.DESC) Pageable pageable){
		
		return boardService.글목록(pageable);
	}
	
	@PostMapping("/api/board")
	public ResponseDto<Integer> save(@RequestBody Board board, @AuthenticationPrincipal PrincipalDetail principal) {
		System.out.println(principal.getUser());
		boardService.글쓰기(board, principal.getUser());
		
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
	}

	@DeleteMapping("/api/board/{id}")
	public ResponseDto<Integer> deleteById(@PathVariable int id) {
		boardService.글삭제하기(id);
		
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
	}

	@PutMapping("/api/board/{id}")
	public ResponseDto<Integer> update(@PathVariable int id, @RequestBody Board board) {
		System.out.println("BoardApiController : update : id : " + id);
		System.out.println("BoardApiController : update : board : " + board.getTitle());
		System.out.println("BoardApiController : update : board : " + board.getContent());
		boardService.글수정하기(id, board);
		
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
	}

	// 데이터 받을 때 컨트롤러에서 dto를 만들어서 받는게 좋다.
	// dto 사용하지 않은 이유는!! -> 프로젝트가 작기때문에
	@PostMapping("/api/board/{boardId}/reply")
	public ResponseDto<Integer> replySave(@RequestBody ReplySaveRequestDto replySaveRequestDto) {
		boardService.댓글쓰기(replySaveRequestDto);
		
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
	}

	@DeleteMapping("/api/board/{boardId}/reply/{replyId}")
	public ResponseDto<Integer> replyDelete(@PathVariable int replyId) {
		boardService.댓글삭제(replyId);
		
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
	}
}
