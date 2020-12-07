package com.sbs.example.mysqlTextBoard.service;

import java.util.List;

import com.sbs.example.mysqlTextBoard.Container;
import com.sbs.example.mysqlTextBoard.controller.BoardController;
import com.sbs.example.mysqlTextBoard.dto.Article;
import com.sbs.java.ssg.util.util;

public class ExportService {
	ArticleService articleService;
	MemberService memberService;
	BoardController boardController;

	public ExportService() {
		articleService = Container.articleService;
		memberService = Container.memberService;
		boardController = new BoardController();
	}

	public void makeHtml() {
		List<Article> articles = articleService.getAllArticles();

		for (Article article : articles) {
			memberService.getMembers();
			String writerName = memberService.getMemberNameById(article.memberId);
			//filename
			String fileName = article.id + ".html";
			//content
			String html = "<meta charset=\"UTF-8\">";
			html += "<div>번호 : " + article.id + "</div>";
			html += "<div>날짜 : " + article.regDate + "</div>";
			html += "<div>작성자 : " + writerName + "</div>";
			html += "<div>제목 : " + article.title + "</div>";
			html += "<div>내용 : " + article.body + "</div>";
			if (article.id > 1) {
				html += "<div><a href=\"" + (article.id - 1) + ".html\">이전글</a></div>";
			}
			
			html += "<div><a href=\"" + (article.id + 1) + ".html\">다음글</a></div>";
			String boardName = boardController.getBoardNameById(article.boardId);
			String boardpath = ("exportHtml/"+boardName+"\\");
			
			util.writeFileContents(boardpath + fileName, html);
		}
	}

}
