package com.nnk.springboot.controlleurtest;

import com.nnk.springboot.AbstractConfigurationTest;
import com.nnk.springboot.controllers.CurveController;
import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.domain.CurvePoint;
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
        CurvePoint curvePoint = new CurvePoint();
        curvePoint.setTerm(10.0);
        curvePoint.setValue(10.0);

        List<CurvePoint> curvePoints = new ArrayList<>();
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

        verify(model).addAttribute(eq("curvePoint"), Mockito.any(CurvePoint.class));
    }

    @Test
    public void validateCurveWithNoErrorsTest() {
        // Objet CurvePoint valide
        CurvePoint curvePoint = new CurvePoint();
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
        CurvePoint curvePoint = new CurvePoint();
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
        CurvePoint expectedCurvePoint = new CurvePoint();
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
}
