package com.nnk.springboot.model;

import jakarta.persistence.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "rating")
public class RatingModel {

    @Id
    @Column()
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;
    @NotNull()
    @Column()
    private String moodysRating;
    @NotNull(message = "The size of sand PRating must be not null")
    @Column()
    private String sandPRating;
    @NotNull(message = "The size of sand PRating must be not null")
    @Column()
    private String fitchRating;
    @NotNull(message= "Order Number is mandatory")
    @Column()
    private Integer orderNumber;

    public RatingModel() {
    }

    public RatingModel(Integer id, String moodysRating, String sandPRating, String fitchRating, Integer orderNumber) {
        this.id = id;
        this.moodysRating = moodysRating;
        this.sandPRating = sandPRating;
        this.fitchRating = fitchRating;
        this.orderNumber = orderNumber;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMoodysRating() {
        return moodysRating;
    }

    public void setMoodysRating(String moodysRating) {
        this.moodysRating = moodysRating;
    }

    public String getSandPRating() {
        return sandPRating;
    }

    public void setSandPRating(String sandPRating) {
        this.sandPRating = sandPRating;
    }

    public String getFitchRating() {
        return fitchRating;
    }

    public void setFitchRating(String fitchRating) {
        this.fitchRating = fitchRating;
    }

    public Integer getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
    }
}
