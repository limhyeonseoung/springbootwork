package com.study.springboot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.study.springboot.domain.Board;
import com.study.springboot.domain.Reply;
import com.study.springboot.service.BoardService;

@Controller
@SessionAttributes("loginUser")
public class BoardController {
	
	@Autowired
	BoardService boardService;
	
	@GetMapping("/list")
	public String list(@RequestParam(value="nowPage", defaultValue="0") int nowPage, Model model) {
		Page<Board> pageList = boardService.list(PageRequest.of(nowPage, 2, Sort.by(Sort.Direction.DESC, "bno")));
		
		int pagePerBlock = 2;	// [1][2][3][4][5]
		int endPage = Math.min(pageList.getTotalPages(), nowPage + pagePerBlock);

		model.addAttribute("boardPage", pageList);
		model.addAttribute("nowPage", nowPage);
		model.addAttribute("endPage", endPage);
		return "board/list";
	}
	
	@GetMapping("/insertForm")
	public String insertForm() {
		return "board/insertForm";
	}
	
	@PostMapping("/insert")
	public String insert(Board board) {
		boardService.insert(board);
		return "redirect:list";
	}
	
	 @GetMapping("/detailForm")
	    public String detailForm(@RequestParam(value="bno") Long bno, Model model) {
	        Board board = boardService.getBoard(bno).orElseThrow(() -> new IllegalArgumentException("Invalid board Id:" + bno));
	        model.addAttribute("board", board);
	        return "board/detailForm";
	    }
	 
	 @GetMapping("/replyForm")
		public String replyForm() {
			return "board/replyForm";
		}
	 @PostMapping("/reply")
	 public String reply(Board board) {
		 boardService.reply(board);
		 return "board/list";
	 }
	 
	// 검색 결과 리스트 컨트롤러
	 @GetMapping("/search")
	 public String search(@RequestParam("keyword") String keyword, 
	                      @RequestParam("type") String type, 
	                      Model model) {
	     // 검색 로직 실행 (제목, 작성자에 대한 검색 처리)
	     List<Board> searchResults = boardService.searchByKeyword(keyword, type);
	     
	     // 검색 결과를 모델에 추가
	     model.addAttribute("searchResults", searchResults);
	     return "searchResult"; // 검색 결과 페이지로 이동
	 }

	 // 게시물 상세보기 컨트롤러
	 @GetMapping("/detailForm")
	 public String detailForm(@RequestParam("bno") Long bno, Model model) {
	     // 게시글의 상세 정보 가져오기
	     Board board = boardService.findById(bno);
	     
	     // 게시글 정보를 모델에 추가
	     model.addAttribute("board", board);
	     return "detailForm"; // 상세보기 페이지로 이동
	 }

	/*
	@PostMapping("/insert")
	public String insert(Board board, HttpSession session) {
		Member m = (Member)session.getAttribute("loginUser");
		board.setWriter(m.getName());
		boardService.insert(board);
		return "redirect:list";
	}
	*/

}