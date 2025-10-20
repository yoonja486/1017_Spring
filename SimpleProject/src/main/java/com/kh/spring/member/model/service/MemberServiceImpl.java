package com.kh.spring.member.model.service;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.spring.member.model.dao.MemberRepository;
import com.kh.spring.member.model.dto.MemberDTO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MemberServiceImpl implements MemberService {

	@Autowired
	private SqlSessionTemplate sqlSession;
	
	@Autowired
	private MemberRepository memberRepository;
	
	@Override
	public MemberDTO login(MemberDTO member) {
		// log.info("나 불렀어?");
		
		return memberRepository.login(sqlSession, member);
	}

	@Override
	public void signUp(MemberDTO member) {
		// 꼼꼼하게 검증
		// 유효값 검증
		if(member == null) {
			return;
		}
		
		if(member.getUserId().length() > 20) {
			return;
		}
		
		if(member.getUserId() == null || member.getUserId().trim().isEmpty() || member.getUserPwd() == null || member.getUserPwd().trim().isEmpty()) {
			return;
		}
		
	}

	@Override
	public void update(MemberDTO member) {
	}

	@Override
	public void delete(MemberDTO member) {
	}

}
