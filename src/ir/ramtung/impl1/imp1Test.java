package ir.ramtung.impl1;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Arrays;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import ir.ramtung.sts01.LibraryException;

public class imp1Test {

	@Test
	public void constructorMessageTest() {
	    String expectedMessage = "Duplicate member found";
	    DuplicateMemberEx exception = new DuplicateMemberEx(expectedMessage);
	    assertNotNull("The DuplicateMemberEx instance should not be null", exception);
	    assertEquals("The message passed to the exception is not as expected", expectedMessage, exception.getMessage());
	}

	@Test
	public void constructorEmptyMessageTest() {
	    DuplicateMemberEx exception = new DuplicateMemberEx("");
	    assertEquals("The message should be empty if an empty string is passed to constructor", "", exception.getMessage());
	}

	@Test
	public void constructorNullMessageTest() {
	    DuplicateMemberEx exception = new DuplicateMemberEx(null);
	    assertNull("The message should be null if null is passed to constructor", exception.getMessage());
	}

	@Test
	public void constructor_MessageIsProperlySetTest() {
	    String expectedMessage = "Duplicate document detected.";
	    DuplicateDocumentEx exception = new DuplicateDocumentEx(expectedMessage);
	    assertEquals("Message should match the one set through constructor.", expectedMessage, exception.getMessage());
	}

	@Test
	public void constructor_CheckInheritanceTest() {
	    String message = "Inheritance test message.";
	    DuplicateDocumentEx exception = new DuplicateDocumentEx(message);
	    assertTrue("DuplicateDocumentEx should be an instance of LibraryException.", exception instanceof LibraryException);
	}

	@Test
	public void constructor_nullMessageTest() {
	    String expectedMessage = null;
	    CannotExtendEx exception = new CannotExtendEx(expectedMessage);
	    assertEquals("Message should be null when provided null input", expectedMessage, exception.getMessage());
	}

	@Test
	public void constructor_validMessageTest() {
	    String expectedMessage = "This is a test message";
	    CannotExtendEx exception = new CannotExtendEx(expectedMessage);
	    assertEquals("Message should match the input message", expectedMessage, exception.getMessage());
	}

	@Test
    public void constructor_emptyMessageTest() {
        String expectedMessage = "";
        CannotExtendEx exception = new CannotExtendEx(expectedMessage);
        assertEquals("Message should be empty when provided empty input", expectedMessage, exception.getMessage());
    }

	@Test
	public void constructor_EmptyMessageTest() {
	    String expectedMessage = "";
	    InvalidArgumentEx exception = new InvalidArgumentEx(expectedMessage);
	    assertEquals(expectedMessage, exception.getMessage());
	}

	@Test
	public void constructor_NullMessageTest() {
	    String expectedMessage = null;
	    InvalidArgumentEx exception = new InvalidArgumentEx(expectedMessage);
	    assertEquals(expectedMessage, exception.getMessage());
	}

	@Test
	public void constructor_MessageIsProperlyAssignedTest() {
	    String expectedMessage = "Test message";
	    InvalidArgumentEx exception = new InvalidArgumentEx(expectedMessage);
	    assertEquals(expectedMessage, exception.getMessage());
	}

	@Test
	public void cannotBorrowEx_ConstructorWithEmptyMessageTest() {
	    String testMessage = "";
	    CannotBorrowEx exception = new CannotBorrowEx(testMessage);
	    assertEquals("Testing that an empty message can be passed to CannotBorrowEx", testMessage, exception.getMessage());
	}

	@Test
	public void cannotBorrowEx_ConstructorWithNullMessageTest() {
	    CannotBorrowEx exception = new CannotBorrowEx(null);
	    assertEquals("Testing that passing null message is handled", null, exception.getMessage());
	}

    // Stub class to test the abstract Document class
    private static class DocumentStub extends Document {
        DocumentStub(String title) throws InvalidArgumentEx {
            super(title);
        }
        @Override
        int loanDuration() {
            return 0;
        }
        @Override
        int penaltyFor(int days) {
            return 0;
        }
    }

	@Test(expected = InvalidArgumentEx.class)
	public void constructor_EmptyTitleTest() throws InvalidArgumentEx {
	    new DocumentStub("");
	}

	@Test
	public void constructor_ValidTitleTest() throws InvalidArgumentEx {
	    String title = "Valid Title";
	    Document doc = new DocumentStub(title);
	    assertEquals(title, doc.getTitle());
	}

	@Test
	public void getTitle_CorrectTitleTest() throws InvalidArgumentEx {
	    String title = "Correct Title";
	    Document doc = new DocumentStub(title);
	    assertEquals(title, doc.getTitle());
	}

	@Test(expected = InvalidArgumentEx.class)
	public void constructor_NullTitleThrowsInvalidArgumentExTest() throws InvalidArgumentEx {
	    new Reference(null);
	}

	@Test
	public void loanDuration_AlwaysReturnsFiveTest() throws InvalidArgumentEx {
	    Reference ref = new Reference("Sample");
	    int expectedDuration = 5;
	    assertEquals("Loan duration should always return 5.", expectedDuration, ref.loanDuration());
	}

