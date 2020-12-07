package com.sbs.example.mysqlTextBoard.controller;

import java.util.Scanner;

import com.sbs.example.mysqlTextBoard.Container;
import com.sbs.example.mysqlTextBoard.service.ExportService;
import Html.free_board;



public class ExportController extends Controller {
	private Scanner sc;
	private String command;
	
	private ExportService exportService;
	private free_board free;

	public ExportController() {
		free = new free_board();
		exportService = Container.exportService;
	}

	public void doCommand(String command) {
		this.command = command;
		

		switch (command) {
		case "html":
			doHtml();
			break;
		case"htmlbyboard":
			doHtmlbyboard();
			break;
		default:
			System.out.println("존재하지 않는 명령어 입니다.");
			break;
		}
	}

	private void doHtml() {
		System.out.println("== html 생성을 시작합니다. ==");
		exportService.makeHtml();
	}
	private void doHtmlbyboard() {
		free.makeHtmlOfSelectedBoard();
	}

}