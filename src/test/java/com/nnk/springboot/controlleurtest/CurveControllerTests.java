package com.nnk.springboot.controlleurtest;

import com.nnk.springboot.AbstractConfigurationTest;
import com.nnk.springboot.controllers.CurveController;
import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.service.CurvePointService;
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

public class CurveControllerTests extends AbstractConfigurationTest {

    @InjectMocks
    private CurveController curveController;
    @Mock
    private CurvePointService pointService;

    @Mock
    private Model model;

    @Test
    public void homeTest() {
        CurvePoint curvePoint = new CurvePoint();
        curvePoint.setTerm(10.0);
        curvePoint.setValue(10.0);

        List<CurvePoint> curvePoints = new ArrayList<>();
        curvePoints.add(curvePoint);

        when(pointService.getAllCurvePoints()).thenReturn(curvePoints);
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
}