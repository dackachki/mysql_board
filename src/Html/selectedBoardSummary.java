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
			
			exportService.makeHtml();
			memberService.getMembers();

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

	public void insertHtml(StringBuilder sb, int page, int startNum, int endNum, String BoardName,
			int pageLastNumber) {

		String presentBoard = boardController.getSelectedBoardName();
		List<Board> boards = boardController.getBoards();
		String ArticleBoardsList = "";
		
		for (Board board : boards) {
			ArticleBoardsList += "<li><a href=\"/./work/work/mysql-text-board/exportHtml/" + board.boardName + "/"
					+ board.boardName + "list-" + 1 + ".html\" class = \"block\"><span>" + board.boardName
					+ "</span></a></li>";
		}
		String head = util.getFileContents("html_template/header.html");
		head = head.replace("[Article_List_Part]", ArticleBoardsList);
		
		
		String directory=" <section class=\"title-bar con-min-width\">\r\n"
				+ "      <h1 class=\"con\">\r\n"
				+ 		getIconByBoardName(presentBoard)
				+ "        <span>"+presentBoard+"게시판"+"</span>\r\n"
				+ "      </h1>\r\n"
				+ "    </section>";
		head = head.replace("[Directory_Show]", directory);
		
		List<Article> articles = articleService.getArticlesBySelectedBoardId();
		StringBuilder Listsb = new StringBuilder();
		 Listsb.append("<main>");
		 Listsb.append("\n");
		for (int a = startNum; a >= endNum; a--) {
			Article article = articles.get(a);
			 Listsb.append("<div>");
			 Listsb.append("\n");
			 Listsb.append("<div class=\"article-list__cell-id\">"+article.id+"</div>");
			 Listsb.append("\n");
			 Listsb.append("<div class=\"article-list__cell-reg-date\">"+article.regDate+"</div>");   
			 Listsb.append("\n");
			 Listsb.append("<div class=\"article-list__cell-writer\">"+article.extra_writer+"</div>");  
			 Listsb.append("\n");
			 Listsb.append("<div class=\"article-list__cell-title\"><a href=\""+presentBoard+"_"+article.id+".html\">"+article.title);
			 Listsb.append("\n");
	         Listsb.append("</a>");  
	         Listsb.append("</div>");
	         Listsb.append("</div>");
	         
		}
		Listsb.append("</div>");
		Listsb.append("</main>");
		
		Listsb.append("</section>");
		Listsb.append("</div>"); 
		Listsb.append("</div>"); 
		Listsb.append("</section>");
		Listsb.append("</main>");
		
		Listsb.append("<div class=\"hyperlink_page\">");

		Listsb.append("<ul>");
		
		
		for (int i = 1; i <= pageLastNumber; i++) {
			Listsb.append("<li>");
			Listsb.append("<a href = \"" + BoardName + "list-" + i + ".html\">" + i + "</a>");
			Listsb.append("</li>");
			
		}
		Listsb.append("</ul>");
		Listsb.append("</div>");
		String articleListHtml = Listsb.toString();
		head = head.replace("[Article_List]", articleListHtml);
		sb.append(util.getFileContents("html_template/footer.html"));
		
		
		sb.append(head);
	}
	private String getIconByBoardName(String boardName) {
		String Icon = "";
		if(boardName.startsWith("free")) {
		Icon="<i class=\"fas fa-smile\"></i>";
		}
		else if(boardName.startsWith("notice")) {
			Icon = "<i class=\"fas fa-flag\"></i>";
		}
		else if(boardName.startsWith("info")) {
			Icon ="<i class=\"fas fa-info\"></i>";
		}
		return Icon;
	}
}