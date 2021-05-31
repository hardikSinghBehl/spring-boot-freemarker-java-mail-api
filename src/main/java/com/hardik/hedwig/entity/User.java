package com.hardik.hedwig.entity;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "users")
public class User implements Serializable {

	private static final long serialVersionUID = -1240069325924945940L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, unique = true, updatable = false)
	private Integer id;

	@Column(name = "email_id", nullable = false, unique = true, length = 50)
	private String emailId;

	@Column(name = "name", nullable = false, length = 50)
	private String fullName;

	@Column(name = "created_at", nullable = false)
	private LocalDate createdAt;

}
