package study.datajpa.repository;


import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import study.datajpa.entity.Member;

@SpringBootTest
@Transactional  // 테스트 에서는 기본적으로 롤백 시킴
@Rollback(false) // console 확인을 위해 false 롤백 안됨. 
class MemberJpaRepositoryTest {

	@Autowired MemberJpaRepository memberJpaRepository;
	@PersistenceContext
	EntityManager em;
	@Test
	public void testMember() {
		Member member = new Member("meberA");
		Member savaMember = memberJpaRepository.save(member);
		
		Member findMember = memberJpaRepository.find(savaMember.getId());
		
		Assertions.assertThat(findMember.getId()).isEqualTo(member.getId());
		Assertions.assertThat(findMember.getUsername()).isEqualTo(member.getUsername());
		Assertions.assertThat(findMember).isEqualTo(member);
		
	}
	
	@Test
	public void basicCRUD() {
		Member member1 = new Member("member1");
		Member member2 = new Member("member2");
		memberJpaRepository.save(member1);
		memberJpaRepository.save(member2);
		
		// 단건 조회 검증
		Member findMember1 = memberJpaRepository.findById(member1.getId()).get();
		Member findMember2 = memberJpaRepository.findById(member1.getId()).get();
		assertThat(findMember1).isEqualTo(member1);
		assertThat(findMember2).isEqualTo(member2);
		
		//리스트 조회 검증
		List<Member> all = memberJpaRepository.findAll();
		assertThat(all.size()).isEqualTo(2);
		
		//카운트 검증
		long count = memberJpaRepository.count();
		assertThat(count).isEqualTo(2);
		
		// 삭제 검증
		memberJpaRepository.delete(member1);
		memberJpaRepository.delete(member2);
		
		long deletedCount = memberJpaRepository.count();
		assertThat(deletedCount).isEqualTo(2);
		
	}
	@Test
	public void findByUsernameAndAgeGreaterThen() {
		Member m1 = new Member("AAA", 10);
		Member m2 = new Member("AAA", 20);
		
		memberJpaRepository.save(m1);
		memberJpaRepository.save(m2);
		
		List<Member> result =memberJpaRepository.findByUsernameAndAgeGreaterThen("AAA", 15);
		
		assertThat(result.get(0).getUsername()).isEqualTo("AAA");
		assertThat(result.get(0).getAge()).isEqualTo(20);
		assertThat(result.size()).isEqualTo(1);
		
	}
	
	@Test
	public void paging() {
		memberJpaRepository.save(new Member("member1",10));
		memberJpaRepository.save(new Member("member2",10));
		memberJpaRepository.save(new Member("member3",10));
		memberJpaRepository.save(new Member("member4",10));
		memberJpaRepository.save(new Member("member5",10));
		
		int age = 10;
		int offset = 0;
		int limit = 3;
		
		List<Member> members = memberJpaRepository.findByPage(age, offset, limit);
		long totalCount = memberJpaRepository.totalCount(age);
		
		assertThat(members.size()).isEqualTo(3);
		assertThat(totalCount).isEqualTo(5);
	}
	
	@Test
	public void bulkUpdate() {
		memberJpaRepository.save(new Member("member1", 10));
		memberJpaRepository.save(new Member("member2", 12));
		memberJpaRepository.save(new Member("member3", 14));
		memberJpaRepository.save(new Member("member4", 17));
		memberJpaRepository.save(new Member("member5", 7));
		
		int resultCount = memberJpaRepository.bulkAgePlus(20);
		// 벌크연산은 영속성 컨텍스트를 거치지 않고 db에 들어가 초기화해주어야한다.
		em.flush(); //혹시나 변경되지 않은 남아있는내용들을 db에 반영
		em.clear();
		
		assertThat(resultCount).isEqualTo(3);
	}

}
