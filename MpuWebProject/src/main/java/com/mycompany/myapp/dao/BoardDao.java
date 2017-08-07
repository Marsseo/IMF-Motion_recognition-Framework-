package com.mycompany.myapp.dao;

import java.util.List;

import com.mycompany.myapp.dto.Board;
import com.mycompany.myapp.dto.BoardComment;

public interface BoardDao {
	
	// Board
	public int boardInsert(Board imageboard);
	public List<Board> boardSelectPage(int pageNo, int rowsPerPage);
	public int boardCountAll();
	public Board boardSelectByBno(int bno);
	public void boardUpdateBhitcount(int bno, int bhitcount);
	public void boardDelete(int bno);
	public void boardUpdate(Board Board);
	public void boardUpdateBlikecount(int bno, int blikecount);
//	public List<Exam12ImageBoard> imageBoardSelectPage(int pageNo, int rowsPerPage, String btitle);
	
	// BoardComment
	public int boardCommentInsert(BoardComment comment);
	public List<BoardComment>boardCommentList(int bcno);
	
}
