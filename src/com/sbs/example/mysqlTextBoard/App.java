package com.sbs.example.mysqlTextBoard;

import java.util.Scanner;

import com.sbs.example.mysqlTextBoard.controller.ArticleController;
import com.sbs.example.mysqlTextBoard.controller.BoardController;
import com.sbs.example.mysqlTextBoard.controller.MemberController;

public class App {
	public void run() {
		Scanner sc = Container.scanner;

		ArticleController articleController = new ArticleController();
		MemberController memberController = new MemberController();
		BoardController boardController = new BoardController();
		while (true) {
			System.out.printf("명령어) ");
			String cmd = sc.nextLine();

			 if (cmd.equals("system exit")) {
				System.out.println("== 시스템 종료 ==");
				break;
			} else if (cmd.startsWith("article")) {
				articleController.doCommand(cmd);
			}
			else if(cmd.startsWith("member")) {
				memberController.doCommand(cmd);
			}
			else if(cmd.startsWith("board")) {
				boardController.doCommand(cmd);
			}
				
		}

	}

}
