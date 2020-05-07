package study.datajpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import study.datajpa.entity.Member;

public interface MemberRepository extends JpaRepository<Member,Long>{

	// where username =? and age > ?
	List<Member> findByUsernameAndAgeGreaterThan(String username, int age); //인터페이스만 구현하여도 파라미터 명으로 쿼리생성
	
}
