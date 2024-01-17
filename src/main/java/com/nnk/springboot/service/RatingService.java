package com.nnk.springboot.service;

import com.nnk.springboot.domain.Rating;

import java.util.List;

public interface RatingService {
    public List<Rating> getAllRatings();

    public void saveRating(Rating rating);

    public void deleteRating(Integer id);

}
