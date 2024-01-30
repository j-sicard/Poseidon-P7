package com.nnk.springboot.controllers;

import com.nnk.springboot.model.RatingModel;
import com.nnk.springboot.service.RatingService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class RatingController {
    @Autowired
    RatingService ratingService;

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(RatingController.class.getName());

    @ModelAttribute("remoteUser")
    public Object remoteUser(final HttpServletRequest request) {
        logger.info("Request.getRemoteUser() is:" + request.getRemoteUser());
        return request.getRemoteUser();
    }

    @RequestMapping("/rating/list")
    public String home(Model model)
    {
        model.addAttribute("rating", ratingService.getAllRatings());
        logger.info("rating/list : OK");
        return "rating/list";
    }

    @GetMapping("/rating/add")
    public String addRatingForm(Model model) {
        model.addAttribute("rating", new RatingModel());
        logger.info("GetMapping(\"/rating/add\") successfully");
        return "rating/add";
    }

    @PostMapping("/rating/validate")
    public String validate(@Valid RatingModel rating, BindingResult result, Model model) {
        if (!result.hasErrors()){
            ratingService.saveRating(rating);
            logger.info("recording successfully completed");
            return "redirect:/rating/list";
        }
        logger.info("validation problem occurred /rating/validate");
        return "rating/add";
    }

    @GetMapping("/rating/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        RatingModel rating = ratingService.getbyid(id).orElseThrow(()-> new IllegalArgumentException("Invalid id:" + id));
        logger.info("/rating/update/{id}" +rating.toString());
        model.addAttribute("rating", rating);
        return "rating/update";
    }

    @PostMapping("/rating/update/{id}")
    public String updateRating(@PathVariable("id") Integer id, @Valid RatingModel rating,
                             BindingResult result, Model model) {
        if (!ratingService.getbyid(id).isPresent()){
            logger.info("Invalid id:" + id);
            return "redirect:/rating/list";
        }
        if (!result.hasErrors()){
            ratingService.saveRating(rating);
            logger.info("update successful");
            return "redirect:/rating/list";
        }
        logger.info("validation problem occurred ( /rating/update/{id} )");
        return "/rating/add";
    }

    @GetMapping("/rating/delete/{id}")
    public String deleteRating(@PathVariable("id") Integer id, Model model) {
        RatingModel rating = ratingService.getbyid(id).orElseThrow(()-> new  IllegalArgumentException("Invalid rating id:" + id));
        logger.info("/rating/delete/{id}" + rating.toString());
        ratingService.deleteRating(rating);
        model.addAttribute("rating", ratingService.getAllRatings());
        return "redirect:/rating/list";
    }
}
