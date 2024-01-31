package com.nnk.springboot.controllers;

import com.nnk.springboot.model.TradeModel;
import com.nnk.springboot.service.TradeService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class TradeController {
    @Autowired
    TradeService tradeService;
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(TradeController.class.getName());

    @ModelAttribute("remoteUser")
    public Object remoteUser(final HttpServletRequest request) {
        logger.info("Request.getRemoteUser() is:" + request.getRemoteUser());
        return request.getRemoteUser();
    }

    @RequestMapping("/trade/list")
    public String home(Model model)
    {
        model.addAttribute("trade", tradeService.getAllTrades());
        logger.info("trade/list : OK");
        return "trade/list";
    }

    @GetMapping("/trade/add")
    public String addTradeForm(Model model) {
        model.addAttribute("trade", new TradeModel());
        logger.info("GetMapping(\"/trade/add\") successfully");
        return "trade/add";
    }

    @PostMapping("/trade/validate")
    public String validate(@Valid TradeModel trade, BindingResult result, Model model) {
        if (!result.hasErrors()){
            tradeService.saveTrade(trade);
            logger.info("recording successfully completed");
            return "redirect:/trade/list";
        }
        logger.info("validation problem occurred (/trade/validate)");
        return "trade/add";
    }

    @GetMapping("/trade/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        TradeModel trade = tradeService.getById(id).orElseThrow(()-> new IllegalArgumentException("Invalid id:" + id));
        logger.info("/trade/update/{id}" +trade.toString());
        model.addAttribute("trade", trade);
        return "trade/update";
    }

    @PostMapping("/trade/update/{id}")
    public String updateTrade(@PathVariable("id") Integer id, @Valid TradeModel trade,
                             BindingResult result, Model model) {
        if (!tradeService.getById(id).isPresent()){
            logger.info("Invalid id:" + id);
            return "redirect:/trade/list";
        }
        if (!result.hasErrors()){
            tradeService.saveTrade(trade);
            logger.info("update successful");
            return "redirect:/trade/list";
        }
        logger.info("validation problem occurred ( /trade/update/{id} )");
        return "/trade/add";
    }

    @GetMapping("/trade/delete/{id}")
    public String deleteTrade(@PathVariable("id") Integer id, Model model) {
        TradeModel trade = tradeService.getById(id).orElseThrow(()-> new  IllegalArgumentException("Invalid id:" + id));
        logger.info( "/trade/delete/{id}" + trade.toString());
        tradeService.deleteTrade(trade);
        model.addAttribute("trade", tradeService.getAllTrades());
        return "redirect:/trade/list";
    }

}
