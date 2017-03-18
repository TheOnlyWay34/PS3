package pkgLibrary;

public class BookException extends Exception {
	private Catalog c;
	private String bookID;
	public BookException(Catalog c, String bookID) {
		super();
		this.c = c;
		this.bookID = bookID;
	}
	public BookException(String ID){
		super();
		this.bookID = ID;
	}
	public Catalog getCat() {
		return c;
	}
	public String getBookid() {
		return bookID;
	}
}