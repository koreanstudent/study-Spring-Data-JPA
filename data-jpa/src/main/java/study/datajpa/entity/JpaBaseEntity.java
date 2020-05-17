package study.datajpa.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import lombok.Getter;

@MappedSuperclass  // 속성들을 상속관계 이용
@Getter
public class JpaBaseEntity {

	@Column(updatable = false)  // 생성일은 실수로 넣더라도 없데이트 되지 않게 막음
	private LocalDateTime createdDate;
	
	private LocalDateTime updatedDate;
	
	@PrePersist // persist 전에 이벤트 발생
	public void prePersist() {
		LocalDateTime now = LocalDateTime.now();
		createdDate = now;
		updatedDate = now;
	}
	
	@PreUpdate
	public void preUpdate() {
		updatedDate = LocalDateTime.now();
	}
	
}
