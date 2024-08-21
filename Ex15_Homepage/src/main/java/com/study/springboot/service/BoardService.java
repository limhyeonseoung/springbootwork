package com.study.springboot.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.study.springboot.domain.Board;
import com.study.springboot.domain.Reply;
import com.study.springboot.repository.BoardRepository;

@Service
public class BoardService {
	
	@Autowired
	BoardRepository boardRepository;

	public Board insert(Board board) {
		return boardRepository.save(board);		
	}

	public Page<Board> list(PageRequest of) {
		return boardRepository.findAll(of);
	}

	public Optional<Board> getBoard(Long bno) {
        return boardRepository.findById(bno);
    }

	public Board reply(Board board) {
		return boardRepository.save(board);
		
	}

	public List<Board> searchByKeyword(String keyword, String type) {
		 Board findById(Long bno); // 상세보기
		return null;
	}


}