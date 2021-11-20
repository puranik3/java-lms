package com.greatlearning.library;

import org.hibernate.cfg.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

@Repository
public class BookServiceImpl implements BookService {

	private SessionFactory sessionFactory;

	// create session
	private Session session;

	@Autowired
	BookServiceImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
		
		try {
			session = sessionFactory.getCurrentSession();
		} catch (HibernateException e) {
			session = sessionFactory.openSession();
		}
	}

	@Transactional
	public List<Book> findAll() {
		Transaction tx = session.beginTransaction();
		// DOUBT: Is the Book.class usage correct?
		List<Book> books = session.createQuery( "from Book"/*, Book.class*/ ).list(); // find all the records from the database table
		tx.commit();

		return books;
	}

	@Transactional
	public Book findById( int id ) {
		Book book = new Book();

		Transaction tx = session.beginTransaction();
		book = session.get(Book.class, id);
		tx.commit();

		return book;
	}

	@Transactional
	public void save( Book theBook ) {
		Transaction tx = session.beginTransaction();
		session.saveOrUpdate(theBook);
		tx.commit();
	}

	@Transactional
	public void deleteById( int id ) {
		Transaction tx = session.beginTransaction();

		// get transaction
		Book book = session.get(Book.class, id);

		// delete record
		session.delete(book);

		tx.commit();
	}

	@Transactional
	public List<Book> searchBy( String Name, String Author ) {
		Transaction tx = session.beginTransaction();
		
		// DOUBT: Should this not be "from Book"  
		String query = "";
		
		if( Name.length() != 0 && Author.length() != 0) {
			query = "from Book where name like '%" + Name + "%' or author like '%" + Author + "%'";
		} else if( Name.length() != 0 ) {
			query = "from Book where name like '%" + Name + "%'";
		} else if( Author.length() != 0 ) {
			query = "from Book where author like '%" + Author + "%'";
		} else {
			System.out.println( "Cannot search without input data" );
		}

		// DOUBT: Is the Book.class usage correct? 
		List<Book> book = session.createQuery( query/*, Book.class*/ ).list();

		tx.commit();

		return book;
	}

	@Transactional
	public void print( List<Book> book ) {
		for( Book b : book ) {
			System.out.println(b);
		}
	}
}