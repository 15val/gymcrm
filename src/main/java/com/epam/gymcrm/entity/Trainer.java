package com.epam.gymcrm.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "trainer", schema = "gymcrm_shema", catalog = "gymcrm")
public class Trainer {

	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "trainer_seq")
	@SequenceGenerator(name = "trainer_seq", sequenceName = "trainer_seq", allocationSize = 1)
	@Id
	private Long id;

	@ManyToOne
	@JoinColumn(name="specialization", referencedColumnName = "id")
	private TrainingType trainingType2;

	@OneToOne(cascade = {CascadeType.MERGE, CascadeType.REMOVE})
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	private User user1;

	@ManyToMany(mappedBy = "trainerSet")
	private Set<Trainee> traineeSet = new HashSet<>();

	@OneToMany(mappedBy="trainer1")
	private Set<Training> trainingSet;
}
