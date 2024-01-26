package com.nnk.springboot.servicetest;

import com.nnk.springboot.AbstractConfigurationTest;
import com.nnk.springboot.model.RatingModel;
import com.nnk.springboot.repositories.RatingRepository;
import com.nnk.springboot.service.RatingService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RatingTests extends AbstractConfigurationTest {

	@Autowired
	private RatingRepository ratingRepository;

	@Autowired
	private RatingService ratingService;


	@Test
	public void ratingTest() {
		RatingModel rating = new RatingModel();
		rating.setMoodysRating("Moodys Rating");
		rating.setSandPRating("Sand PRating");
		rating.setFitchRating("Fitch Rating");
		rating.setOrderNumber(10);

		// Save
		ratingService.saveRating(rating);
		Assert.assertNotNull(rating.getId());
		Assert.assertTrue(rating.getOrderNumber() == 10);

		// Update
		rating.setOrderNumber(20);
		ratingService.saveRating(rating);
		Assert.assertTrue(rating.getOrderNumber() == 20);

		// Find
		List<RatingModel> listResult = ratingService.getAllRatings();
		Assert.assertTrue(listResult.size() > 0);

		// FindById
		Assert.assertTrue(ratingService.getbyid(rating.getId()).isPresent());

		// Delete
		Integer id = rating.getId();
		ratingService.deleteRating(rating);
		Optional<RatingModel> ratingList = ratingRepository.findById(id);
		Assert.assertFalse(ratingList.isPresent());
	}
}
