package com.epam.gymcrm.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "user", schema = "gymcrm_shema", catalog = "gymcrm")
public class User {


	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
	@SequenceGenerator(name = "user_seq", sequenceName = "user_seq", allocationSize = 1)
	@Id
	private Long id;

	@Column(name = "first_name")
	private String 	firstName;

	@Column(name = "last_name")
	private String  lastName;

	@Column(name = "username")
	private String  username;

	@Column(name = "password")
	private String  password;

	@Column(name = "is_active")
	private Boolean isActive;

	@OneToOne(mappedBy = "user")
	private Trainee trainee;

	@OneToOne(mappedBy = "user1")
	private Trainer trainer;
}
