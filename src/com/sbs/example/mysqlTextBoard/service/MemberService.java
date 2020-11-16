package com.sbs.example.mysqlTextBoard.service;

import java.util.List;

import com.sbs.example.mysqlTextBoard.Container;
import com.sbs.example.mysqlTextBoard.dao.MemberDao;
import com.sbs.example.mysqlTextBoard.dto.Member;

public class MemberService {
	MemberDao memberDao = Container.memberDao;

	public void memberJoin(String name, String memberId, String memberPw) {
		memberDao.memberJoin(name,memberId,memberPw);
	}

	public Member getMemberById(String memberId) {
		return memberDao.getMemberById(memberId);
	}

	

	public List<Member> getMembers() {
		return memberDao.getMembers();
	}

	public Member getMemberByLoginedId() {
		return memberDao.getMemberByLoginedId();
	}

}
