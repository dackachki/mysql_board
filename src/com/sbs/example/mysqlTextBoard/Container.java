package com.sbs.example.mysqlTextBoard;

import java.util.Scanner;

import com.sbs.example.mysqlTextBoard.Session.Session;
import com.sbs.example.mysqlTextBoard.dao.BoardDao;
import com.sbs.example.mysqlTextBoard.dao.MemberDao;
import com.sbs.example.mysqlTextBoard.service.BoardService;

public class Container {

	public static Scanner scanner;
	public static MemberDao memberDao;
	public static Session session;
	public static BoardDao boardDao;
	public static BoardService boardService;
	
	static {
		scanner = new Scanner(System.in);
		
		session = new Session();
		memberDao = new MemberDao();
		boardDao = new BoardDao();
		
		boardService = new BoardService();
		
		
	}

}
