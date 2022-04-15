package com.kosta.lhj.dao;

import java.util.List;


import com.kosta.lhj.vo.BoardVo;

public interface BoardDao {
	public List<BoardVo> getList();  // 게시물 전체 목록 조회
	public BoardVo getBoard(int no); // 게시물 상세 조회
	public int insert(BoardVo vo);   // 게시물 등록
	public int delete(int no);       // 게시물 삭제
	public int update(BoardVo vo);   // 게시물 수정
	public void upHit(int no); // 조회수
	public List<BoardVo> getList(String keyField, String keyword, String startDate, String endDate); //게시물 검색
}
