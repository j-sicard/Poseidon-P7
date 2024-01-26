package com.nnk.springboot.repositories;

import com.nnk.springboot.model.CurvePointModel;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CurvePointRepository extends JpaRepository<CurvePointModel, Integer> {

}
