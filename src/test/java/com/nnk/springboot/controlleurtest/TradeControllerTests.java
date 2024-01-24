package com.nnk.springboot.controlleurtest;

import com.nnk.springboot.AbstractConfigurationTest;
import com.nnk.springboot.controllers.TradeController;
import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.service.TradeService;
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
}
