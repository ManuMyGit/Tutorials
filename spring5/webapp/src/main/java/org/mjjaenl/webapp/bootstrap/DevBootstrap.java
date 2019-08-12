package org.mjjaenl.webapp.bootstrap;

import org.mjjaenl.webapp.model.Author;
import org.mjjaenl.webapp.model.Book;
import org.mjjaenl.webapp.model.Publisher;
import org.mjjaenl.webapp.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class DevBootstrap implements ApplicationListener<ContextRefreshedEvent> {
	private BookService bookService;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		initData();
	}
	
	@Autowired
	public DevBootstrap(BookService bookService) {
		this.bookService = bookService;
	}
	
	private void initData() {
		Author tolkien = new Author("JRR", "Tolkien");
		Publisher publisher = new Publisher("Barnes & Noble", "7700 W Northwest Hwy Ste. 300, Dallas, TX 75225");
		Book lotr = new Book("The Lord of the Rings", "9785170789221");
		lotr.setPublisher(publisher);
		tolkien.getBooks().add(lotr);
		lotr.getAuthors().add(tolkien);
		
		bookService.save(lotr);
	}
}
