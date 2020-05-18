package study.datajpa.entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.domain.Persistable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)  //jpa는 기본 생성자가 필요 
public class Item implements Persistable<String> {

	@Id 
	private String id;
	
	@CreatedDate
	private LocalDateTime createdDate;
	
	public Item(String id) {
		this.id = id;
	}

	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return id;
	}

	@Override
	public boolean isNew() {
		// TODO Auto-generated method stub
		return createdDate == null;
	}
}
