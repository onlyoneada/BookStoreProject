package com.bookstoredb.entity;

import java.util.HashSet;
import java.util.Set;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import static jakarta.persistence.GenerationType.IDENTITY;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "category", catalog = "bookstoredb")
@NamedQueries({
	@NamedQuery(name="Category.findAll", query = "SELECT c FROM Category c ORDER BY c.name"),
	@NamedQuery(name="Category.countAll", query = "SELECT COUNT(*) FROM Category c"),
	@NamedQuery(name="Category.findByName", query = "SELECT c FROM Category c WHERE c.name =:name")
	})

public class Category implements java.io.Serializable {

	private Integer categoryId;
	private String name;
	private Set<Book> books = new HashSet<Book>(0);

	public Category() {
	}

	public Category(String name) {
		this.name = name;
	}

	public Category(String name, Set<Book> books) {
		this.name = name;
		this.books = books;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "category_id", unique = true, nullable = false)
	public Integer getCategoryId() {
		return this.categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	@Column(name = "name", nullable = false, length = 30)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "category")
	public Set<Book> getBooks() {
		return this.books;
	}

	public void setBooks(Set<Book> books) {
		this.books = books;
	}

}
