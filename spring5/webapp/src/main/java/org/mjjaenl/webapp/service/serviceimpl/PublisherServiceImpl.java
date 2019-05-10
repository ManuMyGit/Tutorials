package org.mjjaenl.webapp.service.serviceimpl;

import org.mjjaenl.webapp.model.Publisher;
import org.mjjaenl.webapp.repository.PublisherRepository;
import org.mjjaenl.webapp.service.PublisherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PublisherServiceImpl extends GenericServiceImpl<Publisher, PublisherRepository> implements PublisherService {
	@Autowired
	public PublisherServiceImpl(PublisherRepository repository) {
		super(repository);
	}
}
