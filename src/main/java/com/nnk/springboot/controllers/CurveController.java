package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.service.CurvePointService;
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
public class CurveController {
    @Autowired
    CurvePointService curvePointService;

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(CurveController.class.getName());

    @RequestMapping("/curvePoint/list")
    public String home(Model model)
    {
        model.addAttribute("curvepoint", curvePointService.getAllCurvePoints());
        return "curvePoint/list";
    }

    @GetMapping("/curvePoint/add")
    public String addBidForm(CurvePoint bid) {
        return "curvePoint/add";
    }

    @PostMapping("/curvePoint/validate")
    public String validate(@Valid CurvePoint curvePoint, BindingResult result, Model model) {
        // TODO: check data valid and save to db, after saving return Curve list
        if (curvePointService.getCurvePoint(curvePoint.getId()).isPresent() && result.hasFieldErrors()){
            logger.info("validation problem occurred");
            return "curvePoint/add";
        }
        curvePointService.saveCurvePoint(curvePoint);
        logger.info("recording successfully completed");
        return "curvePoint/add";
    }

    @GetMapping("/curvePoint/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        // TODO: get CurvePoint by Id and to model then show to the form
        CurvePoint curvePoint = curvePointService.getCurvePoint(id).orElseThrow(()-> new IllegalArgumentException("Invalid id:" + id));
        logger.info(curvePoint.toString());
        model.addAttribute("curvepoint", curvePoint);
        return "curvePoint/update";
    }

    @PostMapping("/curvePoint/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid CurvePoint curvePoint,
                             BindingResult result, Model model) {
        // TODO: check required fields, if valid call service to update Curve and return Curve list
        if (!curvePointService.getCurvePoint(id).isPresent() && result.hasFieldErrors()){
            logger.info("Invalid id:" + id + "or a validation problem occurred");
            return "curvePoint/list";
        }
        curvePointService.saveCurvePoint(curvePoint);
        logger.info("update successful");
        model.addAttribute("curvepoint", curvePointService.getAllCurvePoints());
        return "redirect:/curvePoint/list";
    }

    @GetMapping("/curvePoint/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
        // TODO: Find Curve by Id and delete the Curve, return to Curve list
        CurvePoint curvePoint = curvePointService.getCurvePoint(id).orElseThrow(()-> new IllegalArgumentException("Invalid curvepoint id:" + id));
        logger.info(curvePoint.toString());
        curvePointService.deleteCurvePoint(curvePoint);
        model.addAttribute("curvepoint", curvePointService.getAllCurvePoints());
        return "redirect:/curvePoint/list";
    }
}
