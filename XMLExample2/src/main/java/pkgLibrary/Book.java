package pkgLibrary;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import pkgMain.XMLReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

public class Book {

	private String id;
	private String author;
	private String title;
	private String genre;
	private double price;
	private Date publish_date;
	private String description;
	private double Cost;

	public Book() {

	}

	public Book(String id, String author, String title, String genre, double price, Date publish_date,
			String description, double Cost) {
		super();
		this.id = id;
		this.author = author;
		this.title = title;
		this.genre = genre;
		this.price = price;
		this.publish_date = publish_date;
		this.description = description;
		this.Cost = Cost;
	}

	public Book(Catalog cat, String id) {

	}

	public String getId() {
		return id;
	}

	@XmlAttribute
	public void setId(String id) {
		this.id = id;
	}

	public String getAuthor() {
		return author;
	}

	@XmlElement
	public void setAuthor(String author) {
		this.author = author;
	}

	public String getTitle() {
		return title;
	}

	@XmlElement
	public void setTitle(String title) {
		this.title = title;
	}

	public String getGenre() {
		return genre;
	}

	@XmlElement
	public void setGenre(String genre) {
		this.genre = genre;
	}

	public double getPrice() {
		return price;
	}

	@XmlElement
	public void setPrice(double price) {
		this.price = price;
	}

	public Date getPublish_date() {
		return publish_date;
	}

	@XmlElement
	public void setPublish_date(Date publish_date) {
		this.publish_date = publish_date;
	}

	public String getDescription() {
		return description;
	}

	@XmlElement
	public void setDescription(String description) {
		this.description = description;
	}

	public double getCost() {
		return Cost;
	}

	public void setCost(double cost) {
		Cost = cost;
	}

	public static Book GetBook(String bookID) throws BookException {
		try {
			Catalog c = ReadXMLFile();
			for (Book b : c.getBooks()) {
				if (b.getId() == bookID) {
					return b;
				}
			}
			System.out.println("cat ID " + c.getId());
			System.out.println("Book count: " + c.getBooks().size());
			throw new BookException(c, bookID);

		} catch (BookException be) {
			throw be;
		} catch (Exception e) {
			throw e;
		}
	}

	private static Catalog ReadXMLFile() {

		Catalog c = null;

		String basePath = new File("").getAbsolutePath();
		basePath = basePath + "\\src\\main\\resources\\XMLFiles\\Books.xml";
		File file = new File(basePath);

		System.out.println(file.getAbsolutePath());
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(Catalog.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			c = (Catalog) jaxbUnmarshaller.unmarshal(file);
			System.out.println(c);
		} catch (JAXBException e) {
			e.printStackTrace();
		}

		return c;

	}

	private static void WriteXMLFile(Catalog c) {
		try {

			String basePath = new File("").getAbsolutePath();
			basePath = basePath + "\\src\\main\\resources\\XMLFiles\\Books.xml";
			File file = new File(basePath);

			JAXBContext jaxbContext = JAXBContext.newInstance(Catalog.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

			// output pretty printed
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			jaxbMarshaller.marshal(c, file);
			jaxbMarshaller.marshal(c, System.out);

		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}

	public void AddBook(String ID, Book b) {
		try {
			Catalog c = ReadXMLFile();
			ArrayList<Book> alist = c.getBooks();
			for (Book bk : c.getBooks())
				if (bk.getId() == ID)
					throw new BookException(ID);
			alist.add(b);
			c.setBooks(alist);
			WriteXMLFile(c);
		} catch (BookException e) {
			System.out.println("Book" + ID + " already exists.");
		}
	}
}
