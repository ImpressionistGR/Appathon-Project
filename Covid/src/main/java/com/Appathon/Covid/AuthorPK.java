package com.Appathon.Covid;

import java.io.Serializable;

public class AuthorPK implements Serializable {
    private String sha;

    private Integer author_id;

    public AuthorPK() {
    }

    public AuthorPK(String sha, Integer author_id) {
        this.sha = sha;
        this.author_id = author_id;
    }

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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((sha == null) ? 0 : sha.hashCode());
        result = prime * result + author_id;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        AuthorPK other = (AuthorPK) obj;
        if (sha == null) {
            if (other.sha != null)
                return false;
        } else if (!sha.equals(other.sha))
            return false;
        if (author_id != other.author_id)
            return false;
        return true;
    }
}
