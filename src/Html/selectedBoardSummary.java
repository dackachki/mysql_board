package Html;

import java.util.List;

import com.sbs.example.mysqlTextBoard.Container;
import com.sbs.example.mysqlTextBoard.controller.BoardController;
import com.sbs.example.mysqlTextBoard.dto.Article;
import com.sbs.example.mysqlTextBoard.service.ArticleService;
import com.sbs.example.mysqlTextBoard.service.ExportService;
import com.sbs.example.mysqlTextBoard.service.MemberService;
import com.sbs.java.ssg.util.util;

public class selectedBoardSummary {
	ArticleService articleService;
	MemberService memberService;
	BoardController boardController;
	ExportService exportService;

	public selectedBoardSummary() {
		articleService = Container.articleService;
		memberService = Container.memberService;
		boardController = new BoardController();
		exportService = Container.exportService;
	}

	public void makeHtmlOfSelectedBoard() {

		if (Container.session.boardSelectedId == 0) {
			System.out.println("보드 선택이 필요합니다.");
			return;
		}

		exportService.makeHtml();
		memberService.getMembers();
		List<Article> articles = articleService.getArticlesBySelectedBoardId();
		StringBuilder sb = new StringBuilder();
		int page = 2 ;
		
		while (page > 1) {
			double size = articles.size();
			page = (int) Math.ceil(size / 10);

			int itemsInAPage = 10;
			int startPos = (int) size - 1;
			startPos -= (page - 1) * itemsInAPage;
			int endPos = startPos - (itemsInAPage - 1);

			if (endPos < 0) {
				endPos = 0;
			}

			for (int i = page; i < 1; i--) {
				StringBuilder[] sbl = new StringBuilder[page];
				insertHtml(sbl[i], page, startPos, endPos);

				
				String fileName = boardController.getBoardNameById(Container.session.boardSelectedId) + " list-" + page
						+ ".html";
				String boardName = boardController.getBoardNameById(Container.session.boardSelectedId);
				String boardpath = ("exportHtml/" + boardName + "\\");
				util.mkdirs("exportHtml\"" + articleService.getArticlesBySelectedBoardId());
				util.writeFileContents(boardpath + fileName, sb);
				page--;
			}

		}
	}

	private void insertHtml(StringBuilder sb, int page, int startNum, int endNum) {

		String presentBoard = boardController.getSelectedBoardName();
		sb.append("<!DOCTYPE html>");
		sb.append("<html lang=\"ko\">");

		sb.append("<head>");
		sb.append("<meta charset=\"UTF-8\">");
		sb.append("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">");
		sb.append("<title> 게시판 정보</title>");
		sb.append("<style>");
		sb.append("\r\n" + "@font-face {\r\n" + "  font-family: 'LotteMartDream';\r\n" + "  font-style: normal;\r\n"
				+ "  font-weight: 400;\r\n"
				+ "  src: url('//cdn.jsdelivr.net/korean-webfonts/1/corps/lottemart/LotteMartDream/LotteMartDreamMedium.woff2') format('woff2'), url('//cdn.jsdelivr.net/korean-webfonts/1/corps/lottemart/LotteMartDream/LotteMartDreamMedium.woff') format('woff');\r\n"
				+ "}\r\n" + "@font-face {\r\n" + "  font-family: 'LotteMartDream';\r\n" + "  font-style: normal;\r\n"
				+ "  font-weight: 700;\r\n"
				+ "  src: url('//cdn.jsdelivr.net/korean-webfonts/1/corps/lottemart/LotteMartDream/LotteMartDreamBold.woff2') format('woff2'), url('//cdn.jsdelivr.net/korean-webfonts/1/corps/lottemart/LotteMartDream/LotteMartDreamBold.woff') format('woff');\r\n"
				+ "}\r\n" + "@font-face {\r\n" + "  font-family: 'LotteMartDream';\r\n" + "  font-style: normal;\r\n"
				+ "  font-weight: 300;\r\n"
				+ "  src: url('//cdn.jsdelivr.net/korean-webfonts/1/corps/lottemart/LotteMartDream/LotteMartDreamLight.woff2') format('woff2'), url('//cdn.jsdelivr.net/korean-webfonts/1/corps/lottemart/LotteMartDream/LotteMartDreamLight.woff') format('woff');\r\n"
				+ "}\r\n" + "html {\r\n" + "  font-family: 'LotteMartDream', sans-serif;\r\n" + "}");

		sb.append("body,ul,li { font-size:1rem;list-style:none;margin:0;padding:0;"
				+ "background-color:black; color:white;}");
		sb.append("a { text-decoration:none; color:inherit;}");
		sb.append("div> ul li>a { ");
		sb.append("font-size:2rem" + "text-decoration:underline;}");
		sb.append(".pagenum {\r\n" + "   width:100%;\r\n" + "   height:100px;\r\n" + "   position:absolute;\r\n"
				+ "   bottom:0;\r\n" + "   background:black;\r\n" + "  text-align: center;\r\n" + "  color: white;\r\n"
				+ "  font-size:3rem;\r\n;}");
		sb.append("</style>");
		sb.append("</head>");

		sb.append("<body>");

		sb.append("<div class=\"top-info\"><h1>" + presentBoard + "게시판 페이지<h1></div>");
		sb.append("<div class=\"pagenum\">");
		List<Article> articles = articleService.getArticlesBySelectedBoardId();
		for (int a = startNum; a >= endNum; a--) {
			Article article = articles.get(a);

			sb.append("<li>");
			sb.append("<num>" + article.id + "번 게시물</num><br>");
			sb.append("<a href=\"" + article.id + ".html\"> 제목:" + article.title + "</a>&nbsp;&nbsp;&nbsp;");
			sb.append("<article> 내용 :" + article.body + "</article><hr><br>");

			sb.append("\n");

		}
		sb.append("</li>");
		sb.append("</ul>");
		sb.append("<div>");
		sb.append("<ul>");

	}
}
