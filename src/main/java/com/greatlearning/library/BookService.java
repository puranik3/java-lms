package com.greatlearning.library;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public interface BookService {
	public List<Book> findAll();
	public List<Book> searchBy( String name, String author );
	public Book findById( int theId );
	public void save( Book theBook );
	public void deleteById( int theId );
}