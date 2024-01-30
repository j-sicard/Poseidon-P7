package com.nnk.springboot.controllers;

import com.nnk.springboot.model.CurvePointModel;
import com.nnk.springboot.service.CurvePointService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class CurveController {
    @Autowired
    CurvePointService curvePointService;

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(CurveController.class.getName());

    @ModelAttribute("remoteUser")
    public Object remoteUser(final HttpServletRequest request) {
        logger.info("Request.getRemoteUser() is:" + request.getRemoteUser());
        return request.getRemoteUser();
    }

    @RequestMapping("/curvePoint/list")
    public String home(Model model)
    {
        model.addAttribute("curvePoint", curvePointService.getAllCurvePoints());
        logger.info("curvePoint/list : OK");
        return "curvePoint/list";
    }

    @GetMapping("/curvePoint/add")
    public String addCurveForm(Model model) {
        model.addAttribute("curvePoint", new CurvePointModel());
        logger.info("GetMapping(\"/curvePoint/add\") successfully");
        return "curvePoint/add";
    }

    @PostMapping("/curvePoint/validate")
    public String validate(@Valid CurvePointModel curvePoint, BindingResult result, Model model) {
        // TODO: check data valid and save to db, after saving return Curve list
        if (!result.hasErrors()){
            curvePointService.saveCurvePoint(curvePoint);
            logger.info("recording successfully completed");
            return "redirect:/curvePoint/list";
        }
        logger.info("validation problem occurred");
        return "curvePoint/add";
    }

    @GetMapping("/curvePoint/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        // TODO: get CurvePoint by Id and to model then show to the form
        CurvePointModel curvePoint = curvePointService.getCurvePoint(id).orElseThrow(()-> new IllegalArgumentException("Invalid id:" + id));
        logger.info(curvePoint.toString());
        model.addAttribute("curvePoint", curvePoint);
        return "curvePoint/update";
    }

    @PostMapping("/curvePoint/update/{id}")
    public String updateCurve(@PathVariable("id") Integer id, @Valid CurvePointModel curvePoint,
                              BindingResult result, Model model) {
        // TODO: check required fields, if valid call service to update Curve and return Curve list
        if (!curvePointService.getCurvePoint(id).isPresent()){
            logger.info("Invalid id:" + id);
            return "redirect:/curvePoint/list";
        }
        if (!result.hasErrors()){
            curvePointService.saveCurvePoint(curvePoint);
            logger.info("update successful");
            return "redirect:/curvePoint/list";
        }
        logger.info("validation problem occurred ( /bidList/curvePoint/{id} )");
        return "/curvePoint/add";
    }

    @GetMapping("/curvePoint/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
        // TODO: Find Curve by Id and delete the Curve, return to Curve list
        CurvePointModel curvePoint = curvePointService.getCurvePoint(id).orElseThrow(()-> new IllegalArgumentException("Invalid curvepoint id:" + id));
        logger.info(curvePoint.toString());
        curvePointService.deleteCurvePoint(curvePoint);
        model.addAttribute("curvePoint", curvePointService.getAllCurvePoints());
        return "redirect:/curvePoint/list";
    }


}
