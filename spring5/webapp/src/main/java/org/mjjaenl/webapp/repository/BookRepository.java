package org.mjjaenl.webapp.repository;

import org.mjjaenl.webapp.model.Book;
import org.springframework.data.repository.CrudRepository;

public interface BookRepository extends CrudRepository<Book, Long> {

}
