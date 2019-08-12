package org.mjjaenl.webapp.service.serviceimpl;

import org.mjjaenl.webapp.model.Book;
import org.mjjaenl.webapp.repository.BookRepository;
import org.mjjaenl.webapp.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl extends GenericServiceImpl<Book, BookRepository> implements BookService {
	@Autowired
	public BookServiceImpl(BookRepository repository) {
		super(repository);
	}
}
