package com.Appathon.Covid;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

public interface PaperRepository extends CrudRepository<Paper, String> {
    @Query(value = "SELECT * FROM paper WHERE title LIKE %?1% ", nativeQuery = true)
    public Collection<Paper> findArticleFromTitle(String word);
}
