package com.epam.gymcrm.repository;

import com.epam.gymcrm.entity.Training;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface TrainingRepository extends JpaRepository<Training, Long> {

}
