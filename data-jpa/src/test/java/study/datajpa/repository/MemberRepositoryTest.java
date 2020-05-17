package study.datajpa.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import study.datajpa.dto.MemberDto;
import study.datajpa.entity.Member;
import study.datajpa.entity.Team;

@SpringBootTest
@Transactional
@Rollback(false)
class MemberRepositoryTest {

	@Autowired MemberRepository memberRepository;
	@Autowired TeamRepository teamRepository;
	@PersistenceContext
	EntityManager em;

	
	@Test
	public void testMember() {
		Member member =new Member("memberA");
		Member saveMember = memberRepository.save(member);
		
		Member findMember = memberRepository.findById(saveMember.getId()).get();
		
		Assertions.assertThat(findMember.getId()).isEqualTo(member.getId());
		Assertions.assertThat(findMember.getUsername()).isEqualTo(member.getUsername());
		Assertions.assertThat(findMember).isEqualTo(member);
		
	}
	
	@Test
	public void findByUsernameAndAgeGreaterThan() {
		Member m1 = new Member("AAA", 10);
		Member m2 = new Member("AAA", 20);
		
		memberRepository.save(m1);
		memberRepository.save(m2);
		
		List<Member> result =memberRepository.findByUsernameAndAgeGreaterThan("AAA", 15);
		
		assertThat(result.get(0).getUsername()).isEqualTo("AAA");
		assertThat(result.get(0).getAge()).isEqualTo(20);
		assertThat(result.size()).isEqualTo(1);
		
	}
	
	@Test
	public void testQuery() {
		Member m1 = new Member("AAA", 10);
		Member m2 = new Member("AAA", 20);
		
		memberRepository.save(m1);
		memberRepository.save(m2);
		
		List<Member> result =memberRepository.findUser("AAA", 15);
		
		assertThat(result.get(0).getUsername()).isEqualTo("AAA");
		assertThat(result.get(0).getAge()).isEqualTo(20);
		assertThat(result.size()).isEqualTo(1);
		
	}
	
	@Test
	public void findMemberDto() {
		
		Team team = new Team("teamA");
		teamRepository.save(team);
		
		
		Member m1 = new Member("AAA", 10);
		m1.setTeam(team);
		memberRepository.save(m1);
		
		List<MemberDto> memberDto =memberRepository.findMemberDto();
		for(MemberDto dto : memberDto) {
			System.out.println("dto = " + dto);
		}

		
	}
	
	// 페이징 처리
	@Test
	public void paging() {
		memberRepository.save(new Member("member1",10));
		memberRepository.save(new Member("member2",10));
		memberRepository.save(new Member("member3",10));
		memberRepository.save(new Member("member4",10));
		
		int age = 10;
		PageRequest pageRequest = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC,"username"));
		
		
		Page<Member> page = memberRepository.findByAge(age, pageRequest);
		
		
		List<Member> content = page.getContent();
		long totalElements = page.getTotalElements(); // 총갯수
		
		assertThat(content.size()).isEqualTo(3);
		assertThat(page.getTotalElements()).isEqualTo(5);
		assertThat(page.getNumber()).isEqualTo(0);
		assertThat(page.getTotalPages()).isEqualTo(2);
		assertThat(page.isFirst()).isTrue();
		assertThat(page.hasNext()).isTrue();
		
		
	}
	
	@Test
	public void bulkUpdate() {
		memberRepository.save(new Member("member1", 10));
		memberRepository.save(new Member("member2", 12));
		memberRepository.save(new Member("member3", 14));
		memberRepository.save(new Member("member4", 17));
		memberRepository.save(new Member("member5", 7));
		
		int resultCount = memberRepository.bulkAgePlus(20);
		
		assertThat(resultCount).isEqualTo(3);
	}
	
	@Test
	public void findMemberLazy() {
		//given
		// member1 -> teamA
		// member2 -> teamB
		
		Team teamA = new Team("teamA");
		Team teamB = new Team("teamB");
		teamRepository.save(teamA);
		teamRepository.save(teamB);
		Member member1 = new Member("member1", 10, teamA);
		Member member2 = new Member("member2", 10, teamB);
		memberRepository.save(member1);
		memberRepository.save(member2);
		
		em.flush();
		em.clear();
		
		// when
		List<Member> members = memberRepository.findAll();
//		List<Member> members = memberRepository.findMemberFetchJoin();
		
		for(Member member : members) {
			System.out.println("member = " + member.getUsername());
			System.out.println("member = " + member.getTeam().getName());
		}
		
	}
	
	@Test
	public void queryHint() {
		//given
		
		Member member1 = new Member("member1", 10);
		memberRepository.save(member1);
		
		em.flush();
		em.clear();
		
		// when
		Member members = memberRepository.findReadOnlyByUsername("member1");
		members.setUsername("member2");
		em.flush();
		
	}
	
	@Test
	public void queryLock() {
		//given
		
		Member member1 = new Member("member1", 10);
		memberRepository.save(member1);
		
		em.flush();
		em.clear();
		
		// when
		List<Member> members = memberRepository.findLockByUsername("member1");
		
	}
	
	// 사용자 정의 메서드
	@Test
	public void callCustom() {
		 List<Member> findMemberCustom = memberRepository.findMemberCustom();
	}
	


}
