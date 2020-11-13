package com.sbs.example.mysqlTextBoard;

import java.util.Scanner;

import com.sbs.example.mysqlTextBoard.Session.Session;
import com.sbs.example.mysqlTextBoard.dao.MemberDao;

public class Container {

	public static Scanner scanner;
	public static MemberDao memberDao;
	public static Session session;
	
	static {
		scanner = new Scanner(System.in);
		
		session = new Session();
		memberDao = new MemberDao();
	}

}
