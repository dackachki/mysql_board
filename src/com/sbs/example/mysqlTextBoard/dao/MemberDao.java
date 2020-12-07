package com.sbs.example.mysqlTextBoard.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.sbs.example.mysqlTextBoard.Container;
import com.sbs.example.mysqlTextBoard.dto.Member;
import com.sbs.example.mysqlutil.MysqlUtil;
import com.sbs.example.mysqlutil.SecSql;

public class MemberDao {
	
	List<Member> members;
	
	
	public List<Member> getMembers() {
		members = new ArrayList<>();
		List<Map<String, Object>> MembersMapList = MysqlUtil.selectRows(new SecSql().append("SELECT * FROM members"));

		for (Map<String, Object> MembersMap : MembersMapList) {
			Member member  = new Member();
			member.memberIndex = (int)MembersMap.get("id");
			member.memberName = (String) MembersMap.get("memberName");
			member.memberId = (String) MembersMap.get("memberId");
			member.memberPw = (String) MembersMap.get("memberPw");
			
			members.add(member);
		}
		return members;
	}

	public void memberJoin(String name, String memberId, String memberPw) {
		MysqlUtil.update(new SecSql().append("insert into members set memberName = ?, memberId = ?,memberPw = ?;",name,memberId,memberPw));

		

		System.out.printf("%s님 가입이 완료되었습니다.\n",name);

	}

	public Member getMemberById(String memberId) {
		for (Member member : members) {
			if (member.memberId.equals(memberId)) {
				return member;
			}

		}
		return null;
	}

	public Member getMemberByLoginedId() {
		for (Member member : members) {
			if (member.memberIndex == Container.session.loginedId) {
				return member;
			}
		}
		return null;
	}

	public String getMemberNameById(int memberId) {
		for (Member member : members) {
			if (member.memberIndex == memberId) {
				return member.memberId;
			}
		}
		return null;
	}


}
