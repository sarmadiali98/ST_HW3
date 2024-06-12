package ir.ramtung.impl1;

import com.pholser.junit.quickcheck.Property;
import com.pholser.junit.quickcheck.runner.JUnitQuickcheck;
import org.junit.runner.RunWith;
import com.pholser.junit.quickcheck.generator.InRange;
import static org.junit.Assert.*;

@RunWith(JUnitQuickcheck.class)
public class LibraryTest {

    @Property
    public void professorBorrowLimit(@InRange(minInt = 1, maxInt = 100) int numOfDocuments) throws InvalidArgumentEx, DuplicateMemberEx, CannotBorrowEx, DuplicateDocumentEx {
        Library library = new Library();
        Professor professor = new Professor("John Doe");
        library.addProfMember(professor.name);

        int successfulBorrows = 0;
        for (int i = 0; i < numOfDocuments; i++) {
            library.addBook("Book" + i, 1);
            try {
                library.borrow(professor.name, "Book" + i);
                successfulBorrows++;
            } catch (CannotBorrowEx e) {
                assertTrue(successfulBorrows >= 5);
                return;
            }
        }

        assertTrue(successfulBorrows <= 5);
    }

    @Property
    public void studentBorrowLimit(@InRange(minInt = 1, maxInt = 100) int numOfDocuments) throws InvalidArgumentEx, DuplicateMemberEx, CannotBorrowEx, DuplicateDocumentEx {
        Library library = new Library();
        Student student = new Student("123", "Jane Doe");
        library.addStudentMember(student.id, student.name);

        int successfulBorrows = 0;
        for (int i = 0; i < numOfDocuments; i++) {
            library.addBook("Book" + i, 1);
            try {
                library.borrow(student.name, "Book" + i);
                successfulBorrows++;
            } catch (CannotBorrowEx e) {
                assertTrue(successfulBorrows >= 2);
                return;
            }
        }

        assertTrue(successfulBorrows <= 2);
    }

    @Property
    public void bookPenaltyCheck(@InRange(minInt = 1, maxInt = 100)int daysLate) throws InvalidArgumentEx, DuplicateMemberEx, CannotBorrowEx, DuplicateDocumentEx {
        Library library = new Library();
        Professor professor = new Professor("John Doe");
        library.addProfMember(professor.name);
        Document document = new Book("Regular Book");
        library.addBook(document.getTitle(), 1);

        library.borrow(professor.name, document.getTitle());

        library.timePass(10 + daysLate);

        int expectedPenalty;
        if (daysLate <= 7) {
            expectedPenalty = daysLate * 2000;
        } else if (daysLate <= 21) {
            expectedPenalty = 7 * 2000 + (daysLate - 7) * 3000;
        } else {
            expectedPenalty = 7 * 2000 + (21 - 7) * 3000 + (daysLate - 21) * 5000;
        }

        try {
            assertEquals(expectedPenalty, library.getTotalPenalty(professor.name));
        } catch (InvalidArgumentEx e) {
            fail("Penalty calculation failed");
        }
    }

    @Property
    public void magazinePenaltyTest(@InRange(minInt = 1, maxInt = 100) int daysLate,
                                    @InRange(minInt = 1300, maxInt = 1399) int year) throws InvalidArgumentEx, DuplicateMemberEx, CannotBorrowEx, DuplicateDocumentEx {
        Library library = new Library();
        Student student = new Student("123", "Jane Doe");
        library.addStudentMember(student.id, student.name);
        Document document = new Magazine("Science Magazine", year, 1);
        library.addMagazine(document.getTitle(), year, 1, 1);

        library.borrow(student.name, document.getTitle());

        library.timePass(2 + daysLate);

        int expectedPenalty = daysLate <= 0 ? 0 : (year < 1390 ? daysLate * 2000 : daysLate * 3000);

        try {
            assertEquals(expectedPenalty, library.getTotalPenalty(student.name));
        } catch (InvalidArgumentEx e) {
            fail("Penalty calculation failed");
        }
    }

    @Property
    public void referencePenaltyTest(@InRange(minInt = 1, maxInt = 100) int daysLate) throws InvalidArgumentEx, DuplicateMemberEx, CannotBorrowEx, DuplicateDocumentEx {
        Library library = new Library();
        Professor professor = new Professor("John Doe");
        library.addProfMember(professor.name);
        Document document = new Reference("Encyclopedia");
        library.addReference(document.getTitle(), 1);

        library.borrow(professor.name, document.getTitle());

        library.timePass(5 + daysLate);

        int expectedPenalty;
        if (daysLate <= 3) {
            expectedPenalty = 3 * 5000 + daysLate * 7000;
        } else {
            expectedPenalty = (21 - 7) * 3000 + (daysLate - 21) * 5000;
        }

        try {
            assertEquals(expectedPenalty, library.getTotalPenalty(professor.name));
        } catch (InvalidArgumentEx e) {
            fail("Penalty calculation failed");
        }
    }

