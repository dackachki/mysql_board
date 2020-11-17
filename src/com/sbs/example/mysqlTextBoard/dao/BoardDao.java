package com.sbs.example.mysqlTextBoard.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.sbs.example.mysqlTextBoard.dto.Board;
import com.sbs.example.mysqlutil.MysqlUtil;
import com.sbs.example.mysqlutil.SecSql;

public class BoardDao {

	List<Board> boards = new ArrayList<>();

	public List<Board> getBoards() {
		return getBoardList();
	}

	public void makeBoard(String boardName) {

		MysqlUtil.update(new SecSql().append("insert into board set boardName = ?;",boardName));

			

			
		System.out.printf("%s 게시판이 생성되었습니다.", boardName);

	}

	public List<Board> getBoardList() {

		boards = new ArrayList<>();
		List<Map<String, Object>> BoardMapList = MysqlUtil.selectRows(new SecSql().append("SELECT * FROM board"));

		for (Map<String, Object> BoardMap : BoardMapList) {
			Board board = new Board();
			board.id = (int) BoardMap.get("id");
			board.boardName = (String) BoardMap.get("boardName");

			boards.add(board);
		}
		return boards;
	}

}
