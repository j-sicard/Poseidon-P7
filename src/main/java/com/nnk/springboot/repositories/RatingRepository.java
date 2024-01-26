package com.nnk.springboot.repositories;

import com.nnk.springboot.model.RatingModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RatingRepository extends JpaRepository<RatingModel, Integer> {


}
