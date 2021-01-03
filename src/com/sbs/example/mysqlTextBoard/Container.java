package com.sbs.example.mysqlTextBoard;

import java.util.Scanner;

import com.sbs.example.mysqlTextBoard.Config.Config;
import com.sbs.example.mysqlTextBoard.Session.Session;
import com.sbs.example.mysqlTextBoard.controller.ArticleController;
import com.sbs.example.mysqlTextBoard.controller.BoardController;
import com.sbs.example.mysqlTextBoard.controller.Controller;
import com.sbs.example.mysqlTextBoard.controller.ExportController;
import com.sbs.example.mysqlTextBoard.controller.MemberController;
import com.sbs.example.mysqlTextBoard.dao.BoardDao;
import com.sbs.example.mysqlTextBoard.dao.MemberDao;
import com.sbs.example.mysqlTextBoard.service.ArticleService;
import com.sbs.example.mysqlTextBoard.service.BoardService;
import com.sbs.example.mysqlTextBoard.service.DisqusApiService;
import com.sbs.example.mysqlTextBoard.service.ExportService;
import com.sbs.example.mysqlTextBoard.service.MemberService;

public class Container {

	public static Scanner scanner;
	public static MemberDao memberDao;
	public static Session session;
	public static BoardDao boardDao;
	public static BoardService boardService;
	public static Controller articleController;
	public static Controller memberController;
	public static Controller boardController;
	public static ArticleService articleService;
	public static MemberService memberService;
	public static ExportService exportService;
	public static Controller exportController;
	public static DisqusApiService disqusApiServce;
	public static Config config;
	
	static {
		scanner = new Scanner(System.in);
		session = new Session();
		config = new Config();
		
		memberDao = new MemberDao();
		boardDao = new BoardDao();
		
		disqusApiServce= new DisqusApiService();
		boardService = new BoardService();
		articleService = new ArticleService();
		memberService = new MemberService();
		exportService = new ExportService();
		
		articleController = new ArticleController();
		memberController = new MemberController();
		boardController = new BoardController();
		exportController = new ExportController();
		
		
	}

}
