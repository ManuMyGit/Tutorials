package org.mjjaenl.webapp.service;

import org.mjjaenl.webapp.model.Author;
import org.mjjaenl.webapp.repository.AuthorRepository;
import org.springframework.stereotype.Service;

@Service
public interface AuthorService extends GenericService<Author, AuthorRepository> {

}
