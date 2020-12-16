package com.sbs.example.mysqlTextBoard.service;

import java.util.List;

import com.sbs.example.mysqlTextBoard.Container;
import com.sbs.example.mysqlTextBoard.controller.BoardController;
import com.sbs.example.mysqlTextBoard.dto.Article;
import com.sbs.example.mysqlTextBoard.dto.Board;
import com.sbs.java.ssg.util.util;

import Html.selectedBoardSummary;


public class ExportService {
	ArticleService articleService;
	MemberService memberService;
	BoardController boardController;
	selectedBoardSummary summary;

	public ExportService() {
		articleService = Container.articleService;
		memberService = Container.memberService;
		boardController = new BoardController();
		summary = new selectedBoardSummary();
	}

	public void makeHtml() {
		int previusId = 1;
		int nextId = 0;
		List<Article> articles = articleService.getArticlesBySelectedBoardId();
		if(Container.session.boardSelectedId ==0) {
			System.out.println("게시판 선택 후 다시 시도하세요");
			return;
		}
		int[] articlesId = new int[articles.size()];
		
		for(int i = 0; i <= articles.size()-1;i++) {
			
			articlesId[i] = articles.get(i).id;
		}
		for (Article article : articles) {
			
			memberService.getMembers();
			String boardName = boardController.getBoardNameById(article.boardId);
			String writerName = memberService.getMemberNameById(article.memberId);
			//filename
			String fileName = boardController.getBoardNameById(article.boardId)+"_"+article.id + ".html";
			//content
			
			
			String html="";
			String articleDetail="";
			String head =util.getFileContents("html_template/header.html"); 
			articleDetail += "<div class=\"con article_body\">";
			articleDetail += "<div>제목 : " + article.title + "</div>";
			articleDetail += "<div>날짜 : " + article.regDate + "</div>";
			articleDetail += "<div>작성자 : " + writerName + "</div>";
			articleDetail += "<div>번호 : " + article.id + "</div>";
			articleDetail += "<div>내용 : " + article.body + "</div>";
			articleDetail += "</div>";
			articleDetail += "<br><br>";
			articleDetail +="\n";
			head = head.replace("[Article_List]", articleDetail);
			
			String presentDirectory ="";
			presentDirectory+="<section class =\"title-bar con-min-width\">";
			presentDirectory+="<h1 class=\"con\">";
			presentDirectory+=summary.getIconByBoardName(boardName);
			presentDirectory+=boardController.getBoardNameById(article.boardId)+"게시판  "+article.id+"번 게시물";
			presentDirectory+="</h1>";
			presentDirectory+="<div class=\"con\">";
			head = head.replace("[Directory_Show]", presentDirectory);
			
			List<Board> boards = boardController.getBoards();
			String ArticleBoardsList = "";
			
			for (Board board : boards) {
				ArticleBoardsList += "<li><a href=\"/./work/work/mysql-text-board/exportHtml/" + board.boardName + "/"
						+ board.boardName + "list-" + 1 + ".html\" class = \"block\">"+summary.getIconByBoardName(board.boardName) +"<span>" + board.boardName
						+ "</span></a></li>";
			}
			head = head.replace("[Board_List_Part]", ArticleBoardsList);
			html +=head;
			String articlePN = "";
			articlePN +="<div class=\"below_button flex\">";
			if(previusId >= articles.size()) {
				previusId = articles.size();
			}
			if(nextId >= articles.size()) {
				nextId = articles.size();
			}
			if (article.id > articlesId[0]) {
				articlePN += "<div><a href=\"" +boardController.getBoardNameById(article.boardId)+"_"+(articles.get(previusId-1).id)+ ".html\">&lt; 이 전 글</a></div>";
				previusId ++;	
			}
			articlePN +="\n";
			if(article.id < articlesId[articles.size()-1]) {
				articlePN += "<div><a href=\"" +boardController.getBoardNameById(article.boardId)+"_"+(articles.get(nextId+1).id)+ ".html\">다 음 글 &gt;</a></div>";
				nextId++;
			}
			articlePN += "<div><a href=\""+boardName+"list-1.html"+"\">목 록</a></div>";
			articlePN +="\n";
			articlePN +="</div>";
			articlePN +="</div>";
			html += articlePN;
			html += "</section>";
			html += "</main>";
			html+=util.getFileContents("html_template/footer.html");
			
			
			String boardpath = ("exportHtml/"+boardName+"\\");
			
			util.writeFileContents(boardpath + fileName, html);
		}
	}

}
