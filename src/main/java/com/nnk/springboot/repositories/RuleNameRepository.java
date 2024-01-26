package com.nnk.springboot.repositories;

import com.nnk.springboot.model.RuleNameModel;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RuleNameRepository extends JpaRepository<RuleNameModel, Integer> {
}