	@Test
	public void constructor_ValidTitleSetCorrectlyTest() throws InvalidArgumentEx {
	    String expectedTitle = "Valid Title";
	    Reference ref = new Reference(expectedTitle);
	    assertEquals("Title should match constructor argument.", expectedTitle, ref.getTitle());
	}

	@Test
	public void penaltyFor_DaysLessThanOrEqualToThreeTest() throws InvalidArgumentEx {
	    Reference ref = new Reference("Sample");
	    int days = 3;
	    int expectedPenalty = 3 * 5000 + days * 7000;
	    assertEquals("Penalty calculation for days <= 3 is incorrect.", expectedPenalty, ref.penaltyFor(days));
	}

	@Test
	public void penaltyFor_DaysGreaterThanTwentyOneTest() throws InvalidArgumentEx {
	    Reference ref = new Reference("Sample");
	    int days = 22;
	    int expectedPenalty = (21 - 7) * 3000 + (days - 21) * 5000;
	    assertEquals("Penalty calculation for days > 21 is incorrect.", expectedPenalty, ref.penaltyFor(days));
	}

	@Test
	public void constructorValidInputTest() throws InvalidArgumentEx {
	    Magazine magazine = new Magazine("Magazine Title", 2023, 1);
	    assertNotNull(magazine);
	}

	@Test
	public void penaltyForPre1390Test() throws InvalidArgumentEx {
	    Magazine magazine = new Magazine("Magazine Title", 1300, 1);
	    assertEquals(6000, magazine.penaltyFor(3));
	}

	@Test
	public void loanDurationTest() throws InvalidArgumentEx {
	    Magazine magazine = new Magazine("Magazine Title", 2023, 1);
	    assertEquals(2, magazine.loanDuration());
	}


	@Test
	public void penaltyForPost1390Test() throws InvalidArgumentEx {
	    Magazine magazine = new Magazine("Magazine Title", 1400, 1);
	    assertEquals(9000, magazine.penaltyFor(3));
	}

	@Test(expected = InvalidArgumentEx.class)
	public void constructorNegativeNumberTest() throws InvalidArgumentEx {
	    new Magazine("Magazine Title", 2023, -1);
	}

	@Test(expected = InvalidArgumentEx.class)
	public void constructorZeroYearTest() throws InvalidArgumentEx {
	    new Magazine("Magazine Title", 0, 1);
	}

    private class TestMember extends Member {
        public TestMember(String name) throws InvalidArgumentEx {
            super(name);
        }

        @Override
        int allowedToBorrow() {
            return 0;
        }
    }

	@Test
	public void getPrevPenaltyReturnsCorrectPenaltyTest() throws InvalidArgumentEx {
	    Member member = new TestMember("John Doe");
	    member.penalize(20);
	    assertEquals(20, member.getPrevPenalty());
	}

	@Test
	public void isNamedTrueForMatchingNameTest() throws InvalidArgumentEx {
	    Member member = new TestMember("John Doe");
	    assertTrue(member.isNamed("John Doe"));
	}

	@Test
	public void isNamedFalseForNonMatchingNameTest() throws InvalidArgumentEx {
	    Member member = new TestMember("John Doe");
	    assertFalse(member.isNamed("Jane Doe"));
	}

	@Test
	public void penalizeAccumulatesPenaltyTest() throws InvalidArgumentEx {
	    Member member = new TestMember("John Doe");
	    member.penalize(10);
	    assertEquals(10, member.getPrevPenalty());
	    member.penalize(5);
	    assertEquals(15, member.getPrevPenalty());
	}

	@Test(expected = InvalidArgumentEx.class)
	public void constructorEmptyNameThrowsExceptionTest() throws InvalidArgumentEx {
	    new TestMember("");
	}

	@Test
	public void constructorValidNameMemberCreatedTest() throws InvalidArgumentEx {
	    Member member = new TestMember("John Doe");
	    assertEquals("John Doe", member.name);
	}

	@Test
	public void constructor_ValidParameters_SuccessTest() throws InvalidArgumentEx {
	    Student student = new Student("123", "John Doe");
	    assertNotNull(student);
	    assertEquals("123", student.id);
	}

	@Test
	public void allowedToBorrow_Always_ReturnsTwoTest() throws InvalidArgumentEx {
	    Student student = new Student("123", "John Doe");
	    int allowed = student.allowedToBorrow();
	    assertEquals(2, allowed);
	}

	@Test(expected = InvalidArgumentEx.class)
	public void constructor_EmptyID_ThrowsInvalidArgumentExTest() throws InvalidArgumentEx {
	    new Student("", "John Doe");
	}

	@Test
	public void LoanExtendSameDayTest() throws InvalidArgumentEx, CannotExtendEx {
	    Member mem = new TestMember("Test1");
	    Book doc = new Book("Title");
	    Loan loan = new Loan(mem, doc, 1);
	    loan.extend(1);
	}

	@Test
	public void LoanNullMemberTest() throws InvalidArgumentEx {
	    Document doc = new Book("Title");
	    Loan loan = new Loan(null, doc, 1);
	}

