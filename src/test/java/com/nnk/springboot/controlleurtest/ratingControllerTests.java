package com.nnk.springboot.controlleurtest;

import com.nnk.springboot.AbstractConfigurationTest;
import com.nnk.springboot.controllers.RatingController;
import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.service.RatingService;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.never;

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

    @Test
    public void validateRatingWithNoErrorsTest() {
        // Objet Rating valide
        Rating rating = new Rating();
        rating.setOrderNumber(10);

        // Simuler un BindingResult sans erreurs
        BindingResult result = mock(BindingResult.class);
        when(result.hasErrors()).thenReturn(false);

        // Appeler la méthode validate
        String viewName = ratingController.validate(rating, result, model);

        verify(ratingService, times(1)).saveRating(rating);

        assertEquals("redirect:/rating/list", viewName);
    }

    @Test
    public void validateRatingWithErrorsTest() {
        // Objet Rating invalide
        Rating rating = new Rating();
        rating.setOrderNumber(-10);

        // Simuler un BindingResult avec des erreurs
        BindingResult result = mock(BindingResult.class);
        when(result.hasErrors()).thenReturn(true);

        String viewName = ratingController.validate(rating, result, model);

        verify(ratingService, never()).saveRating(rating);

        // Vérifier que la vue renvoyée est la vue d'ajout (car il y a des erreurs)
        assertEquals("rating/add", viewName);
    }
}