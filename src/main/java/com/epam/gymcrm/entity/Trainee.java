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
import org.jetbrains.annotations.Nullable;

import java.util.Date;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Table(name = "trainee", schema = "gymcrm_shema", catalog = "gymcrm")
public class Trainee {

	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "trainee_seq")
	@SequenceGenerator(name = "trainee_seq", sequenceName = "trainee_seq", allocationSize = 1)
	@Id
	private Long id;

	@Column(name = "date_of_birth")
	@Nullable
	private Date dateOfBirth;

	@Column(name = "address")
	@Nullable
	private String address;

	@OneToOne(cascade = {CascadeType.ALL})
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	private User user;

	@ManyToMany(cascade = {CascadeType.ALL})
	@Nullable
	@JoinTable(
			name = "trainee_trainer",
			schema = "gymcrm_shema", catalog = "gymcrm",
			joinColumns = {@JoinColumn(name = "trainee_id")},
			inverseJoinColumns = {@JoinColumn(name = "trainer_id")}
	)
	private List<Trainer> trainerList;

	@OneToMany(mappedBy = "trainee1", cascade = {CascadeType.ALL})
	@Nullable
	private List<Training> trainingList;

}