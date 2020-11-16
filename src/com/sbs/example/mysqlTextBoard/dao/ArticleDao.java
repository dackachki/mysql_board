package com.sbs.example.mysqlTextBoard.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.sbs.example.mysqlTextBoard.dto.Article;
import com.sbs.example.mysqlutil.MysqlUtil;
import com.sbs.example.mysqlutil.SecSql;

public class ArticleDao {
	

	public List<Article> getArticles() {
		List<Article> articles = new ArrayList<>();
		Connection con = null;

		
		List<Map<String, Object>> articleListMap = MysqlUtil.selectRows(new SecSql().append("SELECT * FROM article"));
		System.out.println("articleListMap : " + articleListMap);
		for(Map<String, Object> articleMap : articleListMap) {
			Article article = new Article();
			article.id = (int) articleMap.get("id");
			article.regDate =(String) articleMap.get("regDate");
			article.updateDate =(String) articleMap.get("updateDate");
			article.title =(String) articleMap.get("title");
			article.body =(String) articleMap.get("body");
			article.memberId =(int) articleMap.get("memberId");
			article.boardId =(int) articleMap.get("boardId");
			articles.add(article);
			
			
		}
			
			
			

		
		return articles;
	}

	public static Article getDetailById(int inputid) {
		Connection con = null;
		Article article = new Article();

		try {

			String dbmsJdbcUrl = "jdbc:mysql://127.0.0.1:3306/a2?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeNehavior=convertToNull&connectTimeout=60000&socketTimeout=60000";
			String dbmsLoginId = "sbsst";
			String dbmsLoginPw = "sbs123414";

			// 연결 생성
			try {
				con = DriverManager.getConnection(dbmsJdbcUrl, dbmsLoginId, dbmsLoginPw);
			} catch (SQLException e) {
				e.printStackTrace();
			}

			String sql = ("SELECT * FROM article where id =" + inputid + ";");

			try {
				PreparedStatement pstmt = con.prepareStatement(sql);
				
				ResultSet rs = pstmt.executeQuery();

				rs.next();
					int id = rs.getInt("id");
					String regDate = rs.getString("regDate");
					String updateDate = rs.getString("updateDate");
					String title = rs.getString("title");
					String body = rs.getString("body");
					int memberId = rs.getInt("memberId");
					int boardId = rs.getInt("boardId");

					article = new Article(id, regDate, updateDate, title, body, memberId, boardId);
				
			} catch (SQLException e) {
				e.printStackTrace();
			}

		} finally {
			try {
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return article;
	}

	public static String articleModified(int inputid, String title, String body) {
		Connection con = null;

		try {

			String dbmsJdbcUrl = "jdbc:mysql://127.0.0.1:3306/a2?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeNehavior=convertToNull&connectTimeout=60000&socketTimeout=60000";
			String dbmsLoginId = "sbsst";
			String dbmsLoginPw = "sbs123414";

			// 연결 생성
			try {
				con = DriverManager.getConnection(dbmsJdbcUrl, dbmsLoginId, dbmsLoginPw);
			} catch (SQLException e) {
				e.printStackTrace();
			}

			String sql = "update article set title = ?";
					sql+= ", body = ?"; 
					sql+= ", updateDate = now()";
					sql+= "where id = ?;";

			if (title == "") {
				sql = ("update article set body = '" + body + "' where id =" + inputid + ";");
			} else if (body == "") {
				sql = ("update article set title = '" + title + "' where id =" + inputid + ";");
			}

			try {
				PreparedStatement pstmt = con.prepareStatement(sql);
				
				pstmt.setString(1, title);
				pstmt.setString(2, body);
				pstmt.setInt(3, inputid);
				pstmt.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		} finally {
			try {
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		System.out.printf("%d번 게시물이 수정되었습니다.\n", inputid);
		return "수정되었습니다.";
	}

	public void deleteArticleById(int inputid) {
		Connection con = null;

		try {

			String dbmsJdbcUrl = "jdbc:mysql://127.0.0.1:3306/a2?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeNehavior=convertToNull&connectTimeout=60000&socketTimeout=60000";
			String dbmsLoginId = "sbsst";
			String dbmsLoginPw = "sbs123414";

			// 연결 생성
			try {
				con = DriverManager.getConnection(dbmsJdbcUrl, dbmsLoginId, dbmsLoginPw);
			} catch (SQLException e) {
				e.printStackTrace();
			}

			String sql = ("delete FROM article where id =" + inputid + ";");

			try {
				PreparedStatement pstmt = con.prepareStatement(sql);
				pstmt.executeUpdate();

			} catch (SQLException e) {
				e.printStackTrace();
			}

		} finally {
			try {
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		System.out.printf("%d번 게시물이 삭제되었습니다.\n", inputid);
		
	}

	public void doWrite(int memberId, int boardId, String title, String body) {
		Connection con = null;
		int id = 0;
		try {

			String dbmsJdbcUrl = "jdbc:mysql://127.0.0.1:3306/a2?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeNehavior=convertToNull&connectTimeout=60000&socketTimeout=60000";
			String dbmsLoginId = "sbsst";
			String dbmsLoginPw = "sbs123414";

			// 연결 생성
			try {
				con = DriverManager.getConnection(dbmsJdbcUrl, dbmsLoginId, dbmsLoginPw);
			} catch (SQLException e) {
				e.printStackTrace();
			}

			String sql = "insert into article set title = ?";
				sql+= ", body = ?" ;
				sql+= ",memberId = ? ";
				sql+= ",boardId = ? ;";	
			try {
				PreparedStatement pstmt = con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
				pstmt.setString(1, title);
				pstmt.setString(2, body);
				pstmt.setInt(3, memberId);
				pstmt.setInt(4, boardId);
				
				pstmt.executeUpdate();
				ResultSet rs = pstmt.getGeneratedKeys();
				
				rs.next();
				id = rs.getInt(1);
			} catch (SQLException e) {
				e.printStackTrace();
			}

		} finally {
			try {
				if (con != null) {
					con.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		System.out.printf("%d번 게시물이 생성되었습니다.\n", id);
		
		
	}

}
