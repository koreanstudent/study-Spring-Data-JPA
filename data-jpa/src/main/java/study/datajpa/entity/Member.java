package study.datajpa.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"id", "username", "age"})
public class Member {

	@Id @GeneratedValue
	@Column(name = "member_id")
	private Long id;
	private String username;
	private int age;
	
	// 기본 지연로딩으로 세팅 Because 사용할때 쿼리 조회 속도관련..
	@ManyToOne(fetch =FetchType.LAZY)
	@JoinColumn(name="team_id")
	private Team team;
	
//	@NoArgsConstructor(access = AccessLevel.PROTECTED)
//	protected Member() {
//		// TODO Auto-generated constructor stub
//	}
	
	public Member(String username) {
		this.username = username;
	}
	
	public void changeTeam(Team team) {
		this.team = team;
		team.getMembers().add(this);
	}

	public Member(String username, int age, Team team) {

		this.username = username;
		this.age = age;
		if(team != null) {
			changeTeam(team);
		}
		
	}
}
