package com.nnk.springboot.controlleurtest;

import com.nnk.springboot.AbstractConfigurationTest;
import com.nnk.springboot.controllers.RuleNameController;
import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.domain.RuleName;;
import com.nnk.springboot.service.RuleNameService;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class RuleNameControllerTests extends AbstractConfigurationTest {
    @InjectMocks
    private RuleNameController ruleNameController;

    @Mock
    private RuleNameService ruleNameService;

    @Mock
    private Model model;

    @Test
    public void homeTest() {
        RuleName ruleName = new RuleName();
        ruleName.setName("nameTest");
        ruleName.setDescription("DescritpionTest");
        ruleName.setJson("JsonTest");
        ruleName.setTemplate("TemplateTest");
        ruleName.setSqlStr("SqlStrTest");
        ruleName.setSqlPart("SqlPartTest");

        List<RuleName> ruleNames = new ArrayList<>();
        ruleNames.add(ruleName);

        when(ruleNameService.getAllRuleNames()).thenReturn(ruleNames);
        String viewName = ruleNameController.home(model);

        assertEquals("ruleName/list", viewName);
        verify(model).addAttribute("ruleName", ruleNames);
    }

    @Test
    public void addRuleFormTest() {
        String viewName = ruleNameController.addRuleForm(model);

        assertEquals("ruleName/add", viewName);

        verify(model).addAttribute(eq("ruleName"), Mockito.any(RuleName.class));
    }
}
