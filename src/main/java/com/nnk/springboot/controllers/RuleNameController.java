package com.nnk.springboot.controllers;

import com.nnk.springboot.model.RuleNameModel;
import com.nnk.springboot.service.RuleNameService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class RuleNameController {
    @Autowired
    RuleNameService ruleNameService;
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(RuleNameController.class.getName());

    @ModelAttribute("remoteUser")
    public Object remoteUser(final HttpServletRequest request) {
        logger.info("Request.getRemoteUser() is:" + request.getRemoteUser());
        return request.getRemoteUser();
    }

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
        RuleNameModel ruleName = ruleNameService.getById(id).orElseThrow(() -> new IllegalArgumentException("Invalid id:" + id));
        logger.info("/ruleName/update/{id}" + ruleName.toString());
        model.addAttribute("ruleName", ruleName);
        return "ruleName/update";
    }

    @PostMapping("/ruleName/update/{id}")
    public String updateRuleName(@PathVariable("id") Integer id, @Valid RuleNameModel ruleName,
                             BindingResult result, Model model) {
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
        RuleNameModel ruleName = ruleNameService.getById(id).orElseThrow(()-> new IllegalArgumentException("Invalid id:" + id));
        logger.info("/ruleName/delete/{id}" +  ruleName.toString());
        ruleNameService.deleteRuleName(ruleName);
        model.addAttribute("ruleName", ruleNameService.getAllRuleNames());
        return "redirect:/ruleName/list";
    }


}
