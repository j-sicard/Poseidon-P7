package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.service.BigListService;
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
    BigListService bigListService;

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(BidListController.class.getName());

    @RequestMapping("/bidList/list")
    public String home(Model model)
    {
        model.addAttribute("bidlist", bigListService.getAllBidLists());
        return "bidList/list";
    }

    @GetMapping("/bidList/add")
    public String addBidForm(BidList bid) {
        return "bidList/add";
    }

    @PostMapping("/bidList/validate")
    public String validate(@Valid BidList bid, BindingResult result, Model model) {
        // TODO: check data valid and save to db, after saving return bid list
        if (!bigListService.getbyid(bid.getBidListId()).isPresent() && result.hasFieldErrors()){
            logger.info("validation problem occurred");
            return "bidList/add";
        }
        logger.info(bid.toString());
        bigListService.saveBidList(bid);
        model.addAttribute("bidlist", bigListService.getAllBidLists());
        return "bidList/add";
    }

    @GetMapping("/bidList/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        // TODO: get Bid by Id and to model then show to the form
        BidList bidList = bigListService.getbyid(id).orElseThrow(()-> new IllegalArgumentException("Invalid id:" + id));
        logger.info(bidList.toString());
        model.addAttribute("bidlist", bidList);
        return "bidList/update";
    }

    @PostMapping("/bidList/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid BidList bidList,
                             BindingResult result, Model model) {
        // TODO: check required fields, if valid call service to update Bid and return list Bid
        if (!bigListService.getbyid(id).isPresent() && result.hasFieldErrors()){
            logger.info("Invalid id:" + id + "or a validation problem occurred");
            return "bidList/update";
        }
        logger.info(bidList.toString());
        bigListService.saveBidList(bidList);
        model.addAttribute("bidlist", bigListService.getAllBidLists());
        return "redirect:/bidList/list";
    }

    @GetMapping("/bidList/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
        // TODO: Find Bid by Id and delete the bid, return to Bid list*
        BidList bid = bigListService.getbyid(id).orElseThrow(()-> new IllegalArgumentException("Invalide bid Id:" + id));
        logger.info(bid.toString());
        bigListService.deleteBidList(bid);
        model.addAttribute("bidlist", bigListService.getAllBidLists());
        return "redirect:/bidList/list";
    }
}
