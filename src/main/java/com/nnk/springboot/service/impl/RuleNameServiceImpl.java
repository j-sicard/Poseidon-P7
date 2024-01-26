package com.nnk.springboot.service.impl;

import com.nnk.springboot.model.RuleNameModel;
import com.nnk.springboot.repositories.RuleNameRepository;
import com.nnk.springboot.service.RuleNameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RuleNameServiceImpl implements RuleNameService {
    @Autowired
    RuleNameRepository ruleNameRepository;

    public List<RuleNameModel> getAllRuleNames(){
        return ruleNameRepository.findAll();
    }

    public void  saveRuleName(RuleNameModel ruleName){
        ruleNameRepository.save(ruleName);
    }

    public void  deleteRuleName(RuleNameModel ruleName){
        ruleNameRepository.delete(ruleName);
    }

    public Optional<RuleNameModel>getById(Integer id){
        return ruleNameRepository.findById(id);
    }

}
