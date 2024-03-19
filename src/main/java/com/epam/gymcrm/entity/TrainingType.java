package com.epam.gymcrm.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "training_type", schema = "gymcrm_shema", catalog = "gymcrm")
public class TrainingType {

	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "training_type_seq")
	@SequenceGenerator(name = "training_type_seq", sequenceName = "training_type_seq", allocationSize = 1)
	@Id
	private Long id;

	@Column(name = "training_type_name")
	private String trainingTypeName;

	@OneToMany(mappedBy="trainingType1")
	@Nullable
	private List<Training> trainingList;

	@OneToMany(mappedBy="trainingType2")
	@Nullable
	private List<Trainer> trainerList;

}
