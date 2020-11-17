package com.sbs.example.mysqlTextBoard.controller;

import java.util.List;
import java.util.Scanner;

import com.sbs.example.mysqlTextBoard.Container;
import com.sbs.example.mysqlTextBoard.dto.Article;
import com.sbs.example.mysqlTextBoard.dto.Board;
import com.sbs.example.mysqlTextBoard.dto.Member;
import com.sbs.example.mysqlTextBoard.service.ArticleService;




public class ArticleController {
	private static ArticleService articleService;
	Scanner sc = Container.scanner;
	

	public ArticleController() {
		articleService = new ArticleService();

	}

	public void doCommand(String cmd) {
		
		if (cmd.startsWith("article list")) {
			
			 if(cmd.split(" ").length <= 2) {
				 if(Container.session.boardSelectedId == 0) {
					 System.out.println("선택된 게시판이 없습니다.");
					 return;
				 }
					showList();
				}
			 
			 else if (cmd.split(" ") [2].equals("all")) {
				showAllList();
			}
			
				
			
		} 
		else if (cmd.startsWith("article detail")) {
			if(cmd.split(" ").length <= 2) {
				System.out.println("게시물 번호를 입력하세요.");
				return;
			}
			int inputid = Integer.parseInt(cmd.split(" ")[2]);
			detailShow(inputid);
		} else if (cmd.startsWith("article modify")) {
			if(cmd.split(" ").length <= 2) {
				System.out.println("게시물 번호를 입력하세요.");
				return;
			}
			int inputid = Integer.parseInt(cmd.split(" ")[2]);
			
			
			
			
			articleModified(inputid);
		} else if (cmd.startsWith("article delete")) {
			if(cmd.split(" ").length <= 2) {
				System.out.println("게시물 번호를 입력하세요.");
				return;
			}
			int inputid = Integer.parseInt(cmd.split(" ")[2]);
			deleteArticleById(inputid);
		}
		else if(cmd.equals("article write")) {
			doWrite();
		}
	}

	private void showAllList() {
		System.out.println("== 게시물 리스트 ==");

		List<Article> articles = articleService.getAllArticles();

		System.out.println("번호 / 분류 /         작성일       /         수정일        / 작성자 / 제목");

		for (Article article : articles) {
			System.out.printf("%d / %s / %s / %s / %s / %s\n", article.id,getBoardNameByBoardId(article.boardId), article.regDate, article.updateDate,
					getMemberIdByMemberIndex(article.memberId), article.title);
		}
		
	}

	private void doWrite() {
		if(Container.session.loginedId == 0) {
			System.out.println("로그인 후 시도하세요");
			return;
		}
		if(Container.session.boardSelectedId == 0) {
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
			articleService.doWrite(memberId,boardId,title,body);
		
	}

	public void showList() {
		System.out.println("== 게시물 리스트 ==");

		List<Article> articles = articleService.getArticlesBySelectedBoardId();
		
		System.out.println("번호 / 분류 /         작성일       /         수정일        / 작성자 / 제목");

		for (Article article : articles) {
			System.out.printf("%d / %s / %s / %s / %s / %s\n", article.id,getBoardNameByBoardId(article.boardId), article.regDate, article.updateDate,
					getMemberIdByMemberIndex(article.memberId), article.title);
		}
	}

	
	public void detailShow(int inputid) {
		if (inputid > getArticlesSize()) {
			System.out.println("존재하지 않는 게시물 입니다.");
			return;
		}
		
		Article article = articleService.getDetailById(inputid);
		if (article == null) {
			System.out.println("존재하지 않는 게시물 입니다.");
		}
		System.out.println("== 게시판 상세 정보 == ");
		System.out.printf("번호 : %d\n", article.id);
		System.out.printf("등록 날짜 : %s\n", article.regDate);
		System.out.printf("제목 : %s\n", article.title);
		System.out.printf("내용 : %s\n", article.body);
		System.out.printf("작성자 : %d\n", article.memberId);
	}

	public void articleModified(int inputid) {
		System.out.println("== 게시물 수정 == ");
		System.out.println("변경할 제목 : ");
		String title = sc.nextLine();
		System.out.println("변경할 이름 : ");
		String body = sc.nextLine();
		
		articleService.articleModified(inputid, title, body);

	}

	public void deleteArticleById(int inputid) {
		int checkid =0;
		if(Container.session.loginedId == 0) {
			System.out.println("로그인 후 시도하세요");
			return;
		}
		for(Article article : getArticles()) {
			if(article.id == inputid ) {
				checkid = article.memberId;
			}
		}
		if(checkid != Container.session.loginedId) {
			System.out.println("게시물은 작성자만 삭제할 수 있습니다.");
			return;
		}
		
		 articleService.deleteArticleById(inputid);
	}
	public String getMemberIdByMemberIndex(int memberId) {
		for(Member member : Container.memberDao.getMembers()) {
			if(member.memberIndex ==memberId) {
				return member.memberName;
			}
		}
		return null;
	}
	public String getBoardNameByBoardId(int boardId) {
		for (Board board : Container.boardDao.getBoards()) {
			if(board.id == boardId) {
				return board.boardName;
			}
			
		}
		return null;
				
	}
	public List<Article> getArticles(){
		return articleService.getAllArticles();
	}
	public int getArticlesSize() {
		return articleService.getAllArticles().size();
	}


}
