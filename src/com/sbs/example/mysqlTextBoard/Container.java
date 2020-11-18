package com.sbs.example.mysqlTextBoard;

import java.util.Scanner;

import com.sbs.example.mysqlTextBoard.Session.Session;
import com.sbs.example.mysqlTextBoard.controller.ArticleController;
import com.sbs.example.mysqlTextBoard.controller.BoardController;
import com.sbs.example.mysqlTextBoard.controller.Controller;
import com.sbs.example.mysqlTextBoard.controller.MemberController;
import com.sbs.example.mysqlTextBoard.dao.BoardDao;
import com.sbs.example.mysqlTextBoard.dao.MemberDao;
import com.sbs.example.mysqlTextBoard.service.BoardService;

public class Container {

	public static Scanner scanner;
	public static MemberDao memberDao;
	public static Session session;
	public static BoardDao boardDao;
	public static BoardService boardService;
	public static Controller articleController;
	public static Controller memberController;
	public static Controller boardController;
	
	static {
		scanner = new Scanner(System.in);
		session = new Session();
		
		memberDao = new MemberDao();
		boardDao = new BoardDao();
		
		boardService = new BoardService();
		
		articleController = new ArticleController();
		memberController = new MemberController();
		boardController = new BoardController();
		
		
	}

}
