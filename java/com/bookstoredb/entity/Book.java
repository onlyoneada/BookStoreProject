package com.bookstoredb.entity;

import java.util.Base64;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.apache.taglibs.standard.lang.jstl.test.beans.PublicBean1;
import org.junit.Test;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import static jakarta.persistence.GenerationType.IDENTITY;
import static org.junit.Assert.assertTrue;

import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Transient;
import jakarta.persistence.UniqueConstraint;

@Entity
@NamedQueries({
	@NamedQuery(name="Book.findAll", query = "SELECT b FROM Book b"),
	@NamedQuery(name="Book.findByTitle", query = "SELECT b FROM Book b WHERE b.title =:title"),
	@NamedQuery(name="Book.countAll", query = "SELECT COUNT(*) FROM Book b"),
	@NamedQuery(name="Book.findByCategory", query = "SELECT b FROM Book b JOIN Category c ON b.category.categoryId = c.categoryId AND c.categoryId =:catId"),
	@NamedQuery(name="Book.listNew", query = "SELECT b FROM Book b ORDER BY b.publishDate DESC"),
	@NamedQuery(name="Book.search", query = "SELECT b FROM Book b WHERE b.title LIKE '%' || :keyword || '%'"
	+ " OR b.author LIKE '%' || :keyword || '%'"
			+ " OR b.description LIKE '%' || :keyword || '%'")
	})
@Table(name = "book", catalog = "bookstoredb", uniqueConstraints = @UniqueConstraint(columnNames = "title"))
public class Book implements java.io.Serializable {
	private Integer bookId;
	private Category category;
	private String title;
	private String author;
	private String description;
	private String isbn;
	private byte[] image;
	private String base64Image;
	private float price;
	private Date publishDate;
	private Date lastUpdateTime;
	private Set<Review> reviews = new HashSet<Review>(0);
	private Set<OrderDetail> orderDetails = new HashSet<OrderDetail>(0);

	public Book() {
	}

	public Book(Category category, String title, String author, String description, String isbn, byte[] image,
			float price, Date publishDate, Date lastUpdateTime) {
		this.category = category;
		this.title = title;
		this.author = author;
		this.description = description;
		this.isbn = isbn;
		this.image = image;
		this.price = price;
		this.publishDate = publishDate;
		this.lastUpdateTime = lastUpdateTime;
	}

	public Book(Category category, String title, String author, String description, String isbn, byte[] image,
			float price, Date publishDate, Date lastUpdateTime, Set<Review> reviews, Set<OrderDetail> orderDetails) {
		this.category = category;
		this.title = title;
		this.author = author;
		this.description = description;
		this.isbn = isbn;
		this.image = image;
		this.price = price;
		this.publishDate = publishDate;
		this.lastUpdateTime = lastUpdateTime;
		this.reviews = reviews;
		this.orderDetails = orderDetails;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "book_id", unique = true, nullable = false)
	public Integer getBookId() {
		return this.bookId;
	}

	public void setBookId(Integer bookId) {
		this.bookId = bookId;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "category_id", nullable = false)
	public Category getCategory() {
		return this.category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	@Column(name = "title", unique = true, nullable = false, length = 128)
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "author", nullable = false, length = 64)
	public String getAuthor() {
		return this.author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	@Column(name = "description", nullable = false, length = 16777215)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "isbn", nullable = false, length = 15)
	public String getIsbn() {
		return this.isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	@Column(name = "image", nullable = false)
	public byte[] getImage() {
		return this.image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	@Column(name = "price", nullable = false, precision = 12, scale = 0)
	public float getPrice() {
		return this.price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "publish_date", nullable = false, length = 10)
	public Date getPublishDate() {
		return this.publishDate;
	}

	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "last_Update_Time", nullable = false, length = 19)
	public Date getLastUpdateTime() {
		return this.lastUpdateTime;
	}

	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "book")
	public Set<Review> getReviews() {
		return this.reviews;
	}

	public void setReviews(Set<Review> reviews) {
		this.reviews = reviews;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "book")
	public Set<OrderDetail> getOrderDetails() {
		return this.orderDetails;
	}

	public void setOrderDetails(Set<OrderDetail> orderDetails) {
		this.orderDetails = orderDetails;
	}
	
	@Transient
	public String getBase64Image() {
		this.base64Image = Base64.getEncoder().encodeToString(this.image);
		return this.base64Image;
	}
	
	@Transient
	public void setBase64Image(String base64Image) {
		this.base64Image = base64Image;
		
	}

	@Override
	public int hashCode() {
		return Objects.hash(bookId);
	}
	
	@Transient
	public float getAverageRating() {
		float averageRating = 0.0f;
		float sum = 0.0f;	
		int totalReviews = reviews.size();
		
		if (totalReviews == 0) {
			return 0.0f;
		}
		
		for (Review eachReview: reviews) {
			sum = sum + eachReview.getRating();
		}
		averageRating = sum / totalReviews;
		return averageRating;
	}
	
	@Transient
	public String getRatingStars() {
		float averageRating = getAverageRating();
		return getRatingString(averageRating);
	}
	
	@Transient
	public String getRatingString(float averageRating){
		String result = "";
		int numberOfStarsOn = (int) averageRating;
		
		for (int i =1; i<=numberOfStarsOn; i++) {
			result += "on,";
		}
		int next = numberOfStarsOn+1;
		
		if (averageRating > numberOfStarsOn) {
			result += "half,";
			next++;
		}
		
		for (int j = next; j<=5; j++) {
			result+="off,";
		}
		
		return result.substring(0,result.length()-1);
		/*
		String result = "";
		int i = 1;
		
		while(i <= averageRating) {
			result  = result + "on,";
			i++;
		}
		if (i - averageRating >0 && i - averageRating < 1 ) {
			result = result + "half";
			return result;
		}
		
		while (averageRating < 5) {
			result = result + "off,";
			averageRating++;
		}
		return result.substring(0,result.length()-1);
		*/
	}
}
