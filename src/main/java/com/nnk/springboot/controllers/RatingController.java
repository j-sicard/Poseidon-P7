package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import com.nnk.springboot.service.RatingService;
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
public class RatingController {
    @Autowired
    RatingService ratingService;

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(RatingController.class.getName());

    @RequestMapping("/rating/list")
    public String home(Model model)
    {
        model.addAttribute("rating", ratingService.getAllRatings());
        return "rating/list";
    }

    @GetMapping("/rating/add")
    public String addRatingForm(Rating rating) {
        return "rating/add";
    }

    @PostMapping("/rating/validate")
    public String validate(@Valid Rating rating, BindingResult result, Model model) {
        // TODO: check data valid and save to db, after saving return Rating list
        if (ratingService.getbyid(rating.getId()).isPresent() && result.hasFieldErrors()){
            logger.info("validation problem occurred");
            return "rating/add";
        }
        ratingService.saveRating(rating);
        logger.info("recording successfully completed");
        return "rating/add";
    }

    @GetMapping("/rating/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        // TODO: get Rating by Id and to model then show to the form
        Rating rating = ratingService.getbyid(id).orElseThrow(()-> new IllegalArgumentException("Invalid id:" + id));
        logger.info(rating.toString());
        model.addAttribute("rating", ratingService.getbyid(id));
        return "rating/update";
    }

    @PostMapping("/rating/update/{id}")
    public String updateRating(@PathVariable("id") Integer id, @Valid Rating rating,
                             BindingResult result, Model model) {
        // TODO: check required fields, if valid call service to update Rating and return Rating list
        if (!ratingService.getbyid(id).isPresent() && result.hasFieldErrors()){
            logger.info("Invalid id:" + id + "or a validation problem occurred");
            return "rating/list";
        }
        ratingService.saveRating(rating);
        logger.info("update successful");
        model.addAttribute("rating", ratingService.getAllRatings());
        return "redirect:/rating/list";
    }

    @GetMapping("/rating/delete/{id}")
    public String deleteRating(@PathVariable("id") Integer id, Model model) {
        // TODO: Find Rating by Id and delete the Rating, return to Rating list
        Rating rating = ratingService.getbyid(id).orElseThrow(()-> new  IllegalArgumentException("Invalid rating id:" + id));
        logger.info(rating.toString());
        ratingService.deleteRating(rating);
        model.addAttribute("rating", ratingService.getAllRatings());
        return "redirect:/rating/list";
    }
}
