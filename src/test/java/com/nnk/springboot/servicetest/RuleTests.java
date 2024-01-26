package com.nnk.springboot.servicetest;

import com.nnk.springboot.AbstractConfigurationTest;
import com.nnk.springboot.model.RuleNameModel;
import com.nnk.springboot.repositories.RuleNameRepository;
import com.nnk.springboot.service.RuleNameService;
import org.junit.Assert;
import org.junit.Test;;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;


public class RuleTests extends AbstractConfigurationTest {

	@Autowired
	private RuleNameRepository ruleNameRepository;

	@Autowired
	RuleNameService ruleNameService;

	@Test
	public void ruleTest() {
		RuleNameModel rule = new RuleNameModel();
		rule.setName("Rule Name");
		rule.setDescription("Description");
		rule.setJson("Json");
		rule.setTemplate("Template");
		rule.setSqlStr("SQL");
		rule.setSqlPart("SQL Part");

		// Save
		ruleNameService.saveRuleName(rule);
		Assert.assertNotNull(rule.getId());
		Assert.assertTrue(rule.getName().equals("Rule Name"));

		// Update
		rule.setName("Rule Name Update");
		ruleNameService.saveRuleName(rule);
		Assert.assertTrue(rule.getName().equals("Rule Name Update"));

		// Find
		List<RuleNameModel> listResult = ruleNameService.getAllRuleNames();
		Assert.assertTrue(listResult.size() > 0);

		// FindById
		Assert.assertTrue(ruleNameService.getById(rule.getId()).isPresent());

		// Delete
		Integer id = rule.getId();
		ruleNameService.deleteRuleName(rule);
		Optional<RuleNameModel> ruleList = ruleNameRepository.findById(id);
		Assert.assertFalse(ruleList.isPresent());
	}
}
