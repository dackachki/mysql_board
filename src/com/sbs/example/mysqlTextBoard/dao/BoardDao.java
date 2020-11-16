package com.sbs.example.mysqlTextBoard.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.sbs.example.mysqlTextBoard.dto.Board;


public class BoardDao {
	
	
	
	public List<Board> getBoards() {
		return  getBoardList();
	}
	public void makeBoard(String boardName) {
			
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

			String sql = "insert into board set boardName = ?;";

			try {
				PreparedStatement pstmt = con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
				pstmt.setString(1, boardName);
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

	System.out.printf("%d번 게시판이 생성되었습니다.",id);
	
	}
	public List<Board> getBoardList() {
		List<Board> boards = new ArrayList<>();
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

			String sql = "SELECT * FROM board;";

			try {
				PreparedStatement pstmt = con.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery();

				while (rs.next()) {

					int id = rs.getInt("id");
					String boardName = rs.getString("boardName");
					

					Board board = new Board(id, boardName);
					boards.add(board);
				}
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
		return boards;
	}
	
	
	
}
