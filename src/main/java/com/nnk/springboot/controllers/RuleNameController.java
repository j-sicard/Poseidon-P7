package com.nnk.springboot.controllers;

import com.nnk.springboot.model.RuleNameModel;
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
        model.addAttribute("ruleName", ruleNameService.getAllRuleNames());
        logger.info("ruleName/list : OK");
        return "ruleName/list";
    }

    @GetMapping("/ruleName/add")
    public String addRuleForm(Model model) {
        model.addAttribute("ruleName", new RuleNameModel());
        logger.info("GetMapping(\"/ruleName/add\") successfully");
        return "ruleName/add";
    }

    @PostMapping("/ruleName/validate")
    public String validate(@Valid RuleNameModel ruleName, BindingResult result, Model model) {
        // TODO: check data valid and save to db, after saving return RuleName list
        if (!result.hasErrors()){
            ruleNameService.saveRuleName(ruleName);
            logger.info("recording successfully completed");
            return "redirect:/ruleName/list";
        }
        logger.info("validation problem occurred/ruleName/validate");
        return "ruleName/add";
    }

    @GetMapping("/ruleName/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        // TODO: get RuleName by Id and to model then show to the form
        RuleNameModel ruleName = ruleNameService.getById(id).orElseThrow(() -> new IllegalArgumentException("Invalid id:" + id));
        logger.info("/ruleName/update/{id}" + ruleName.toString());
        model.addAttribute("ruleName", ruleName);
        return "ruleName/update";
    }

    @PostMapping("/ruleName/update/{id}")
    public String updateRuleName(@PathVariable("id") Integer id, @Valid RuleNameModel ruleName,
                             BindingResult result, Model model) {
        // TODO: check required fields, if valid call service to update RuleName and return RuleName list
        if (!ruleNameService.getById(ruleName.getId()).isPresent()){
            logger.info("Invalid id:" + id);
            return "redirect:/ruleName/list";
        }
        if (!result.hasErrors()){
            ruleNameService.saveRuleName(ruleName);
            logger.info("update successful");
            return "redirect:/ruleName/list";
        }
        logger.info("validation problem occurred ( /ruleName/update/{id} )");
        return "ruleName/add";
    }

    @GetMapping("/ruleName/delete/{id}")
    public String deleteRuleName(@PathVariable("id") Integer id, Model model) {
        // TODO: Find RuleName by Id and delete the RuleName, return to Rule list
        RuleNameModel ruleName = ruleNameService.getById(id).orElseThrow(()-> new IllegalArgumentException("Invalid id:" + id));
        logger.info("/ruleName/delete/{id}" +  ruleName.toString());
        ruleNameService.deleteRuleName(ruleName);
        model.addAttribute("ruleName", ruleNameService.getAllRuleNames());
        return "redirect:/ruleName/list";
    }
}
