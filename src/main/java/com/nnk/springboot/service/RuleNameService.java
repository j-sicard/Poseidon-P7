package com.nnk.springboot.service;

import com.nnk.springboot.model.RuleNameModel;

import java.util.List;
import java.util.Optional;

public interface RuleNameService {
    public List<RuleNameModel> getAllRuleNames();

    public void  saveRuleName(RuleNameModel ruleName);

    public void  deleteRuleName(RuleNameModel ruleName);

    public Optional<RuleNameModel> getById(Integer id);
}
