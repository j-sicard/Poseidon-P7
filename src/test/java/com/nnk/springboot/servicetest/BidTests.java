package com.nnk.springboot.servicetest;

import com.nnk.springboot.AbstractConfigurationTest;
import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import com.nnk.springboot.service.BigListService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.List;
import java.util.Optional;

public class BidTests extends AbstractConfigurationTest{

	@Autowired
	private BidListRepository bidListRepository;
	@Autowired
	private BigListService bigListService;

	@Test
	public void bidListTest() {
		BidList bidList = new BidList();
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
		List<BidList> listResult = bigListService.getAllBidLists();
		System.out.println("List size: " + listResult.size());
		Assert.assertTrue(listResult.size() > 0);


		// Delete
		Integer id = bidList.getBidListId();
		bigListService.deleteBidListById(id);
		Optional<BidList> bidLists = bidListRepository.findById(id);
		Assert.assertFalse(bidLists.isPresent());
	}
}
