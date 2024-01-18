package com.nnk.springboot.service;

import com.nnk.springboot.domain.RuleName;

import java.util.List;
import java.util.Optional;

public interface RuleNameService {
    public List<RuleName> getAllRuleNames();

    public void  saveRuleName(RuleName ruleName);

    public void  deleteRuleName(RuleName ruleName);

    public Optional<RuleName> getById(Integer id);
}
