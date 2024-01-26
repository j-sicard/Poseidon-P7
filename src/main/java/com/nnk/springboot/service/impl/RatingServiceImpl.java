package com.nnk.springboot.service.impl;

import com.nnk.springboot.model.RatingModel;
import com.nnk.springboot.repositories.RatingRepository;
import com.nnk.springboot.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RatingServiceImpl implements RatingService {
    @Autowired
    RatingRepository ratingRepository;

    public List<RatingModel> getAllRatings(){
        return ratingRepository.findAll();
    }

    public void saveRating(RatingModel rating){
         ratingRepository.save(rating);
    }

    public void deleteRating(RatingModel rating){
        ratingRepository.delete(rating);
    }

    public Optional<RatingModel> getbyid(Integer id){
        return ratingRepository.findById(id);
    }
}
