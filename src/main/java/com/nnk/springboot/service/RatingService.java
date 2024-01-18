package com.nnk.springboot.service;

import com.nnk.springboot.domain.Rating;

import java.util.List;
import java.util.Optional;

public interface RatingService {
    public List<Rating> getAllRatings();

    public void saveRating(Rating rating);

    public void deleteRating(Rating rating);

    public Optional<Rating> getbyid(Integer id);

}
