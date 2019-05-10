package org.mjjaenl.webapp.service;

import org.mjjaenl.webapp.model.Book;
import org.mjjaenl.webapp.repository.BookRepository;
import org.springframework.stereotype.Service;

@Service
public interface BookService extends GenericService<Book, BookRepository> {

}
