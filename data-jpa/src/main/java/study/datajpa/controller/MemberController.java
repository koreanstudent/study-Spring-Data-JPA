package study.datajpa.controller;

import javax.annotation.PostConstruct;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import study.datajpa.entity.Member;
import study.datajpa.repository.MemberRepository;

@RestController
@RequiredArgsConstructor
public class MemberController {

	private final MemberRepository memberRepository;
	
	@GetMapping("/members/{id}")
	public String findMember(@PathVariable("id") Long id){
		Member member = memberRepository.findById(id).get();
		return member.getUsername();
		
	}
	
	@GetMapping("/members2/{id}")
	public String findMember2(@PathVariable("id") Member member) {
		return member.getUsername();
	}
	
	@PostConstruct // 의존성 주입이 이루어진 후 초기화를 수행하는 메서드이다. service를 수행하기 전에 발생한다.
	public void init() {
		memberRepository.save(new Member("userA"));
	}
}
