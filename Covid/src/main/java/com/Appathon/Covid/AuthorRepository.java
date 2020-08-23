package com.Appathon.Covid;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

public interface AuthorRepository extends CrudRepository<Author, AuthorPK> {
}
