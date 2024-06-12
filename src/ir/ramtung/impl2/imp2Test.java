package ir.ramtung.impl2;
import static org.junit.Assert.*;
import org.junit.Test;
import ir.ramtung.sts01.LibraryException;

import org.junit.Test;
import static org.junit.Assert.*;

import ir.ramtung.impl2.Document;
import ir.ramtung.impl2.Book;
import ir.ramtung.impl2.Magazine;
import ir.ramtung.impl2.Student;
import ir.ramtung.impl2.Prof;
import ir.ramtung.sts01.LibraryException;
import org.junit.Test;
import static org.junit.Assert.*;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

import ir.ramtung.impl2.Book;
import ir.ramtung.impl2.Magazine;
import ir.ramtung.impl2.Reference;
import org.junit.Test;
import static org.junit.Assert.*;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Arrays;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import ir.ramtung.sts01.LibraryException;

public class imp2Test {

	@Test
	public void getCopiesTest() {
	    Document magazine = new Magazine("Magazine Title",1838,1, 7);
	    assertEquals(7, magazine.getCopies());
	}

	@Test
	public void calculatePenaltyBookTest() {
	    Book book = new Book("Test Book", 1);
	    int penalty = book.calculatePenalty(10);
	    assertEquals(2000, penalty);
	}

	@Test
	public void returnBookTest() {
	    Document magazine = new Magazine("Magazine Title",1838,1, 7);
	    magazine.barrowBook();
	    magazine.returnBook();
	    assertEquals(7, magazine.getCopies());
	}

	@Test(expected = IllegalArgumentException.class)
	public void constructorNegativeCopiesTest() {
	    new Book("Impossible Book", -1);
	}

	@Test
	public void getTitleTest() {
	    Document book = new Book("Book Title", 4);
	    assertEquals("Book Title", book.getTitle());
	}

	@Test
	public void constructorTest() {
		Document book = new Book("Software Engineering", 3);
		assertEquals("Software Engineering", book.getTitle());
		assertEquals(3, book.getCopies());
		Document magazine = new Magazine("Nature", 1838, 1, 7);
		assertEquals("Nature", magazine.getTitle());
		assertEquals(7, magazine.getCopies());
		Document reference = new Reference("Encyclopedia", 2);
		assertEquals("Encyclopedia", reference.getTitle());
		assertEquals(2, reference.getCopies());
	}


	@Test
	public void constructorZeroCopiesTest() {
	    Document book = new Book("Zero Copies Book", 0);
	    assertEquals(0, book.getCopies());
	}

	@Test
	public void getDayDefaultTest() {
	    Document book = new Book("Intro to Java", 8);
	    assertEquals(10, book.getDay());
	}

	@Test
	public void barrowBookTest() {
	    Document book = new Book("Book Title", 8);
	    book.barrowBook();
	    assertEquals(7, book.getCopies());
	}

	@Test
	public void BookConstructor_InitialValuesSetCorrectlyTest() {
	    String expectedTitle = "Some Book Title";
	    int expectedCopies = 3;
	    Book book = new Book(expectedTitle, expectedCopies);
	    assertEquals(expectedTitle, book.getTitle());
	    assertEquals(expectedCopies, book.getCopies());
	    assertEquals(10, book.getDay());
	}

	@Test
	public void calculatePenalty_DayPenaltyLessThanEqualTo21MoreThan7Test() {
	    Book book = new Book("Title", 1);
	    int dayPenalty = 14;
	    int expectedPenalty = 3000;
	    int actualPenalty = book.calculatePenalty(dayPenalty);
	    assertEquals(expectedPenalty, actualPenalty);
	}

	@Test
	public void calculatePenalty_DayPenaltyLessThanEqualTo7Test() {
	    Book book = new Book("Title", 1);
	    int dayPenalty = 7;
	    int expectedPenalty = 2000;
	    int actualPenalty = book.calculatePenalty(dayPenalty);
	    assertEquals(expectedPenalty, actualPenalty);
	}

	@Test
	public void calculatePenalty_DayPenaltyMoreThan21Test() {
	    Book book = new Book("Title", 1);
	    int dayPenalty = 22;
	    int expectedPenalty = 2000;
	    int actualPenalty = book.calculatePenalty(dayPenalty);
	    assertEquals(expectedPenalty, actualPenalty);
	}

	@Test
	public void constructorValidInputTest() {
	    Reference reference = new Reference("Test Title", 3);
	    assertEquals("Test Title", reference.getTitle());
	    assertEquals(3, reference.getCopies());
	    assertEquals(5, reference.getDay());
	}

	@Test
	public void calculatePenaltyWhenDayPenaltyGreaterThanThreeTest() {
	    Reference reference = new Reference("Test Title", 3);
	    int penalty = reference.calculatePenalty(-4);
	    assertEquals(7000, penalty);
	}

	@Test
	public void calculatePenaltyWhenDayPenaltyIsThreeTest() {
	    Reference reference = new Reference("Test Title", 3);
	    int penalty = reference.calculatePenalty(-3);
	    assertEquals(5000, penalty);
	}

