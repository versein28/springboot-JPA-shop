package com.hwsin.shop.model;

import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Board {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(nullable = false,length = 100)
	private String title;
	
	@Lob//대용량 데이터
	private String content; //섬머노트 라이브러리 <html>태그가 섞여서 디자인이 됨

	private int count;//조회수
	
	@ManyToOne(fetch = FetchType.EAGER) // Many = Board, User=One
	@JoinColumn(name="userId")
	private Users user;//DB는 오브제그를 저장할 수 없다. FK,자바는 오브젝트를 저장할 수 있다.
	
	@OneToMany(mappedBy = "board", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE) // mappedBy 연관관게의 주인이 아니다 (난 FK가 아니에요) DB에 컬럼을 만들지 마세요
	// @JoinColumn(name="reply") 필요가없다
	@JsonIgnoreProperties({"board"})
	@OrderBy("id desc")
	private List<Reply> reply;
	
	@CreationTimestamp
	private Timestamp createDate;
	
	
}
