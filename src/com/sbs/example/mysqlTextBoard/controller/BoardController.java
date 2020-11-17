package com.sbs.example.mysqlTextBoard.controller;

import java.util.List;
import java.util.Scanner;

import com.sbs.example.mysqlTextBoard.Container;
import com.sbs.example.mysqlTextBoard.dto.Board;
import com.sbs.example.mysqlTextBoard.service.BoardService;

public class BoardController {
	private BoardService boardService;
	Scanner sc = Container.scanner;

	public BoardController() {
		boardService = Container.boardService;

	}

	public void doCommand(String cmd) {
		if (cmd.equals("board make")) {
			System.out.println("== 게시판 생성 ==");
			System.out.println("게시판 이름 입력 : ");
			String boardName = sc.nextLine();
			boardService.makeBoard(boardName);
		} else if (cmd.equals("board list")) {
			System.out.println("== 생성된 게시판 목록 ==");
			System.out.println("번호  / 이름");
			for (Board board : getBoards()) {
				System.out.printf("%d  / %s\n", board.id, board.boardName);
			}

		} else if (cmd.equals("board select")) {
			System.out.println("선택할 보드 이름 입력 : ");
			String boardLF = sc.nextLine();
			boolean boardPass = isBoardExist(boardLF);
			if (boardPass == false) {
				System.out.println("존재하지 않는 게시판입니다.");
			}
			if (boardPass) {
				System.out.printf("%s 게시판이 선택되었습니다.\n", boardLF);
				System.out.println(Container.session.boardSelectedId);

			}

		} else if (cmd.equals("board preinfo")) {
			
			int presentBoardId = Container.session.boardSelectedId;
			if(presentBoardId == 0) {
				System.out.println("선택된 게시판이 없습니다.");
				return;
			}
			System.out.printf("선택된 게시판 번호 :%d\n", presentBoardId);
			for(Board board : getBoards()) {
				if(presentBoardId == board.id) {
					System.out.printf("선택된 게시판 이름 : %s\n ",board.boardName);
				}
			}
		}
	}

	private boolean isBoardExist(String boardLF) {
		for (Board board : getBoards()) {
			if (board.boardName.equals(boardLF)) {
				Container.session.boardSelectedId = board.id;
				return true;
			}
		}
		return false;
	}

	public List<Board> getBoards() {
		return boardService.getBoards();
	}
}