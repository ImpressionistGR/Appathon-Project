package com.Appathon.Covid;

import javax.persistence.*;
import java.util.Collection;

@Entity
@IdClass(AuthorPK.class)
public class Author {
    @Id
    private String sha;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer author_id;

    @Column(name = "fullname")
    private String fullName;

    private String institution;

    private String settlement;

    private String country;

    public String getSha() {
        return sha;
    }

    public void setSha(String sha) {
        this.sha = sha;
    }

    public Integer getAuthor_id() {
        return author_id;
    }

    public void setAuthor_id(Integer author_id) {
        this.author_id = author_id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getInstitution() {
        return institution;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
    }

    public String getSettlement() {
        return settlement;
    }

    public void setSettlement(String settlement) {
        this.settlement = settlement;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