    @Property
    public void renewals(@InRange(minInt = 1, maxInt = 100) int renewals) throws InvalidArgumentEx, DuplicateMemberEx, CannotBorrowEx, CannotExtendEx, DuplicateDocumentEx {
        Library library = new Library();
        Student student = new Student("123", "Jane Doe");
        library.addStudentMember(student.id, student.name);
        Document document = new Book("Regular Book");
        library.addBook(document.getTitle(), 1);

        library.borrow(student.name, document.getTitle());

        int successfulRenewals = 0;
        for (int i = 0; i < renewals; i++) {
            try {
                library.extend(student.name, document.getTitle());
                successfulRenewals++;
            } catch (CannotExtendEx e) {
                break;
            }
        }

        assertTrue("Renewals should be at most 2", successfulRenewals <= 2);
        if (successfulRenewals < 2) {
            assertTrue("If renewals failed, it should be due to exceeding limits", renewals >= successfulRenewals);
        }
    }

    @Property
    public void timePassTest(@InRange(minInt = 1, maxInt = 100)int days) throws InvalidArgumentEx {
        Library library = new Library();
        library.timePass(days);
        assertEquals(days, library.now);
    }

    @Property
    public void errorHandlingTest() throws InvalidArgumentEx, DuplicateMemberEx, CannotBorrowEx, DuplicateDocumentEx {
        Library library = new Library();
        Student student = new Student("123", "Jane Doe");
        library.addStudentMember(student.id, student.name);
        Document document = new Book("Regular Book");
        library.addBook(document.getTitle(), 1);

        library.borrow(student.name, document.getTitle());

        assertThrows(CannotBorrowEx.class, () -> library.borrow(student.name, document.getTitle()));
    }

    @Property
    public void emptyDocumentTitleTest() {
        assertThrows(InvalidArgumentEx.class, () -> new Book(""));
        assertThrows(InvalidArgumentEx.class, () -> new Reference(""));
        assertThrows(InvalidArgumentEx.class, () -> new Magazine("", 2023, 1));
    }

    @Property
    public void zeroCopiesTest() throws DuplicateDocumentEx, InvalidArgumentEx {
        Library library = new Library();
        assertThrows(InvalidArgumentEx.class, () -> library.addBook("Book Title", 0));
        assertThrows(InvalidArgumentEx.class, () -> library.addReference("Reference Title", 0));
        assertThrows(InvalidArgumentEx.class, () -> library.addMagazine("Magazine Title", 2023, 1, 0));
    }

    @Property
    public void duplicateDocumentTitleTest() throws InvalidArgumentEx, DuplicateDocumentEx {
        Library library = new Library();
        library.addBook("Book Title", 1);
        assertThrows(DuplicateDocumentEx.class, () -> library.addBook("Book Title", 1));
        library.addReference("Reference Title", 1);
        assertThrows(DuplicateDocumentEx.class, () -> library.addReference("Reference Title", 1));
        library.addMagazine("Magazine Title", 2023, 1, 1);
        assertThrows(DuplicateDocumentEx.class, () -> library.addMagazine("Magazine Title", 2023, 1, 1));
    }

    @Property
    public void duplicateMemberNameTest() throws InvalidArgumentEx, DuplicateMemberEx {
        Library library = new Library();
        library.addStudentMember("123", "John Doe");
        assertThrows(DuplicateMemberEx.class, () -> library.addStudentMember("456", "John Doe"));
        library.addProfMember("Jane Smith");
        assertThrows(DuplicateMemberEx.class, () -> library.addProfMember("Jane Smith"));
    }

    @Property
    public void emptyMemberNameTest() {
        Library library = new Library();
        assertThrows(InvalidArgumentEx.class, () -> library.addStudentMember("123", ""));
        assertThrows(InvalidArgumentEx.class, () -> library.addProfMember(""));
    }

    @Property
    public void emptyStudentIdTest() {
        Library library = new Library();
        assertThrows(InvalidArgumentEx.class, () -> library.addStudentMember("", "John Doe"));
    }

    @Property
    public void extendLoanSameDayTest() throws InvalidArgumentEx, DuplicateMemberEx, DuplicateDocumentEx, CannotBorrowEx, CannotExtendEx {
        Library library = new Library();
        library.addStudentMember("123", "John Doe");
        library.addBook("Some Book", 1);

        library.borrow("John Doe", "Some Book");

        // Try to extend the loan on the same day
        assertThrows(CannotExtendEx.class, () -> library.extend("John Doe", "Some Book"));
    }
}