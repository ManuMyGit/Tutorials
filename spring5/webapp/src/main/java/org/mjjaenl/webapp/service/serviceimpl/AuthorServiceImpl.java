package org.mjjaenl.webapp.service.serviceimpl;

import org.mjjaenl.webapp.model.Author;
import org.mjjaenl.webapp.repository.AuthorRepository;
import org.mjjaenl.webapp.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorServiceImpl extends GenericServiceImpl<Author, AuthorRepository> implements AuthorService {
	@Autowired
	public AuthorServiceImpl(AuthorRepository repository) {
		super(repository);
	}
}
