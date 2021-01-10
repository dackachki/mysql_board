package com.sbs.example.mysqlTextBoard.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.sbs.example.mysqlTextBoard.Container;
import com.sbs.example.mysqlTextBoard.dto.Article;
import com.sbs.example.mysqlTextBoard.dto.Recommand;
import com.sbs.example.mysqlTextBoard.dto.Reply;
import com.sbs.example.mysqlutil.MysqlUtil;
import com.sbs.example.mysqlutil.SecSql;

public class ArticleDao {
		int articleSize = 0;
	

	public int getArticleSize() {
		return articleSize;
	}
	
	public List<Article> getAllArticles() {
		List<Article> articles = new ArrayList<>();
		List<Map<String, Object>> articleListMap = MysqlUtil
				.selectRows(new SecSql().append("SELECT article.*,members.memberName as extra_writer FROM article join members where article.memberId = members.id;"));
				for (Map<String, Object> articleMap : articleListMap) {
					Article article = new Article(articleMap);
					articles.add(article);
					

				}
				return articles;
	}
	public List<Article> getArticles() {
		List<Article> articles = new ArrayList<>();
		List<Map<String, Object>> articleListMap = MysqlUtil
				.selectRows(new SecSql().append("SELECT article.*,members.memberName as extra_writer from article join members where article.memberId = members.id " ));
			

		for (Map<String, Object> articleMap : articleListMap) {
			Article article = new Article(articleMap);
			articles.add(article);

		}
		articleSize = articles.size();
		return articles;
	}

	public Article getDetailById(int inputid) {

		Map<String, Object> articleMap = MysqlUtil
				.selectRow(new SecSql().append("SELECT * FROM article where id = ? ; ", inputid));
		if (articleMap.isEmpty()) {

			return null;
		}

		return new Article(articleMap);

	}

	public static void articleModified(int inputid, String title, String body) {

		MysqlUtil.update(new SecSql().append("update article set title = ?,body = ?,updateDate = now() where id = ?",
				title, body, inputid));

		System.out.printf("%d번 게시물이 수정되었습니다.\n", inputid);
	}

	public void deleteArticleById(int inputid) {
		MysqlUtil.update(new SecSql().append("delete from article where id = ?", inputid));

		System.out.printf("%d번 게시물이 삭제되었습니다.\n", inputid);

	}

	public void doWrite(int memberId, int boardId, String title, String body) {

		MysqlUtil.update(new SecSql().append(
				"insert into article set title = ?,body = ?,memberId =? ,boardId = ?;", title, body,
				memberId, boardId));
		Map<String, Object> tableStatus = MysqlUtil.selectRow(new SecSql().append("SHOW TABLE STATUS WHERE NAME = 'article'"));
		String aaa= String.valueOf(tableStatus.get("Auto_increment"));
		int articleNum = Integer.parseInt(aaa);
		System.out.printf("%d번 게시물이 생성되었습니다.\n", articleNum -1);


	}
	public void doWriteReply(int inputid, String replyBody) {
		int writeMemberId = Container.session.loginedId;
		MysqlUtil.update(new SecSql().append("insert into replies set bodyR = ?,writeMemberId = ?,articleNumber = ?;", replyBody, writeMemberId,
				inputid));
		
		System.out.printf("%d번 게시물에 댓글이 생성되었습니다.\n", inputid);
		
	}

	public List<Reply> getArticleReplyById(int inputid) {
		List<Reply> repliesById = new ArrayList<>();
		List<Map<String, Object>> replyListMap = MysqlUtil
				.selectRows(new SecSql().append("SELECT * FROM replies where articleNumber = ?", inputid));

		for (Map<String, Object> replyMap : replyListMap) {
			Reply reply = new Reply(replyMap);
				repliesById.add(reply);

			}
		
		return repliesById;	
			
		}

	public void doReplyModify(int replyId, String replyBody) {
		MysqlUtil.update(new SecSql().append(
				"update replies set bodyR = ?, updateDate = now() where id = ?;",replyBody,replyId));
		
		/*SecSql sql = new SecSql();
		sql.append("update replies set bodyR = ?, updateDate = now() where id = ?;",replyBody,replyId);
		*/
		System.out.printf("댓글이 수정되었습니다.\n");
		
	}

	public void doReplyDelete(int replyId) {
		MysqlUtil.update(new SecSql().append("delete from replies where id = ?;",replyId));
		
		/*SecSql sql = new SecSql();
		
		sql.append("delete from replies where id = ?;",replyId);
		System.out.printf("게시물에 댓글이 삭제되었습니다.\n");
		*/
	}

	public List<Reply> getAllReplies() {
		List<Reply> allReplies = new ArrayList<>();
		List<Map<String, Object>> replyListMap = MysqlUtil.selectRows(new SecSql().append("SELECT * FROM replies;"));

		for (Map<String, Object> replyMap : replyListMap) {
			Reply reply = new Reply(replyMap);
			allReplies.add(reply);

			}
		
		return allReplies;	
			
		}

	public void articleRecommand() {
		List<Recommand> recommandList = new ArrayList<>();
		List<Map<String, Object>> recommandListMap = MysqlUtil.selectRows(new SecSql()
				.append("select a.id as '게시물번호',group_concat(r.memberId) as '추천인', count(r.memberId) as '추천수' from recommand as r join article as a on a.id = r.articleNum group by a.id;"));	
		System.out.println(recommandListMap);
		
		for (Map<String, Object> recommandMap : recommandListMap) {
			Recommand recommand = new Recommand(recommandMap);
				recommandList.add(recommand);
			
		}
		System.out.println(recommandList);
		
	}

	public Object updatePageHits() {
		
		return null;
	}
		
	}


