package study.datajpa.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import study.datajpa.dto.MemberDto;
import study.datajpa.entity.Member;

public interface MemberRepository extends JpaRepository<Member,Long>{

	// where username =? and age > ?   
	List<Member> findByUsernameAndAgeGreaterThan(String username, int age); //인터페이스만 구현하여도 파라미터 명으로 쿼리생성
	
	// 쿼리가 복잡해지면 이렇게 사용 권장 정적쿼리.
	@Query("select m from Member m where m.username = :username and m.age = :age")
	List<Member> findUser(@Param("username") String username, @Param("age") int age);
	
	@Query("select m.username from Member m")
	List<String> findUsernameList();
	
	// dto 조회할때. new - jpql이 제공하는 문법 
	@Query("select new study.datajpa.dto.MemerDto(m.id, m.username, t.name) from Member m join m.team")
	List<MemberDto> findMemberDto();
	
	Page<Member> findByAge(int age, Pageable pageable);
	
	@Modifying(clearAutomatically = true) // 변경시 넣어주어야함. executeUpdate() 역활 clearAutomatically = true 이 쿼리가 나가고 난 다음에 영속성컨텍스트 초기화
	@Query("update Member m set m.age = m.age +1 where m.age >= :age")
	int bulkAgePlus(@Param("age") int age);
}
