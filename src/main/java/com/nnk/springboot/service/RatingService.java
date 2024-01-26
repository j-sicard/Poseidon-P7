package com.nnk.springboot.service;

import com.nnk.springboot.model.RatingModel;

import java.util.List;
import java.util.Optional;

public interface RatingService {
    public List<RatingModel> getAllRatings();

    public void saveRating(RatingModel rating);

    public void deleteRating(RatingModel rating);

    public Optional<RatingModel> getbyid(Integer id);

}
