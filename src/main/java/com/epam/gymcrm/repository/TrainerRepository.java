package com.epam.gymcrm.repository;

import com.epam.gymcrm.entity.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface TrainerRepository extends JpaRepository<Trainer, Long> {

}