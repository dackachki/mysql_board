package com.sbs.example.mysqlTextBoard.controller;

import java.util.List;
import java.util.Scanner;

import com.sbs.example.mysqlTextBoard.Container;
import com.sbs.example.mysqlTextBoard.dto.Member;
import com.sbs.example.mysqlTextBoard.service.MemberService;

public class MemberController {
	private static MemberService memberService;
	Scanner sc = new Scanner(System.in);

	public MemberController() {

		memberService = new MemberService();

	}

	public void doCommand(String cmd) {
		if (cmd.equals("member join")) {
			memberJoin();
		} else if (cmd.equals("member login")) {
			memberLogin();
		} else if (cmd.equals("member list")) {
			showMemberList();
		} else if (cmd.equals("member logout")) {
			memberLogout();
		}

	}

	private void memberLogout() {
		System.out.println("== 로그아웃 되었습니다. ==");
		Container.session.loginedId = 0;

	}

	private void memberLogin() {
		Member loginTryMember = null;
		System.out.println("== 회원 로그인 ==");

		boolean idPassOk = false;

		while (true) {
			int tryC = 0;
			int maxC = 3;
			System.out.printf("아이디 입력 : ");
			String memberId = sc.nextLine().trim();
			if (tryC > maxC) {
				System.out.println("확인 후 다시 시도하세요.");
				return;
			}
			if(memberId.length() == 0 ) {
				System.out.println("아이디를 입력하세요.");
				tryC++;
				continue;
			}
			for (Member member : getMembers()) {
				if (member.memberId.equals(memberId)) {
					idPassOk = true;
					loginTryMember = member;
				}
			}
			
			if (idPassOk == false) {
				System.out.println("존재하지 않는 아이디입니다.");
				tryC++;
			}
			
			if (idPassOk) {
				break;

			}
			
			
						
		}

		System.out.printf("비밀번호 입력 : ");
		String passwd = sc.nextLine();
		if (loginTryMember.memberPw.equals(passwd) == false) {
			System.out.println("비밀번호가 일치하지 않습니다.");
			return;
		} else if (loginTryMember.memberPw.equals(passwd)) {
			System.out.printf("%s 님 환영합니다.\n", loginTryMember.memberName);
			Container.session.loginedId = loginTryMember.memberIndex;
		}
	}

	public void memberJoin() {
		System.out.println("== 회원 가입 ==");
		System.out.printf("이름 입력 : ");
		String name = sc.nextLine();
		System.out.printf("아이디 입력 : ");
		String memberId = sc.nextLine();
		System.out.printf("비밀번호 입력 : ");
		String memberPw = sc.nextLine();

		memberService.memberJoin(name, memberId, memberPw);

	}

	public void showMemberList() {
		System.out.println("== 게시물 리스트 ==");

		List<Member> members = memberService.getMembers();

		System.out.println("번호 / 이름 / 아이디");

		for (Member member : members) {
			System.out.printf("%d / %s / %s\n", member.memberIndex, member.memberName, member.memberId);
		}

	}

	private List<Member> getMembers() {
		return memberService.getMembers();
	}
}