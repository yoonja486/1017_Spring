package com.kh.spring.board.model.service;

import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.swing.Spring;

import org.springframework.web.multipart.MultipartFile;

import com.kh.spring.board.model.dto.BoardDTO;

public interface BoardService {

	// 게시글 목록 조회 + 페이징처리
	// 총 게시글 수 조회
	// int selectListCount();
	
	// 목록조회
	/*
	 * selectBoardList();
	 * findAll();
	 * selectAll();
	 */
	Map<String, Object> findAll(Long boardNo);
	
	// 게시글 작성
	/*
	 * insertBoard();
	 * save();
	 */
	int save(BoardDTO board, MultipartFile upfile, HttpSession session);
	
	// 게시글 상세보기(조회수 증가)
	/*
	 * selectBoard();
	 * findByXXX(); - pk값, 아이디값
	 */
	BoardDTO findByBoardNo(Long boardNo);
	
	// 게시글 삭제하기
	/*
	 * daleteBoard();
	 * deleteByXXX(); - pk값, 아이디값
	 */
	int deleteByBoardNo(Long boardNo);
	
	// 게시글 수정하기
	/*
	 * updateBoard();
	 * updateByXXX();
	 */
	int update(BoardDTO board);
	
	// ---------------------------------------------
	// 댓글 서비스
	
	
	
	
	
	
}
