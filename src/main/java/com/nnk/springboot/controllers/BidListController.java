package com.nnk.springboot.controllers;

import com.nnk.springboot.model.BidListModel;
import com.nnk.springboot.service.BidListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
public class BidListController {
    @Autowired
    BidListService bigListService;

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(BidListController.class.getName());

    @RequestMapping("/bidList/list")
    public String home(Model model)
    {
        model.addAttribute("bidList", bigListService.getAllBidLists());
        logger.info("bidList/list : OK");
        return "bidList/list";
    }

    @GetMapping("/bidList/add")
    public String addBidForm(Model model) {
        model.addAttribute("bidList", new BidListModel());
        logger.info("GetMapping(\"/bidList/add\") successfully");
        return "bidList/add";
    }

    @PostMapping("/bidList/validate")
    public String validate(@Valid BidListModel bidList, BindingResult result, Model model) {
        if (!result.hasErrors()){
            bigListService.saveBidList(bidList);
            logger.info("recording successfully completed");
            return "redirect:/bidList/list";
        }
        logger.info("validation problem occurred ( /bidList/validate )");
        return "bidList/add";
    }

    @GetMapping("/bidList/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        // TODO: get Bid by Id and to model then show to the form
        BidListModel bidList = bigListService.getbyid(id).orElseThrow(()-> new IllegalArgumentException("Invalid id:" + id));
        logger.info("/bidList/update/{id}" + bidList.toString());
        model.addAttribute("bidList", bidList);
        return "bidList/update";
    }

    @PostMapping("/bidList/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid BidListModel bidList,
                             BindingResult result, Model model) {
        // TODO: check required fields, if valid call service to update Bid and return list Bid
        if (!bigListService.getbyid(id).isPresent()){
            logger.info("Invalid id:" + id);
            return "redirect:/bidList/list";
        }
        if (!result.hasErrors()){
            bigListService.saveBidList(bidList);
            logger.info("update successful");
            return "redirect:/bidList/list";
        }
        logger.info("validation problem occurred ( /bidList/update/{id} )");
        return "/bidList/add";
    }

    @GetMapping("/bidList/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
        // TODO: Find Bid by Id and delete the bid, return to Bid list*
        BidListModel bidList = bigListService.getbyid(id).orElseThrow(()-> new IllegalArgumentException("Invalide bid Id:" + id));
        logger.info("/bidList/delete/{id}" + bidList.toString());
        bigListService.deleteBidList(bidList);
        model.addAttribute("bidList", bigListService.getAllBidLists());
        return "redirect:/bidList/list";
    }
}
