package com.nnk.springboot.controlleurtest;

import com.nnk.springboot.AbstractConfigurationTest;
import com.nnk.springboot.controllers.TradeController;
import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.service.TradeService;
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

public class TradeControllerTests extends AbstractConfigurationTest {
    @InjectMocks
    private TradeController tradeController;

    @Mock
    private TradeService tradeService;

    @Mock
    private Model model;

    @Test
    public void homeTest() {
        Trade trade = new Trade();
        trade.setAccount("AccountTest");
        trade.setType("TypeTest");
        trade.setBuyQuantity(10.0);

        List<Trade> trades = new ArrayList<>();
        trades.add(trade);

        when(tradeService.getAllTrades()).thenReturn(trades);
        String viewName = tradeController.home(model);

        assertEquals("trade/list", viewName);
        verify(model).addAttribute("trade", trades);
    }

    @Test
    public void addTradeFormTest() {
        String viewName = tradeController.addTradeForm(model);

        assertEquals("trade/add", viewName);

        verify(model).addAttribute(eq("trade"), Mockito.any(Trade.class));
    }

    @Test
    public void validateTradeWithNoErrorsTest() {
        // Objet Trade valide
        Trade trade = new Trade();
        trade.setBuyQuantity(10.0);

        // Simuler un BindingResult sans erreurs
        BindingResult result = mock(BindingResult.class);
        when(result.hasErrors()).thenReturn(false);

        // Appeler la méthode validate
        String viewName = tradeController.validate(trade, result, model);

        verify(tradeService, times(1)).saveTrade(trade);

        assertEquals("redirect:/trade/list", viewName);
    }

    @Test
    public void validateTradeWithErrorsTest() {
        // Objet BidList invalide
        Trade trade = new Trade();
        trade.setBuyQuantity(-10.0);

        // Simuler un BindingResult avec des erreurs
        BindingResult result = mock(BindingResult.class);
        when(result.hasErrors()).thenReturn(true);

        String viewName = tradeController.validate(trade, result, model);

        verify(tradeService, never()).saveTrade(trade);

        // Vérifier que la vue renvoyée est la vue d'ajout (car il y a des erreurs)
        assertEquals("trade/add", viewName);
    }

    @Test
    public void testShowUpdateForm() {
        Integer tradeId = 1;
        Trade expectedTrade = new Trade();
        expectedTrade.setTradeId(tradeId);

        // retourne l'enchère simulée lorsque getById est appelé
        when(tradeService.getById(tradeId)).thenReturn(Optional.of(expectedTrade));

        // Appelez la méthode showUpdateForm
        String viewName = tradeController.showUpdateForm(tradeId, model);

        // Vérifie que l'objet Trade a été ajouté au modèle avec le nom "trade"
        verify(model).addAttribute("trade", expectedTrade);

        // Vérifie que le nom de la vue retournée est "bidList/update"
        assertEquals("trade/update", viewName);
    }
}
