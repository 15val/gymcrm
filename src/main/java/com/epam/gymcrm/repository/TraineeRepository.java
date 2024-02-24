package com.epam.gymcrm.repository;

import com.epam.gymcrm.entity.Trainee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface TraineeRepository extends JpaRepository<Trainee, Long> {

}
