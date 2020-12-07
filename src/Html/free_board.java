package Html;

import java.util.List;

import com.sbs.example.mysqlTextBoard.Container;
import com.sbs.example.mysqlTextBoard.controller.BoardController;
import com.sbs.example.mysqlTextBoard.dto.Article;
import com.sbs.example.mysqlTextBoard.service.ArticleService;
import com.sbs.example.mysqlTextBoard.service.MemberService;
import com.sbs.java.ssg.util.util;

public class free_board {
	 ArticleService articleService;
	 MemberService memberService;
	BoardController boardController;

	public free_board() {
		articleService = Container.articleService;
		memberService = Container.memberService;
		boardController = new BoardController();
	}

	public  void makeHtmlOfSelectedBoard() {
		
	
		
		if(Container.session.boardSelectedId == 0) {
			System.out.println("보드 선택이 필요합니다.");
			return;
		}
		List<Article> articles = articleService.getArticlesBySelectedBoardId();
		for (Article article : articles) {
			memberService.getMembers();
			StringBuilder sb = new StringBuilder();
			//filename
			String fileName = boardController.getBoardNameById(article.boardId) + ".html";
			//content

			sb.append("<!DOCTYPE html>");
			sb.append("<html lang=\"ko\">");

			sb.append("<head>");
			sb.append("<meta charset=\"UTF-8\">");
			sb.append("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">");
			sb.append("<title> 게시판 정보</title>");
			sb.append("</head>");

			sb.append("<body>");

			sb.append("<h1>자유게시판</h1>");

			sb.append("<div>");
			sb.append("<ul>");
			for(int i =0;i <articleService.getArticlesBySelectedBoardId().size(); i++) {
				sb.append("<li>");
				sb.append("<h2>"+articles.get(i).title+"<h2>&nbsp;");
				sb.append("<h2>"+articles.get(i).body+"<h2><br>");
				sb.append("</li>");
			}
			sb.append("</ul>");
			String boardName = boardController.getBoardNameById(article.boardId);
			String boardpath = ("exportHtml/"+boardName+"\\");
			util.mkdirs("exportHtml\""+ articleService.getArticlesBySelectedBoardId());
			util.writeFileContents(boardpath + fileName, sb);
		}
	}

}

