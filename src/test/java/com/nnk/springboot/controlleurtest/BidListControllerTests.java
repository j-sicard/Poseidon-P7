package com.nnk.springboot.controlleurtest;

import com.nnk.springboot.AbstractConfigurationTest;
import com.nnk.springboot.controllers.BidListController;
import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.service.BidListService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class BidListControllerTests extends AbstractConfigurationTest {
    @InjectMocks
    private BidListController bidListController;

    @Mock
    private BidListService bidListService;

    @Mock
    private Model model;

    @Test
    public void homeTest() {
        BidList bidList1 = new BidList();
        bidList1.setAccount("AccountTest1");
        bidList1.setType("TypeTest1");
        bidList1.setBidQuantity(10.0);

        List<BidList> bidListsTest = new ArrayList<>();
        bidListsTest.add(bidList1);

        when(bidListService.getAllBidLists()).thenReturn(bidListsTest);
        String viewName = bidListController.home(model);

        assertEquals("bidList/list", viewName);
        verify(model).addAttribute("bidList", bidListsTest);
    }

    @Test
    public void addBidFormTest() {
        String viewName = bidListController.addBidForm(model);

        assertEquals("bidList/add", viewName);

        verify(model).addAttribute(eq("bidList"), Mockito.any(BidList.class));
    }

    @Test
    public void validateBidListWithNoErrorsTest() {
        // Objet BidList valide
        BidList bidList = new BidList();
        bidList.setBidQuantity(10.0);

        // Simuler un BindingResult sans erreurs
        BindingResult result = mock(BindingResult.class);
        when(result.hasErrors()).thenReturn(false);

        // Appeler la méthode validate
        String viewName = bidListController.validate(bidList, result, model);

        verify(bidListService, times(1)).saveBidList(bidList);

        assertEquals("redirect:/bidList/list", viewName);
    }

    @Test
    public void validateBidListWithErrorsTest() {
        // Objet BidList invalide
        BidList bidList = new BidList();
        bidList.setBidQuantity(-5.0);

        // Simuler un BindingResult avec des erreurs
        BindingResult result = mock(BindingResult.class);
        when(result.hasErrors()).thenReturn(true);

        String viewName = bidListController.validate(bidList, result, model);

        verify(bidListService, never()).saveBidList(bidList);

        // Vérifier que la vue renvoyée est la vue d'ajout (car il y a des erreurs)
        assertEquals("bidList/add", viewName);
    }
}