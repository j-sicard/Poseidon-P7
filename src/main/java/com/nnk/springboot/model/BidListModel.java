package com.nnk.springboot.model;

import jakarta.persistence.*;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Timestamp;

@Entity
@Table(name = "bidlist")
public class BidListModel {
    @Id
    @Column()
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer BidListId;

    @NotBlank(message = "Account is mandatory")
    @Size(max = 35, message = "The size of the sandPRating must be of maximum 35 characters")
    @Column()
    private  String account;

    @NotBlank(message = "Type is mandatory")
    @Size(max = 35, message = "The size of the sandPRating must be of maximum 35 characters")
    @Column()
    private  String  type;

    @NotNull(message = "Bid Quantity is mandatory")
    @DecimalMin(value = "0", inclusive = false, message = "Bid Quantity must be positive")
    @Column()
    private  Double bidQuantity;
    @Column()
    private  Double askQuantity;
    @Column()
    private  Double bid;
    @Column()
    private  Double ask;
    @Column()
    private  String benchmark;
    @Column()
    private  Timestamp bidListDate;
    @Column()
    private  String commentary;
    @Column()
    private  String security;
    @Column()
    private String status;
    @Column()
    private String trader;
    @Column()
    private String book;
    @Column()
    private  String creationName;
    @Column()
    private  Timestamp creationDate;
    @Column()
    private  String revisionName;
    @Column()
    private  String dealName;
    @Column()
    private  String dealType;
    @Column()
    private  String sourceListId;
    @Column()
    private String side;

    public BidListModel() {
    }

    public BidListModel(Integer bigListId, String account, String type, Double bigQuantity, Double askQuantity, Double bid, Double ask, String benchmark, Timestamp bidListDate, String commentary, String security, String status, String trader, String book, String creationName, Timestamp creationDate, String revisionName, String dealName, String dealType, String sourceListId, String side) {
        BidListId = bigListId;
        this.account = account;
        this.type = type;
        this.bidQuantity = bigQuantity;
        this.askQuantity = askQuantity;
        this.bid = bid;
        this.ask = ask;
        this.benchmark = benchmark;
        this.bidListDate = bidListDate;
        this.commentary = commentary;
        this.security = security;
        this.status = status;
        this.trader = trader;
        this.book = book;
        this.creationName = creationName;
        this.creationDate = creationDate;
        this.revisionName = revisionName;
        this.dealName = dealName;
        this.dealType = dealType;
        this.sourceListId = sourceListId;
        this.side = side;
    }

    public Integer getBidListId() {
        return BidListId;
    }

    public void setBidListId(Integer bidListId) {
        BidListId = bidListId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getBidQuantity() {
        return bidQuantity;
    }

    public void setBidQuantity(Double bidQuantity) {
        this.bidQuantity = bidQuantity;
    }

    public Double getAskQuantity() {
        return askQuantity;
    }

    public void setAskQuantity(Double askQuantity) {
        this.askQuantity = askQuantity;
    }

    public Double getBid() {
        return bid;
    }

    public void setBid(Double bid) {
        this.bid = bid;
    }

    public Double getAsk() {
        return ask;
    }

    public void setAsk(Double ask) {
        this.ask = ask;
    }

    public String getBenchmark() {
        return benchmark;
    }

    public void setBenchmark(String benchmark) {
        this.benchmark = benchmark;
    }

    public Timestamp getBidListDate() {
        return bidListDate;
    }

    public void setBidListDate(Timestamp bidListDate) {
        this.bidListDate = bidListDate;
    }

    public String getCommentary() {
        return commentary;
    }

    public void setCommentary(String commentary) {
        this.commentary = commentary;
    }

    public String getSecurity() {
        return security;
    }

    public void setSecurity(String security) {
        this.security = security;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTrader() {
        return trader;
    }

    public void setTrader(String trader) {
        this.trader = trader;
    }

    public String getBook() {
        return book;
    }

    public void setBook(String book) {
        this.book = book;
    }

    public String getCreationName() {
        return creationName;
    }

    public void setCreationName(String creationName) {
        this.creationName = creationName;
    }

    public Timestamp getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }

    public String getRevisionName() {
        return revisionName;
    }

    public void setRevisionName(String revisionName) {
        this.revisionName = revisionName;
    }

    public String getDealName() {
        return dealName;
    }

    public void setDealName(String dealName) {
        this.dealName = dealName;
    }

    public String getDealType() {
        return dealType;
    }

    public void setDealType(String dealType) {
        this.dealType = dealType;
    }

    public String getSourceListId() {
        return sourceListId;
    }

    public void setSourceListId(String sourceListId) {
        this.sourceListId = sourceListId;
    }

    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
    }
}
