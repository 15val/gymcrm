package com.epam.gymcrm.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "trainee", schema = "gymcrm_shema", catalog = "gymcrm")
public class Trainee {

	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "trainee_seq")
	@SequenceGenerator(name = "trainee_seq", sequenceName = "trainee_seq", allocationSize = 1)
	@Id
	private Long id;

	@Column(name = "date_of_birth")
	private Date dateOfBirth;

	@Column(name = "address")
	private String  address;

	@OneToOne(cascade = {CascadeType.MERGE, CascadeType.REMOVE})
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	private User user;

	@ManyToMany(cascade = { CascadeType.ALL })
	@JoinTable(
			name = "trainee_trainer",
			joinColumns = { @JoinColumn(name = "trainee_id") },
			inverseJoinColumns = { @JoinColumn(name = "trainer_id") }
	)
	private Set<Trainer> trainerSet;

	@OneToMany(mappedBy="trainee1", cascade = { CascadeType.ALL })
	private Set<Training> trainingSet;
}
