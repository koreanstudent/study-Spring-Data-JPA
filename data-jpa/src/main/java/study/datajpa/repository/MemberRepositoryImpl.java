package study.datajpa.repository;

import java.util.List;

import javax.persistence.EntityManager;

import lombok.RequiredArgsConstructor;
import study.datajpa.entity.Member;

@RequiredArgsConstructor
// MemberRepository이름을 맞춰야하고 Impl 넣어야한다 jpa사용하기위해 
public class MemberRepositoryImpl implements MemberRepositoryCustom {
	
	private final EntityManager em;

	@Override
	public List<Member> findMemberCustom() {
		return em.createQuery("select m from Member m")
				.getResultList();
	}
	
	
}
