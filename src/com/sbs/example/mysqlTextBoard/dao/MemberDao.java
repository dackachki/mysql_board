package com.sbs.example.mysqlTextBoard.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.sbs.example.mysqlTextBoard.Container;
import com.sbs.example.mysqlTextBoard.dto.Member;

public class MemberDao {
	List<Member> members;

	public List<Member> getMembers() {
		members = new ArrayList<>();
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

			String sql = "SELECT * FROM members;";

			try {
				PreparedStatement pstmt = con.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery();

				while (rs.next()) {

					int id = rs.getInt("id");
					String memberName = rs.getString("memberName");
					String memberId = rs.getString("memberId");
					String memberPw = rs.getString("memberPw");
					

					Member member = new Member(id,memberName,memberId,memberPw);

					members.add(member);
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

		return members;
	}

	public void memberJoin(String name, String memberId, String memberPw) {
		
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

				String sql = "insert into members set memberName = '" + name +"', memberId = '"+ memberId +"', memberPw = '" +memberPw + "' ;";
				
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
			System.out.println("회원이 추가되었습니다.");
			
		}

	public Member getMemberById(String memberId) {
			for(Member member : members) {
				if(member.memberId.equals(memberId)) {
					return member;
				}
				
			}
			return null;
	}

	public Member getMemberByLoginedId() {
		for(Member member : members) {
			if(member.memberIndex == Container.session.loginedId) {
				return member;
			}
		}
		return null;
	}
	



	}


