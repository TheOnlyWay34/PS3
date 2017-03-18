package pkgMain;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;

import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class XMLValidator {
	public static final String XML_FILE = "Books.xml";
	public static final String SCHEMA_FILE = "Books.xsd";

	public static void main(String[] args) {

		XMLValidator XMLValidator = new XMLValidator();
		boolean valid = XMLValidator.validate(XML_FILE, SCHEMA_FILE);

		System.out.printf("%s validation = %b.", XML_FILE, valid);
	}

	private boolean validate(String xmlFile, String schemaFile) {

		String basePathXML = new File("").getAbsolutePath();
		basePathXML = basePathXML + "\\src\\main\\resources\\XMLFiles\\" + xmlFile;
		File fileXML = new File(basePathXML);

		String basePathXSD = new File("").getAbsolutePath();
		basePathXSD = basePathXSD + "\\src\\main\\resources\\XSDFiles\\" + schemaFile;
		File fileXSD = new File(basePathXSD);

		SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		try {

			Schema schema = schemaFactory.newSchema(fileXSD);
			Validator validator = schema.newValidator();
			validator.setErrorHandler(new MyErrorHandler());
			validator.validate(new StreamSource(fileXML));
			return true;
		} catch (SAXException se) {
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	private static class MyErrorHandler extends DefaultHandler {
		public void warning(SAXParseException e) throws SAXException {
			System.out.println("Warning: ");
			printInfo(e);
			throw e;
		}

		public void error(SAXParseException e) throws SAXException {
			System.out.println("Error: ");
			printInfo(e);
			throw e;
		}

		public void fatalError(SAXParseException e) throws SAXException {
			System.out.println("Fattal error: ");
			printInfo(e);
			throw e;
		}

		private void printInfo(SAXParseException e) {
			System.out.println("   Public ID: " + e.getPublicId());
			System.out.println("   System ID: " + e.getSystemId());
			System.out.println("   Line number: " + e.getLineNumber());
			System.out.println("   Column number: " + e.getColumnNumber());
			System.out.println("   Message: " + e.getMessage());
		}
	}
}