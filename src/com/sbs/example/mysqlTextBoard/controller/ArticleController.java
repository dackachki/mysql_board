package com.sbs.example.mysqlTextBoard.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import com.sbs.example.mysqlTextBoard.Container;
import com.sbs.example.mysqlTextBoard.dto.Article;
import com.sbs.example.mysqlTextBoard.dto.Board;
import com.sbs.example.mysqlTextBoard.dto.Member;
import com.sbs.example.mysqlTextBoard.dto.Reply;
import com.sbs.example.mysqlTextBoard.service.ArticleService;

public class ArticleController extends Controller {
	private static ArticleService articleService;
	Scanner sc = Container.scanner;

	public ArticleController() {
		articleService = new ArticleService();

	}

	public void doCommand(String cmd) {

		if (cmd.startsWith("article list")) {

			if (cmd.split(" ").length <= 2) {
				if (Container.session.boardSelectedId == 0) {
					System.out.println("선택된 게시판이 없습니다.");
					return;
				}
				showList();
			}

			else if (cmd.split(" ")[2].equals("all")) {
				showAllList();
			}

		} else if (cmd.startsWith("article detail")) {
			if (cmd.split(" ").length <= 2) {
				System.out.println("게시물 번호를 입력하세요.");
				return;
			}
			int inputid = Integer.parseInt(cmd.split(" ")[2]);

			detailShow(inputid);

		} else if (cmd.startsWith("article modify")) {
			if (cmd.split(" ").length <= 2) {
				System.out.println("게시물 번호를 입력하세요.");
				return;
			}
			int inputid = Integer.parseInt(cmd.split(" ")[2]);

			articleModified(inputid);
		} else if (cmd.startsWith("article delete")) {
			if (cmd.split(" ").length <= 2) {
				System.out.println("게시물 번호를 입력하세요.");
				return;
			}
			int inputid = Integer.parseInt(cmd.split(" ")[2]);

			deleteArticleById(inputid);

		} else if (cmd.equals("article write")) {
			doWrite();
		} else if (cmd.startsWith("article addreply")) {
			if (cmd.split(" ").length <= 2) {
				System.out.println("게시물 번호를 입력하세요.");
				return;
			}
			if (Container.session.loginedId == 0) {
				System.out.println("로그인 후 다시 시도하세요.");
				return;
			}
			int inputid = Integer.parseInt(cmd.split(" ")[2]);
			doWriteReply(inputid);

		} else if (cmd.equals("article replymodify")) {
		
			if (Container.session.loginedId == 0) {
				System.out.println("로그인 후 다시 시도하세요.");
				return;
			}

			
			doReplyModify();

		} else if (cmd.equals("article replydelete")) {
			if (Container.session.loginedId == 0) {
				System.out.println("로그인 후 다시 시도하세요.");
				return;
			}
			doReplyDelete();
		}
		else if(cmd.startsWith("article recommand")) {
			if (Container.session.loginedId == 0) {
				System.out.println("로그인 후 다시 시도하세요.");
				return;
			}
			int inputid = Integer.parseInt(cmd.split(" ")[2]);
			articleRecommand(inputid);
			
		}
	}

	private void articleRecommand(int inputid) {
			articleService.articleRecommand(inputid);
	}

	private void doReplyDelete() {
		System.out.println("== 댓글 삭제 ==");
		System.out.println("== 회원님의 댓글 리스트 ==");
		System.out.println("댓글ID /   생성일   / 게시물 번호 / 내용");
		for (Reply reply : getRepliesForLoginedMember()) {
			if (reply.writeMemberId == Container.session.loginedId) {
				System.out.printf("%d /%s   / %d  /  %s\n", reply.id, reply.regDate, reply.articleNumber, reply.bodyR);
			}
		}
		System.out.println("삭제할 댓글 id 입력 : ");
		int replyId = sc.nextInt();
		articleService.doReplyDelete(replyId);

	}

	private void doReplyModify() {
		System.out.println("== 리플 수정 ==");
		System.out.println("== 회원님의 댓글 리스트 ==");
		System.out.println("댓글ID /   생성일   / 게시물 번호 / 내용");
		if(getRepliesForLoginedMember().isEmpty()) {
			System.out.println("작성한 댓글이 없습니다.");
			return;
		}
		else {
			for(Reply reply : getRepliesForLoginedMember()) {
				System.out.printf("%d /%s   / %d   /  %s\n", reply.id, reply.regDate, reply.articleNumber, reply.bodyR);
			}
		}
		System.out.printf("수정할 댓글 id : ");
		int replyId = sc.nextInt();
		 sc.nextLine(); 
		System.out.printf("수정할 댓글 내용 : ");
		String replyBody = sc.nextLine();
		articleService.doReplyModify(replyId, replyBody);

	}

	private List<Reply> getRepliesForLoginedMember() {
		List<Reply> repliesByLoginedMember = new ArrayList<>();
		int getMemberId = Container.session.loginedId;
		for(Reply reply : articleService.getAllReplies()) {
			if(reply.writeMemberId == getMemberId)
				repliesByLoginedMember.add(reply);
		}
		return repliesByLoginedMember;
	}
		

