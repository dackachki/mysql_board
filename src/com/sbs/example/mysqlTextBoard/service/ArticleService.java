package com.sbs.example.mysqlTextBoard.service;

import java.util.List;

import com.sbs.example.mysqlTextBoard.dao.ArticleDao;
import com.sbs.example.mysqlTextBoard.dto.Article;

public class ArticleService {
	private ArticleDao articleDao;

	public ArticleService() {
		articleDao = new ArticleDao();
	}

	public List<Article> getArticles() {
		return articleDao.getArticles();
	}

	
	
	public static String articleModified(int inputid, String title, String body) {
		return ArticleDao.articleModified(inputid,title, body);
	

}

	public Article getDetailById(int inputid) {
		return articleDao.getDetailById(inputid);
	}

	

	public void deleteArticleById(int inputid) {
		 articleDao.deleteArticleById(inputid);
}

	public void doWrite(int memberId, int boardId, String title, String body) {
		articleDao.doWrite(memberId,boardId,title,body);
		
	}
}
