package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
import com.nnk.springboot.service.RuleNameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
public class RuleNameController {
    @Autowired
    RuleNameService ruleNameService;
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(RuleNameController.class.getName());

    @RequestMapping("/ruleName/list")
    public String home(Model model)
    {
        model.addAttribute("rulename", ruleNameService.getAllRuleNames());
        return "ruleName/list";
    }

    @GetMapping("/ruleName/add")
    public String addRuleForm(RuleName bid) {
        return "ruleName/add";
    }

    @PostMapping("/ruleName/validate")
    public String validate(@Valid RuleName ruleName, BindingResult result, Model model) {
        // TODO: check data valid and save to db, after saving return RuleName list
        if (ruleNameService.getById(ruleName.getId()).isPresent() && result.hasFieldErrors()){
            logger.info("validation problem occurred");
            return "ruleName/add";
        }
        ruleNameService.saveRuleName(ruleName);
        logger.info("recording successfully completed");
        model.addAttribute("rating", ruleNameService.getAllRuleNames());
        return "ruleName/add";
    }

    @GetMapping("/ruleName/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        // TODO: get RuleName by Id and to model then show to the form
        RuleName ruleName = ruleNameService.getById(id).orElseThrow(() -> new IllegalArgumentException("Invalid id:" + id));
        logger.info(ruleName.toString());
        model.addAttribute("rulename", ruleNameService.getById(id));
        return "ruleName/update";
    }

    @PostMapping("/ruleName/update/{id}")
    public String updateRuleName(@PathVariable("id") Integer id, @Valid RuleName ruleName,
                             BindingResult result, Model model) {
        // TODO: check required fields, if valid call service to update RuleName and return RuleName list
        if (!ruleNameService.getById(ruleName.getId()).isPresent() && result.hasFieldErrors()){
            logger.info("Invalid id:" + id + "or a validation problem occurred");
            return "ruleName/list";
        }
        ruleNameService.saveRuleName(ruleName);
        logger.info("update successful");
        model.addAttribute("rulename", ruleNameService.getAllRuleNames());
        return "redirect:/ruleName/list";
    }

    @GetMapping("/ruleName/delete/{id}")
    public String deleteRuleName(@PathVariable("id") Integer id, Model model) {
        // TODO: Find RuleName by Id and delete the RuleName, return to Rule list
        RuleName ruleName = ruleNameService.getById(id).orElseThrow(()-> new IllegalArgumentException("Invalid id:" + id));
        logger.info(ruleName.toString());
        ruleNameService.deleteRuleName(ruleName);
        model.addAttribute("rulename", ruleNameService.getAllRuleNames());
        return "redirect:/ruleName/list";
    }
}
