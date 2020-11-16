package com.sbs.example.mysqlTextBoard.service;

import java.util.List;

import com.sbs.example.mysqlTextBoard.Container;
import com.sbs.example.mysqlTextBoard.dao.BoardDao;
import com.sbs.example.mysqlTextBoard.dto.Board;

public class BoardService {
	BoardDao boardDao;
	
	public BoardService() {
		boardDao = Container.boardDao;
	}

	public void makeBoard(String boardName) {
		boardDao.makeBoard(boardName);
	}
	public List<Board> getBoards(){
		return boardDao.getBoards();
	}
}
