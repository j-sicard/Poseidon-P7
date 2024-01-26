package com.nnk.springboot.controlleurtest;

import com.nnk.springboot.AbstractConfigurationTest;
import com.nnk.springboot.controllers.TradeController;
import com.nnk.springboot.model.TradeModel;
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
        TradeModel trade = new TradeModel();
        trade.setAccount("AccountTest");
        trade.setType("TypeTest");
        trade.setBuyQuantity(10.0);

        List<TradeModel> trades = new ArrayList<>();
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

        verify(model).addAttribute(eq("trade"), Mockito.any(TradeModel.class));
    }

    @Test
    public void validateTradeWithNoErrorsTest() {
        // Objet Trade valide
        TradeModel trade = new TradeModel();
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
        TradeModel trade = new TradeModel();
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
        TradeModel expectedTrade = new TradeModel();
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

    @Test
    public void updateTradeSuccessTest() {
        Integer tradeId = 1;
        TradeModel trade = new TradeModel();
        trade.setTradeId(tradeId);

        // Configure le service pour retourner true lorsqu'on vérifie la présence de l'enchère
        when(tradeService.getById(tradeId)).thenReturn(Optional.of(trade));

        // Appele la méthode updateTrade et un objet Trade valide
        String viewName = tradeController.updateTrade(tradeId, trade, mock(BindingResult.class), model);

        // Vérifie que l'enchère est enregistrée avec le service
        verify(tradeService).saveTrade(trade);

        // Vérifie que le nom de la vue retournée est "redirect:/trade/list"
        assertEquals("redirect:/trade/list", viewName);
    }

    @Test
    public void updateTradeInvalidIdTest() {
        Integer tradeId = 999;

        // Configure le service pour retourner false lorsqu'on vérifie la présence de l'enchère
        when(tradeService.getById(tradeId)).thenReturn(Optional.empty());

        // Appele la méthode updateTrade et un objet Trade valide
        String viewName = tradeController.updateTrade(tradeId, new TradeModel(), mock(BindingResult.class), model);

        // Vérifie que la méthode redirige vers "/trade/list"
        assertEquals("redirect:/trade/list", viewName);

        // Vérifie que la méthode n'essaie pas de sauvegarder l'enchère
        verify(tradeService, never()).saveTrade(any(TradeModel.class));
    }

    @Test
    public void updateTradeValidationProblemTest() {
        Integer tradeId = 1;
        TradeModel trade = new TradeModel();
        trade.setTradeId(tradeId);

        // Configure le service pour retourner true lorsqu'on vérifie la présence de l'enchère
        when(tradeService.getById(tradeId)).thenReturn(Optional.of(trade));

        // Configure le BindingResult pour indiquer qu'il y a des erreurs de validation
        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(true);

        // Appel la méthode updateTrade et un objet Trade avec des erreurs de validation
        String viewName = tradeController.updateTrade(tradeId, trade, bindingResult, model);

        // Vérifie que la méthode renvoie la vue "/trade/add"
        assertEquals("/trade/add", viewName);

        // Vérifie que la méthode n'essaie pas de sauvegarder l'enchère
        verify(tradeService, never()).saveTrade(any(TradeModel.class));
    }

    @Test
    public void deleteTradeTest() {
        Integer tradeId = 1;
        TradeModel trade = new TradeModel();
        trade.setTradeId(tradeId);

        // Configure le service pour retourner l'enchère lorsqu'on vérifie la présence de l'enchère
        when(tradeService.getById(tradeId)).thenReturn(Optional.of(trade));

        // Appele la méthode deleteTrade
        String viewName = tradeController.deleteTrade(tradeId, model);

        // Vérifie que l'enchère est supprimée avec le service
        verify(tradeService).deleteTrade(trade);

        // Vérifie que le nom de la vue retournée est "redirect:/trade/list"
        assertEquals("redirect:/trade/list", viewName);

        // Vérifie que la liste des enchères est ajoutée au modèle
        verify(model).addAttribute(eq("trade"), anyList());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDeleteTradeInvalidId() {
        Integer tradeId = 999;

        // Configure le service pour retourner false lorsqu'on vérifie la présence de l'enchère
        when(tradeService.getById(tradeId)).thenReturn(Optional.empty());

        // Appele la méthode deleteBid avec l'ID factice
        tradeController.deleteTrade(tradeId, model);
    }
}
