package com.sbs.example.mysqlTextBoard.dto;

public class Board {
	public int id;
	public String boardName;
	public Board(int id, String boardName) {
		this.id = id;
		this.boardName = boardName;
	}
	public Board() {
		
	}
	@Override
	public String toString() {
		return "Board [id=" + id + ", boardName=" + boardName + "]";
	}
}
