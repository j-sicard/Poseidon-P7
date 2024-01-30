package com.nnk.springboot.model;

import jakarta.persistence.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "rulename")
public class RuleNameModel {
    @Id
    @Column()
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;
    @NotBlank(message= "Rule name is mandatory")
    @Size(max=120, message = "The size of the name must be of maximum 120 characters")
    @Column()
    private String name;
    @NotBlank(message = "Description is mandatory")
    @Size(max=120, message = "The size of the description must be of maximum 120 characters")
    @Column()
    private String description;
    @Size(max=120)
    @Column()
    private String json;
    @Size(max=512)
    @Column()
    private String template;
    @Size(max=120)
    @Column()
    private String sqlStr;
    @Size(max=120)
    @Column()
    private String sqlPart;

    public RuleNameModel() {
    }

    public RuleNameModel(Integer id, String name, String description, String json, String template, String sqlStr, String sqlPart) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.json = json;
        this.template = template;
        this.sqlStr = sqlStr;
        this.sqlPart = sqlPart;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public String getSqlStr() {
        return sqlStr;
    }

    public void setSqlStr(String sqlStr) {
        this.sqlStr = sqlStr;
    }

    public String getSqlPart() {
        return sqlPart;
    }

    public void setSqlPart(String sqlPart) {
        this.sqlPart = sqlPart;
    }
}
