package com.epam.gymcrm.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/*@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "trainee_trainer", schema = "gymcrm_shema", catalog = "gymcrm")
public class TraineeTrainer {

	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "trainee_trainer_seq")
	@SequenceGenerator(name = "trainee_trainer_seq", sequenceName = "trainee_trainer_seq", allocationSize = 1)
	@Id
	private Long id;

	@ManyToOne
	@JoinColumn(name = "trainee_id", referencedColumnName = "id")
	private Trainee trainee;

	@ManyToOne
	@JoinColumn(name = "trainer_id", referencedColumnName = "id")
	private Trainer trainer;

}*/