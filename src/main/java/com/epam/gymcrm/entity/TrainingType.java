package com.epam.gymcrm.entity;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "training_type", schema = "gymcrm_shema", catalog = "gymcrm")
public class TrainingType {

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	@Column(name = "id")
	private Long id;

	@Column(name = "training_type_name")
	private String trainingTypeName;

	@OneToMany(mappedBy="trainingType1")
	private Set<Training> trainingSet;

	@OneToMany(mappedBy="trainingType2")
	private Set<Trainer> trainerSet;
}
