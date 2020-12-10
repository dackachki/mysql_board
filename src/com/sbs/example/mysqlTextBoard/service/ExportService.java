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
			String writerName = memberService.getMemberNameById(article.memberId);
			//filename
			String fileName = boardController.getBoardNameById(article.boardId)+"_"+article.id + ".html";
			//content
			
			String html = "<meta charset=\"UTF-8\">";
			String head =util.getFileContents("html_template/header.html"); 
			html +=head;
			html += "<div>번호 : " + article.id + "</div>";
			html += "<div>날짜 : " + article.regDate + "</div>";
			html += "<div>작성자 : " + writerName + "</div>";
			html += "<div>제목 : " + article.title + "</div>";
			html += "<div>내용 : " + article.body + "</div>";
			html +="\n";
			
			if(previusId >= articles.size()) {
				previusId = articles.size();
			}
			if(nextId >= articles.size()) {
				nextId = articles.size();
			}
			if (article.id > articlesId[0]) {
				html += "<div><a href=\"" +boardController.getBoardNameById(article.boardId)+"_"+(articles.get(previusId-1).id)+ ".html\">이전글</a></div>";
				previusId ++;	
			}
			html +="\n";
			if(article.id < articlesId[articles.size()-1]) {
			html += "<div><a href=\"" +boardController.getBoardNameById(article.boardId)+"_"+(articles.get(nextId+1).id)+ ".html\">다음글</a></div>";
				nextId++;
			}
			html +="\n";
			
			
			String boardName = boardController.getBoardNameById(article.boardId);
			String boardpath = ("exportHtml/"+boardName+"\\");
			
			util.writeFileContents(boardpath + fileName, html);
		}
	}

}
