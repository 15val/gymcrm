package com.epam.gymcrm.entity;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	@Column(name = "id")
	private Integer id;

	@Basic
	@Column(name = "first_name")
	private String 	firstName;

	@Basic
	@Column(name = "last_name")
	private String  lastName;

	@Basic
	@Column(name = "username")
	private String  username;

	@Basic
	@Column(name = "password")
	private String  password;

	@Basic
	@Column(name = "is_active")
	private Boolean isActive;

	@OneToOne(mappedBy = "user")
	private Trainee trainee;

	@OneToOne(mappedBy = "user1")
	private Trainer trainer;
}
