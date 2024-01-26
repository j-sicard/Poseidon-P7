package com.nnk.springboot.controlleurtest;

import com.nnk.springboot.AbstractConfigurationTest;
import com.nnk.springboot.controllers.CurveController;
import com.nnk.springboot.model.CurvePointModel;
import com.nnk.springboot.service.CurvePointService;
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

public class CurveControllerTests extends AbstractConfigurationTest {

    @InjectMocks
    private CurveController curveController;
    @Mock
    private CurvePointService curvePointService;

    @Mock
    private Model model;

    @Test
    public void homeTest() {
        CurvePointModel curvePoint = new CurvePointModel();
        curvePoint.setTerm(10.0);
        curvePoint.setValue(10.0);

        List<CurvePointModel> curvePoints = new ArrayList<>();
        curvePoints.add(curvePoint);

        when(curvePointService.getAllCurvePoints()).thenReturn(curvePoints);
        String viewName = curveController.home(model);

        assertEquals("curvePoint/list", viewName);
        verify(model).addAttribute("curvePoint", curvePoints);
    }

    @Test
    public void addCurveFormTest() {
        String viewName = curveController.addCurveForm(model);

        assertEquals("curvePoint/add", viewName);

        verify(model).addAttribute(eq("curvePoint"), Mockito.any(CurvePointModel.class));
    }

    @Test
    public void validateCurveWithNoErrorsTest() {
        // Objet CurvePoint valide
        CurvePointModel curvePoint = new CurvePointModel();
        curvePoint.setValue(10.0);

        // Simuler un BindingResult sans erreurs
        BindingResult result = mock(BindingResult.class);
        when(result.hasErrors()).thenReturn(false);

        // Appeler la méthode validate
        String viewName = curveController.validate(curvePoint, result, model);

        verify(curvePointService, times(1)).saveCurvePoint(curvePoint);

        assertEquals("redirect:curvePoint/list", viewName);
    }

    @Test
    public void validateCurveWithErrorsTest() {
        // Objet curvePoint invalide
        CurvePointModel curvePoint = new CurvePointModel();
        curvePoint.setValue(-5.0);

        // Simuler un BindingResult avec des erreurs
        BindingResult result = mock(BindingResult.class);
        when(result.hasErrors()).thenReturn(true);

        String viewName = curveController.validate(curvePoint, result, model);

        verify(curvePointService, never()).saveCurvePoint(curvePoint);

        // Vérifier que la vue renvoyée est la vue d'ajout (car il y a des erreurs)
        assertEquals("curvePoint/add", viewName);
    }

    @Test
    public void testShowUpdateForm() {
        Integer curveId = 1;
        CurvePointModel expectedCurvePoint = new CurvePointModel();
        expectedCurvePoint.setCurveId(curveId);

        // retourne l'enchère simulée lorsque getCurvePoint est appelé
        when(curvePointService.getCurvePoint(curveId)).thenReturn(Optional.of(expectedCurvePoint));

        // Appelez la méthode showUpdateForm
        String viewName = curveController.showUpdateForm(curveId, model);

        // Vérifie que l'objet CurvePoint a été ajouté au modèle avec le nom "curvePoint"
        verify(model).addAttribute("curvePoint", expectedCurvePoint);

        // Vérifie que le nom de la vue retournée est "curvePoint/update"
        assertEquals("curvePoint/update", viewName);
    }

    @Test
    public void updateCurveSuccessTest() {
        Integer curveId = 1;
        CurvePointModel curvePoint = new CurvePointModel();
        curvePoint.setCurveId(curveId);

        // Configure le service pour retourner true lorsqu'on vérifie la présence de l'enchère
        when(curvePointService.getCurvePoint(curveId)).thenReturn(Optional.of(curvePoint));

        // Appele la méthode UpdateCurve et un objet CurvePoint valide
        String viewName = curveController.updateCurve(curveId, curvePoint, mock(BindingResult.class), model);

        // Vérifie que l'enchère est enregistrée avec le service
        verify(curvePointService).saveCurvePoint(curvePoint);

        // Vérifie que le nom de la vue retournée est "redirect:/curvePoint/list"
        assertEquals("redirect:/curvePoint/list", viewName);
    }

    @Test
    public void updateCurveInvalidIdTest() {
        Integer curveId = 999;

        // Configure le service pour retourner false lorsqu'on vérifie la présence de l'enchère
        when(curvePointService.getCurvePoint(curveId)).thenReturn(Optional.empty());

        // Appele la méthode updateCurve et un objet CurvePoint valide
        String viewName = curveController.updateCurve(curveId, new CurvePointModel(), mock(BindingResult.class), model);

        // Vérifie que la méthode redirige vers "/curvePoint/list"
        assertEquals("redirect:/curvePoint/list", viewName);

        // Vérifie que la méthode n'essaie pas de sauvegarder l'enchère
        verify(curvePointService, never()).saveCurvePoint(any(CurvePointModel.class));
    }

    @Test
    public void updateCurveValidationProblemTest() {
        Integer curveId = 1;
        CurvePointModel curvePoint = new CurvePointModel();
        curvePoint.setCurveId(curveId);

        // Configure le service pour retourner true lorsqu'on vérifie la présence de l'enchère
        when(curvePointService.getCurvePoint(curveId)).thenReturn(Optional.of(curvePoint));

        // Configure le BindingResult pour indiquer qu'il y a des erreurs de validation
        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(true);

        // Appel la méthode updateCurve et un objet curvePoint avec des erreurs de validation
        String viewName = curveController.updateCurve(curveId, curvePoint, bindingResult, model);

        // Vérifie que la méthode renvoie la vue "/curvePoint/add"
        assertEquals("/curvePoint/add", viewName);

        // Vérifie que la méthode n'essaie pas de sauvegarder l'enchère
        verify(curvePointService, never()).saveCurvePoint(any(CurvePointModel.class));
    }

    @Test
    public void testDeleteCurve() {
        Integer curveId = 1;
        CurvePointModel curvePoint = new CurvePointModel();
        curvePoint.setId(curveId);

        // Configure le service pour retourner l'enchère lorsqu'on vérifie la présence de l'enchère
        when(curvePointService.getCurvePoint(curveId)).thenReturn(Optional.of(curvePoint));

        // Appele la méthode deleteCurve
        String viewName = curveController.deleteBid(curveId, model);

        // Vérifie que l'enchère est supprimée avec le service
        verify(curvePointService).deleteCurvePoint(curvePoint);

        // Vérifie que le nom de la vue retournée est "redirect:/curvePoint/list"
        assertEquals("redirect:/curvePoint/list", viewName);

        // Vérifie que la liste des enchères est ajoutée au modèle
        verify(model).addAttribute(eq("curvePoint"), anyList());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDeleteCurvePointInvalidId() {
        Integer curvePointId = 999;

        // Configure le service pour retourner false lorsqu'on vérifie la présence de l'enchère
        when(curvePointService.getCurvePoint(curvePointId)).thenReturn(Optional.empty());

        // Appele la méthode deleteBid avec l'ID factice
        curveController.deleteBid(curvePointId, model);
    }
}
