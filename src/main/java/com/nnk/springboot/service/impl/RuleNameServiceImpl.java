package com.nnk.springboot.service.impl;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
import com.nnk.springboot.service.RuleNameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RuleNameServiceImpl implements RuleNameService {
    @Autowired
    RuleNameRepository ruleNameRepository;

    public List<RuleName> getAllRuleNames(){
        return ruleNameRepository.findAll();
    }

    public void  saveRuleName(RuleName ruleName){
        ruleNameRepository.save(ruleName);
    }

    public void  deleteRuleName(Integer id){
        ruleNameRepository.deleteById(id);
    }

}
