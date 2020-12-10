package Html;

import java.util.List;

import com.sbs.example.mysqlTextBoard.Container;
import com.sbs.example.mysqlTextBoard.controller.BoardController;
import com.sbs.example.mysqlTextBoard.dto.Article;
import com.sbs.example.mysqlTextBoard.dto.Board;
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

	public void makeAllBoardHtml() {
		for (Board board : boardController.getBoards()) {
			Container.session.boardSelectedId = board.id;
			makeHtmlOfSelectedBoard();
			System.out.printf("%s 게시판 html이 생성되었습니다.\n", board.boardName);
		}
		Container.session.boardSelectedId = 0;
	}

	public void makeHtmlOfSelectedBoard() {

		if (Container.session.boardSelectedId == 0) {
			System.out.println("보드 선택이 필요합니다.");
			return;
		}

		exportService.makeHtml();
		memberService.getMembers();
		List<Article> articles = articleService.getArticlesBySelectedBoardId();

		double size = articles.size();
		int page = (int) Math.ceil(size / 10);
		String BoardName = boardController.getBoardNameById(Container.session.boardSelectedId);
		int pageLastNumber = page;
		for (int i = 0; i < page; i++) {

			int itemsInAPage = 10;
			int startPos = (int) size - 1;
			startPos -= (page - 1) * itemsInAPage;
			int endPos = startPos - (itemsInAPage - 1);
			if (endPos < 0) {
				endPos = 0;
			}

			StringBuilder[] sbl = new StringBuilder[page];
			sbl[i] = new StringBuilder();
			insertHtml(sbl[i], page, startPos, endPos, BoardName, pageLastNumber);

			String fileName = boardController.getBoardNameById(Container.session.boardSelectedId) + "list-" + page
					+ ".html";
			String boardName = boardController.getBoardNameById(Container.session.boardSelectedId);
			String boardpath = ("exportHtml/" + boardName + "\\");
			util.mkdirs("exportHtml\"" + articleService.getArticlesBySelectedBoardId());
			util.writeFileContents(boardpath + fileName, sbl[i]);
			page--;
			i--;
		}

	}

	private void insertHtml(StringBuilder sb, int page, int startNum, int endNum, String BoardName,
			int pageLastNumber) {

		String presentBoard = boardController.getSelectedBoardName();
		List<Board> boards = boardController.getBoards();
		String ArticleBoardsList = "";
		for (Board board : boards) {
			ArticleBoardsList += "<li><a href=\"/./work/work/mysql-text-board/exportHtml/" + board.boardName + "/"
					+ board.boardName + "list-" + page + ".html\" class = \"block\"><span>" + board.boardName
					+ "</span></a></li>";
		}
		String head = util.getFileContents("html_template/header.html");
		head = head.replace("[Article_List_Part]", ArticleBoardsList);
		sb.append(head);

		sb.append("<div class=\"top-info\"><h1>" + presentBoard + "게시판" + page + "페이지<h1></div>");

		List<Article> articles = articleService.getArticlesBySelectedBoardId();
		for (int a = startNum; a >= endNum; a--) {
			Article article = articles.get(a);

			sb.append("<li>");
			sb.append("<num>" + article.id + "번 게시물</num><br>");
			sb.append("<a href=\"" + BoardName + "_" + article.id + ".html\"> 제목:" + article.title
					+ "</a>&nbsp;&nbsp;&nbsp;");
			sb.append("<article> 내용 :" + article.body + "</article><br>");
			sb.append("<div> 작성 날짜 :" + article.regDate + "</div><hr><br>");

			sb.append("\n");

		}
		sb.append("</li>");
		sb.append("</ul>");
		sb.append("\n");
		sb.append("<div class=\"hyperlink_page\">");

		sb.append("<ul>");
		sb.append("\n");
		for (int i = 1; i <= pageLastNumber; i++) {
			sb.append("<div><a href = \"" + BoardName + "list-" + i + ".html\">" + i + "</a></div>");
			sb.append("</ul></div>");
			sb.append("\n");
		}
		sb.append(util.getFileContents("html_template/footer.html"));
	}
}
