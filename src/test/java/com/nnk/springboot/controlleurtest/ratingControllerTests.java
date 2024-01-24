package com.nnk.springboot.controlleurtest;

import com.nnk.springboot.AbstractConfigurationTest;
import com.nnk.springboot.controllers.RatingController;
import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.service.RatingService;
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

public class ratingControllerTests extends AbstractConfigurationTest {
    @InjectMocks
    private RatingController ratingController;

    @Mock
    private RatingService ratingService;

    @Mock
    private Model model;

    @Test
    public void homeTest() {
        Rating rating = new Rating();
        rating.setMoodysRating("MoodysRatingTest");
        rating.setSandPRating("SandPRatingTest");
        rating.setFitchRating("FitchRatingTest");
        rating.setOrderNumber(10);

        List<Rating> ratings = new ArrayList<>();
        ratings.add(rating);

        when(ratingService.getAllRatings()).thenReturn(ratings);
        String viewName = ratingController.home(model);

        assertEquals("rating/list", viewName);
        verify(model).addAttribute("rating", ratings);
    }

    @Test
    public void addRatingFormTest() {
        String viewName = ratingController.addRatingForm(model);

        assertEquals("rating/add", viewName);

        verify(model).addAttribute(eq("rating"), Mockito.any(Rating.class));
    }
}