package com.nnk.springboot.domain;

import jakarta.persistence.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "rating")
public class Rating {

    @Id
    @Column()
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;
    @Size(max=120, message = "The size of moodys Rating must be of maximum 120 characters")
    @Column()
    private String moodysRating;
    @Size(max=120, message = "The size of sand PRating must be of maximum 120 characters")
    @Column()
    private String sandPRating;
    @Size(max=120, message = "The size of fitch Rating must be of maximum 120 characters")
    @Column()
    private String fitchRating;
    @NotNull(message= "Order Number is mandatory")
    @Column()
    private Integer orderNumber;

    public Rating() {
    }

    public Rating(Integer id, String moodysRating, String sandPRating, String fitchRating, Integer orderNumber) {
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
