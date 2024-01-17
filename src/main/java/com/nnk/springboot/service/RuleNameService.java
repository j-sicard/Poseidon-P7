package com.nnk.springboot.service;

import com.nnk.springboot.domain.RuleName;

import java.util.List;

public interface RuleNameService {
    public List<RuleName> getAllRuleNames();

    public void  saveRuleName(RuleName ruleName);

    public  void  deleteRuleName(Integer id);
}
