package com.nnk.springboot.servicetest;

import com.nnk.springboot.AbstractConfigurationTest;
import com.nnk.springboot.model.BidListModel;
import com.nnk.springboot.repositories.BidListRepository;
import com.nnk.springboot.service.BidListService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.List;
import java.util.Optional;

public class BidTests extends AbstractConfigurationTest{

	@Autowired
	private BidListRepository bidListRepository;
	@Autowired
	private BidListService bigListService;

	@Test
	public void bidListTest() {
		BidListModel bidList = new BidListModel();
		bidList.setAccount("Account Test");
		bidList.setType("Type Test");
		bidList.setBidQuantity(10.0);

		// Save
		bigListService.saveBidList(bidList);
		Assert.assertNotNull(bidList.getBidListId());
		Assert.assertEquals(bidList.getBidQuantity(), 10d, 10d);

		// Update
		bidList.setBidQuantity(20d);
		bigListService.saveBidList(bidList);
		Assert.assertEquals(bidList.getBidQuantity(), 20d, 20d);


		// Find
		List<BidListModel> listResult = bigListService.getAllBidLists();
		System.out.println("List size: " + listResult.size());
		Assert.assertTrue(listResult.size() > 0);

		// FindById
		Assert.assertTrue(bigListService.getbyid(bidList.getBidListId()).isPresent());


		// Delete
		Integer id = bidList.getBidListId();
		bigListService.deleteBidList(bidList);
		Optional<BidListModel> bidLists = bidListRepository.findById(id);
		Assert.assertFalse(bidLists.isPresent());
	}
}
