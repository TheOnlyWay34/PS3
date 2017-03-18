package pkgLibrary;

import java.io.File;
import java.util.ArrayList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.*;
import pkgLibrary.Book;

import javax.xml.bind.annotation.*;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)

public class Catalog {

	@XmlAttribute
	int id;

	@XmlElement(name = "book")
	ArrayList<Book> books;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public ArrayList<Book> getBooks() {
		return books;
	}

	public void setBooks(ArrayList<Book> books) {
		this.books = books;
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

	public void AddBook(String ID, Book book) {
		try {
			Catalog c = ReadXMLFile();
			ArrayList<Book> alist = c.getBooks();
			for (Book bk : c.getBooks())
				if (bk.getId() == ID)
					throw new BookException(ID);
			alist.add(book);
			c.setBooks(alist);
			WriteXMLFile(c);
		} catch (BookException e) {
			System.out.println("Book" + ID + " already exists.");
		}
	}

}
