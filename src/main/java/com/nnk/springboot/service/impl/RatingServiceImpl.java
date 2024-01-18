package com.nnk.springboot.service.impl;

import com.nnk.springboot.domain.Rating;
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

    public List<Rating> getAllRatings(){
        return ratingRepository.findAll();
    }

    public void saveRating(Rating rating){
         ratingRepository.save(rating);
    }

    public void deleteRating(Rating rating){
        ratingRepository.delete(rating);
    }

    public Optional<Rating> getbyid(Integer id){
        return ratingRepository.findById(id);
    }
}