	private void doWriteReply(int inputid) {
		System.out.println("== 게시물 댓글 입력 ==");
		System.out.printf("댓글 내용 : ");
		String replyBody = sc.nextLine();
		articleService.doWriteReply(inputid, replyBody);
	}

	private void showAllList() {
		System.out.println("== 게시물 리스트 ==");

		List<Article> articles = articleService.getAllArticles();

		System.out.println("번호 / 분류 /         작성일       /         수정일        / 작성자 / 작성자번호 / 제목");

		for (Article article : articles) {
			System.out.printf("%d / %s / %s / %s / %s / %d / %s\n", article.id, getBoardNameByBoardId(article.boardId),
					article.regDate, article.updateDate, article.extra_writer, article.memberId, article.title);
		}

	}

	private void doWrite() {
		if (Container.session.loginedId == 0) {
			System.out.println("로그인 후 시도하세요");
			return;
		}
		if (Container.session.boardSelectedId == 0) {
			System.out.println("게시판 선택 후 다시 시도하세요");
			return;
		}
		System.out.println("== 게시물 작성 == ");
		System.out.printf("제목 : ");
		String title = sc.nextLine();
		System.out.printf("내용 : ");
		String body = sc.nextLine();
		int memberId = Container.session.loginedId;
		int boardId = Container.session.boardSelectedId;
		articleService.doWrite(memberId, boardId, title, body);

	}

	public void showList() {
		System.out.println("== 게시물 리스트 ==");

		List<Article> articles = articleService.getArticlesBySelectedBoardId();

		System.out.println("번호 / 분류 /         작성일       /         수정일        / 작성자 / 제목");

		for (Article article : articles) {
			System.out.printf("%d / %s / %s / %s / %s / %s\n", article.id, getBoardNameByBoardId(article.boardId),
					article.regDate, article.updateDate, article.extra_writer, article.title);
		}
	}

	public void detailShow(int inputid) {
		if (inputid > getArticlesSize()) {
			System.out.println("존재하지 않는 게시물 입니다.");
			return;
		}
		if (Container.session.loginedId == 0) {
			System.out.println("로그인 후 시도하세요");
			return;
		}

		Article article = articleService.getDetailById(inputid);
		if (article == null) {
			System.out.println("존재하지 않는 게시물 입니다.");
			return;
		}

		System.out.println("== 게시판 상세 정보 == ");
		System.out.printf("번호 : %d\n", article.id);
		System.out.printf("등록 날짜 : %s\n", article.regDate);
		System.out.printf("제목 : %s\n", article.title);
		System.out.printf("내용 : %s\n", article.body);
		System.out.printf("작성자 : %s\n", getMemberIdByMemberIndex(article.memberId));

		List<Reply> getReplies = getArticleReplyById(inputid);
		if (getReplies.isEmpty()) {
			System.out.println("게시물의 댓글이 없습니다.");

		} else {
			System.out.printf("== %d번 게시물 댓글 리스트 ==\n", inputid);
			System.out.println("댓글 ID /    생성일    /    수정일    /내용   / 작성자 ");
			for (Reply reply : getReplies) {

				System.out.printf("%d / %s /   %s   \n", reply.id, reply.regDate, reply.updateDate, reply.bodyR,
						getMemberIdByMemberIndex(reply.writeMemberId));
			}
		}

	}

	private List<Reply> getArticleReplyById(int inputid) {
		return articleService.getArticleReplyById(inputid);

	}

	public void articleModified(int inputid) {
		int checkid = 0;
		for (Article article : getArticles()) {
			if (article.id == inputid) {
				checkid = article.memberId;
			}
		}
		if (checkid != Container.session.loginedId) {
			System.out.println("게시물은 작성자만 수정할 수 있습니다.");
			return;
		}
		System.out.println("== 게시물 수정 == ");
		System.out.println("변경할 제목 : ");
		String title = sc.nextLine();
		System.out.println("변경할 내용 : ");
		String body = sc.nextLine();

		articleService.articleModified(inputid, title, body);

	}

	public void deleteArticleById(int inputid) {
		int checkid = 0;
		if (Container.session.loginedId == 0) {
			System.out.println("로그인 후 시도하세요");
			return;
		}
		for (Article article : getArticles()) {
			if (article.id == inputid) {
				checkid = article.memberId;
			}
		}
		if (checkid != Container.session.loginedId) {
			System.out.println("게시물은 작성자만 삭제할 수 있습니다.");
			return;
		}

		articleService.deleteArticleById(inputid);
	}

	public String getMemberIdByMemberIndex(int memberId) {
		for (Member member : Container.memberDao.getMembers()) {
			if (member.memberIndex == memberId) {
				return member.memberName;
			}
		}
		return null;
	}

	public String getBoardNameByBoardId(int boardId) {
		for (Board board : Container.boardDao.getBoards()) {
			if (board.id == boardId) {
				return board.boardName;
			}

		}
		return null;

	}

	public List<Article> getArticles() {
		return articleService.getAllArticles();
	}

	public int getArticlesSize() {
		return articleService.getAllArticles().size();
	}

}
