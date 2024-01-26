package com.nnk.springboot.controlleurtest;

import com.nnk.springboot.AbstractConfigurationTest;
import com.nnk.springboot.controllers.RatingController;
import com.nnk.springboot.model.RatingModel;
import com.nnk.springboot.service.RatingService;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        RatingModel rating = new RatingModel();
        rating.setMoodysRating("MoodysRatingTest");
        rating.setSandPRating("SandPRatingTest");
        rating.setFitchRating("FitchRatingTest");
        rating.setOrderNumber(10);

        List<RatingModel> ratings = new ArrayList<>();
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

        verify(model).addAttribute(eq("rating"), Mockito.any(RatingModel.class));
    }

    @Test
    public void validateRatingWithNoErrorsTest() {
        // Objet Rating valide
        RatingModel rating = new RatingModel();
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
        RatingModel rating = new RatingModel();
        rating.setOrderNumber(-10);

        // Simuler un BindingResult avec des erreurs
        BindingResult result = mock(BindingResult.class);
        when(result.hasErrors()).thenReturn(true);

        String viewName = ratingController.validate(rating, result, model);

        verify(ratingService, never()).saveRating(rating);

        // Vérifier que la vue renvoyée est la vue d'ajout (car il y a des erreurs)
        assertEquals("rating/add", viewName);
    }

    @Test
    public void testShowUpdateForm() {
        Integer ratinId = 1;
        RatingModel expectedRating = new RatingModel();
        expectedRating.setId(ratinId);

        // retourne l'enchère simulée lorsque getbyid est appelé
        when(ratingService.getbyid(ratinId)).thenReturn(Optional.of(expectedRating));

        // Appelez la méthode showUpdateForm
        String viewName = ratingController.showUpdateForm(ratinId, model);

        // Vérifie que l'objet Rating a été ajouté au modèle avec le nom "rating"
        verify(model).addAttribute("rating", expectedRating);

        // Vérifie que le nom de la vue retournée est "rating/update"
        assertEquals("rating/update", viewName);
    }

    @Test
    public void updateRatingSuccessTest() {
        Integer ratingId = 1;
        RatingModel rating = new RatingModel();
        rating.setId(ratingId);

        // Configure le service pour retourner true lorsqu'on vérifie la présence de l'enchère
        when(ratingService.getbyid(ratingId)).thenReturn(Optional.of(rating));

        // Appele la méthode updateRating et un objet Rating valide
        String viewName = ratingController.updateRating(ratingId, rating, mock(BindingResult.class), model);

        // Vérifie que l'enchère est enregistrée avec le service
        verify(ratingService).saveRating(rating);

        // Vérifie que le nom de la vue retournée est "redirect:/rating/list"
        assertEquals("redirect:/rating/list", viewName);
    }

    @Test
    public void updateRatingInvalidIdTest() {
        Integer ratingId = 999;

        // Configure le service pour retourner false lorsqu'on vérifie la présence de l'enchère
        when(ratingService.getbyid(ratingId)).thenReturn(Optional.empty());

        // Appele la méthode updateRating et un objet Rating valide
        String viewName = ratingController.updateRating(ratingId, new RatingModel(), mock(BindingResult.class), model);

        // Vérifie que la méthode redirige vers "/rating/list"
        assertEquals("redirect:/rating/list", viewName);

        // Vérifie que la méthode n'essaie pas de sauvegarder l'enchère
        verify(ratingService, never()).saveRating(any(RatingModel.class));
    }

    @Test
    public void updateRatingValidationProblemTest() {
        Integer ratingId = 1;
        RatingModel rating = new RatingModel();
        rating.setId(ratingId);

        // Configure le service pour retourner true lorsqu'on vérifie la présence de l'enchère
        when(ratingService.getbyid(ratingId)).thenReturn(Optional.of(rating));

        // Configure le BindingResult pour indiquer qu'il y a des erreurs de validation
        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(true);

        // Appel la méthode updateRating et un objet Rating avec des erreurs de validation
        String viewName = ratingController.updateRating(ratingId, rating, bindingResult, model);

        // Vérifie que la méthode renvoie la vue "/rating/add"
        assertEquals("/rating/add", viewName);

        // Vérifie que la méthode n'essaie pas de sauvegarder l'enchère
        verify(ratingService, never()).saveRating(any(RatingModel.class));
    }

    @Test
    public void testDeleteRating() {
        Integer ratingId = 1;
        RatingModel rating = new RatingModel();
        rating.setId(ratingId);

        // Configure le service pour retourner l'enchère lorsqu'on vérifie la présence de l'enchère
        when(ratingService.getbyid(ratingId)).thenReturn(Optional.of(rating));

        // Appele la méthode deleteRating
        String viewName = ratingController.deleteRating(ratingId, model);

        // Vérifie que l'enchère est supprimée avec le service
        verify(ratingService).deleteRating(rating);

        // Vérifie que le nom de la vue retournée est "redirect:/rating/list"
        assertEquals("redirect:/rating/list", viewName);

        // Vérifie que la liste des enchères est ajoutée au modèle
        verify(model).addAttribute(eq("rating"), anyList());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDeleteRatingInvalidId() {
        Integer ratingId = 999;

        // Configure le service pour retourner false lorsqu'on vérifie la présence de l'enchère
        when(ratingService.getbyid(ratingId)).thenReturn(Optional.empty());

        // Appele la méthode deleteBid avec l'ID factice
        ratingController.deleteRating(ratingId, model);
    }
}