package com.epam.gymcrm.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Training {

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	@Column(name = "id")
	private Long id;

	@ManyToOne
	@JoinColumn(name="trainee_id", referencedColumnName = "id")
	private Trainee trainee1;

	@ManyToOne
	@JoinColumn(name="trainer_id", referencedColumnName = "id")
	private Trainer trainer1;

	@Column(name = "training_name")
	private String  trainingName;

	@ManyToOne
	@JoinColumn(name="training_type_id", referencedColumnName = "id")
	private TrainingType trainingType1;

	@Column(name = "training_date")
	private Date trainingDate;

	@Column(name = "training_duration")
	private Integer trainingDuration;

}
