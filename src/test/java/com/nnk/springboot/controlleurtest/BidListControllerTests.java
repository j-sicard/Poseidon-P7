package com.nnk.springboot.controlleurtest;

import com.nnk.springboot.AbstractConfigurationTest;
import com.nnk.springboot.controllers.BidListController;
import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.service.BigListService;
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


public class BidListControllerTests extends AbstractConfigurationTest {
    @InjectMocks
    private BidListController bidListController;

    @Mock
    private BigListService bigListService;

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

        when(bigListService.getAllBidLists()).thenReturn(bidListsTest);
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


}