	@Test
	public void isByMemberTest() throws InvalidArgumentEx {
		Member mem1 = new TestMember("Test1");
	    Member mem2 = new TestMember("Test1");
	    Document doc = new Book("Title");
	    Loan loan = new Loan(mem1, doc, 1);
	    assertTrue(loan.isBy(mem1));
	    assertFalse(loan.isBy(mem2));
	}

	@Test
	public void LoanNullDocumentTest() throws InvalidArgumentEx {
	    Member mem = new TestMember("Test1");
	    Loan loan = new Loan(mem, null, 1);
	}

	@Test
	public void returnLateLoanTest() throws InvalidArgumentEx {
	    Member mem = new TestMember("Test1");
	    Document doc = new Book("Title");
	    Loan loan = new Loan(mem, doc, 1);
	    loan.return_(3);
	    assertEquals(2, mem.getPrevPenalty());
	}

	@Test
	public void returnOnTimeLoanTest() throws InvalidArgumentEx {
	    Member mem = new TestMember("Test1");
	    Document doc = new Book("Title");
	    Loan loan = new Loan(mem, doc, 1);
	    loan.return_(2);
	    assertEquals(0, mem.getPrevPenalty());
	}

	@Test
	public void isForDocumentTest() throws InvalidArgumentEx {
	    Member mem = new TestMember("Test1");
	    Document doc1 = new Book("Title1");
	    Document doc2 = new Book("Title2");
	    Loan loan = new Loan(mem, doc1, 1);
	    assertTrue(loan.isFor(doc1));
	    assertFalse(loan.isFor(doc2));
	}

	@Test
	public void getPenaltyTest() throws InvalidArgumentEx {
	    Member mem = new TestMember("Test1");
	    Document doc = new Book("Title");
	    Loan loan = new Loan(mem, doc, 1);
	    assertEquals(2, loan.getPenalty(3));
	    assertEquals(0, loan.getPenalty(2));
	}

	@Test
	public void availableTitlesTest() throws Exception {
	    Library library = new Library();
	    library.addBook("Book1", 1);
	    library.addBook("Book2", 0);
	    assertEquals(Arrays.asList("Book1"), library.availableTitles());
	}

	@Test(expected = DuplicateDocumentEx.class)
	public void addBookDuplicateTest() throws Exception {
	    Library library = new Library();
	    library.addBook("Book1", 10);
	    library.addBook("Book1", 10);
	}

	@Test
	public void extendValidTest() throws Exception {
	    Library library = new Library();
	    library.addStudentMember("123", "Albert");
	    library.addBook("Book1", 1);
	    library.borrow("Albert", "Book1");
	    library.extend("Albert", "Book1");
	}

	@Test(expected = DuplicateMemberEx.class)
	public void addProfMemberDuplicateTest() throws Exception {
	    Library library = new Library();
	    library.addProfMember("Professor X");
	    library.addProfMember("Professor X");
	}

	@Test
	public void addStudentMemberValidTest() throws Exception {
	    Library library = new Library();
	    library.addStudentMember("123", "Albert");
	    assertEquals(1, library.members.size());
	}

	@Test
	public void addProfMemberValidTest() throws Exception {
	    Library library = new Library();
	    library.addProfMember("Professor X");
	    assertEquals(1, library.members.size());
	}

	@Test(expected = InvalidArgumentEx.class)
	public void borrowInvalidTest() throws Exception {
	    Library library = new Library();
	    library.borrow("Albert", "Book1");
	}

	@Test
	public void addBookValidTest() throws Exception {
	    Library library = new Library();
	    library.addBook("Book1", 10);
	    assertEquals(1, library.documents.size());
	}

	@Test
	public void borrowValidTest() throws Exception {
	    Library library = new Library();
	    library.addStudentMember("123", "Albert");
	    library.addBook("Book1", 10);
	    library.borrow("Albert", "Book1");
	    assertEquals(1, library.loans.size());
	}

	@Test
	public void timePassValidTest() throws Exception {
	    Library library = new Library();
	    library.timePass(5);
	    assertEquals(5, library.now);
	}

	@Test(expected = InvalidArgumentEx.class)
	public void timePassNegativeTest() throws Exception {
	    Library library = new Library();
	    library.timePass(-5);
	}

	@Test(expected = InvalidArgumentEx.class)
	public void returnDocumentInvalidTest() throws Exception {
	    Library library = new Library();
	    library.returnDocument("Albert", "Book1");
	}

	@Test(expected = InvalidArgumentEx.class)
	public void extendInvalidTest() throws Exception {
	    Library library = new Library();
	    library.extend("Albert", "Book1");
	}

	@Test
	public void returnDocumentValidTest() throws Exception {
	    Library library = new Library();
	    library.addStudentMember("123", "Albert");
	    library.addBook("Book1", 1);
	    library.borrow("Albert", "Book1");
	    library.returnDocument("Albert", "Book1");
	    assertEquals(0, library.loans.size());
	}

	@Test(expected = DuplicateMemberEx.class)
	public void addStudentMemberDuplicateTest() throws Exception {
	    Library library = new Library();
	    library.addStudentMember("123", "Albert");
	    library.addStudentMember("123", "Albert");
	}
}