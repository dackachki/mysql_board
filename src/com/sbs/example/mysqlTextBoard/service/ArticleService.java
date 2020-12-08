package com.sbs.example.mysqlTextBoard.service;

import java.util.ArrayList;
import java.util.List;

import com.sbs.example.mysqlTextBoard.Container;
import com.sbs.example.mysqlTextBoard.dao.ArticleDao;
import com.sbs.example.mysqlTextBoard.dto.Article;
import com.sbs.example.mysqlTextBoard.dto.Reply;

public class ArticleService {
	private ArticleDao articleDao;

	public ArticleService() {
		articleDao = new ArticleDao();
	}

	public List<Article> getArticlesBySelectedBoardId() {
		List<Article> articlesByBoardId = new ArrayList<>();
		for (Article article : articleDao.getArticles()) {
			if (article.boardId == Container.session.boardSelectedId) {
				articlesByBoardId.add(article);

			}
		}

		return articlesByBoardId;
	}

	public void articleModified(int inputid, String title, String body) {
		ArticleDao.articleModified(inputid, title, body);

	}

	public Article getDetailById(int inputid) {
		return articleDao.getDetailById(inputid);
	}

	public void deleteArticleById(int inputid) {
		articleDao.deleteArticleById(inputid);
	}

	public void doWrite(int memberId, int boardId, String title, String body) {
		articleDao.doWrite(memberId, boardId, title, body);

	}

	public List<Article> getAllArticles() {
		return articleDao.getAllArticles();

	}

	public void doWriteReply(int inputid, String replyBody) {
		 articleDao.doWriteReply(inputid,replyBody);
	}

	public List<Reply> getArticleReplyById(int inputid) {
			return articleDao.getArticleReplyById(inputid);
	}

	public void doReplyModify(int replyId, String replyBody) {
		articleDao.doReplyModify(replyId,replyBody);
		
	}

	public void doReplyDelete(int replyId) {
		articleDao.doReplyDelete(replyId);
		
	}
	public List<Reply> getAllReplies(){
		return articleDao.getAllReplies();
	}

	public void articleRecommand() {
		articleDao.articleRecommand();
		
	}
	
}