	@Test
	public void calculatePenaltyWhenDayPenaltyLessThanEqualThreeTest() {
	    Reference reference = new Reference("Test Title", 3);
	    int penalty = reference.calculatePenalty(-2);
	    assertEquals(5000, penalty);
	}

	@Test
	public void constructor_ValidData_SuccessfulCreationTest() {
	    Magazine magazine = new Magazine("Test Magazine", 1400, 1, 5);
	    assertEquals("Magazine title mismatch", "Test Magazine", magazine.getTitle());
	}

	@Test
	public void calculatePenalty_PostOrEqual1390Year_Returns3000Test() {
	    Magazine newMagazine = new Magazine("New Magazine", 1390, 1, 3);
	    int penalty = newMagazine.calculatePenalty(10);
	    assertEquals("Penalty for post- or in 1390 magazine should be 3000", 3000, penalty);
	}

	@Test
	public void calculatePenalty_Pre1390Year_Returns2000Test() {
	    Magazine oldMagazine = new Magazine("Old Magazine", 1389, 1, 3);
	    int penalty = oldMagazine.calculatePenalty(10);
	    assertEquals("Penalty for pre-1390 magazine should be 2000", 2000, penalty);
	}

	@Test
	public void borrowValidDocumentTest() {
	    Student student = new Student("John Doe", "444");
	    Document doc = new Book("Java Basics", 1);
	    student.borrow(doc);
	    assertEquals("Document should be borrowed", 1, student.docsBorrow.size());
	}

	@Test(expected = LibraryException.class)
	public void extendDocumentAlreadyExtendedTest() throws LibraryException {
	    Student student = new Student("John Doe", "888");
	    Document doc = new Book("Java Basics", 1);
	    student.borrow(doc);
	    student.extend("Java Basics");
	    student.extend("Java Basics");
	}

	@Test
	public void returnDocValidTest() {
	    Student student = new Student("John Doe", "999");
	    Document doc = new Book("Java Basics", 1);
	    student.borrow(doc);
	    student.returnDoc("Java Basics");
	    assertTrue("Document should be returned", student.docsBorrow.isEmpty());
	}

	@Test
	public void getNameValidTest() {
	    Prof prof = new Prof("Jane Doe");
	    assertEquals("Jane Doe", prof.getName());
	}

	@Test
	public void extendValidDocumentTest() throws LibraryException {
	    Student student = new Student("John Doe", "123");
	    Document doc = new Book("Java Basics", 1);
	    student.borrow(doc);
	    student.extend("Java Basics");
	    assertEquals("Extension should be successful", false, student.canExtend.get(0));
	}

	@Test
	public void getPenaltyNoPenaltyTest() {
	    Student student = new Student("John Doe", "333");
	    assertEquals("No penalty should exist.", 0, student.getPenalty());
	}

	@Test
	public void timePassPenaltyAccruedTest() {
	    Prof prof = new Prof("Jane Doe");
	    Document doc = new Magazine("Science Today", 2, -3, 999);
	    prof.borrow(doc);
	    prof.timePass();
	    assertTrue("Penalty should have been accrued", prof.getPenalty() > 0);
	}

	@Test
	public void profConstructorTest() {
	    Prof prof = new Prof("Dr. Smith");
	    assertNotNull("Constructor must initialize the prof object", prof);
	}

	@Test(expected = LibraryException.class)
	public void addStudentMemberStudentNameEmptyTest() throws LibraryException {
	    Library library = new Library();
	    library.addStudentMember("123", "");
	}

	@Test(expected = LibraryException.class)
	public void timePassNegativeDaysTest() throws LibraryException {
	    Library library = new Library();
	    library.timePass(-1);
	}

	@Test
	public void addStudentMemberSuccessTest() throws LibraryException {
	    Library library = new Library();
	    library.addStudentMember("123", "John Doe");
	}

	@Test
	public void addBookSuccessTest() throws LibraryException {
	    Library library = new Library();
	    library.addBook("Effective Java", 3);
	}

	@Test
	public void addProfMemberSuccessTest() throws LibraryException {
	    Library library = new Library();
	    library.addProfMember("Prof. Smith");
	}

	@Test(expected = LibraryException.class)
	public void addStudentMemberStudentIdEmptyTest() throws LibraryException {
	    Library library = new Library();
	    library.addStudentMember("", "John Doe");
	}

	@Test(expected = LibraryException.class)
	public void addProfMemberNameEmptyTest() throws LibraryException {
	    Library library = new Library();
	    library.addProfMember(" ");
	}

	@Test(expected = LibraryException.class)
	public void addReferenceReferenceTitleEmptyTest() throws LibraryException {
	    Library library = new Library();
	    library.addReference(" ", 5);
	}

	@Test
	public void borrowDocumentNotAvailableTest() throws LibraryException {
	    Library library = new Library();
	    library.addBook("Effective Java", 0);
	    try {
	        library.borrow("Prof. Smith", "Effective Java");
	        fail("LibraryException expected due to no available copies");
	    } catch (LibraryException e) {
	        assertEquals("this book doesnt exist", e.getMessage());
	    }
	}

	@Test(expected = LibraryException.class)
	public void addBookBookNameEmptyTest() throws LibraryException {
	    Library library = new Library();
	    library.addBook(" ", 5);
	}
}