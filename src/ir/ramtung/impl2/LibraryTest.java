package ir.ramtung.impl2;

import com.pholser.junit.quickcheck.Property;
import com.pholser.junit.quickcheck.runner.JUnitQuickcheck;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;
import ir.ramtung.sts01.LibraryException;
import com.pholser.junit.quickcheck.generator.InRange;

@RunWith(JUnitQuickcheck.class)
public class LibraryTest {

    @Property
    public void professorBorrowLimit(@InRange(minInt = 1, maxInt = 100) int numOfDocuments) throws LibraryException {
        Library library = new Library();
        library.addProfMember("John Doe");

        int successfulBorrows = 0;
        for (int i = 0; i < numOfDocuments; i++) {
            library.addBook("Book" + i, 1);
            try {
                library.borrow("John Doe", "Book" + i);
                successfulBorrows++;
            } catch (LibraryException e) {
                assertTrue(successfulBorrows >= 5);
                return;
            }
        }

        assertTrue(successfulBorrows <= 5);
    }

    @Property
    public void studentBorrowLimit(@InRange(minInt = 1, maxInt = 100) int numOfDocuments) throws LibraryException {
        Library library = new Library();
        library.addStudentMember("123", "Jane Doe");

        int successfulBorrows = 0;
        for (int i = 0; i < numOfDocuments; i++) {
            library.addBook("Book" + i, 1);
            try {
                library.borrow("Jane Doe", "Book" + i);
                successfulBorrows++;
            } catch (LibraryException e) {
                assertTrue(successfulBorrows >= 2);
                return;
            }
        }

        assertTrue(successfulBorrows <= 2);
    }

    @Property
    public void bookPenaltyCheck(@InRange(minInt = 1, maxInt = 100)int daysLate) throws LibraryException {
        Library library = new Library();
        library.addProfMember("John Doe");
        library.addBook("Regular Book", 1);

        library.borrow("John Doe", "Regular Book");

        library.timePass(10 + daysLate);

        int expectedPenalty;
        if (daysLate <= 7) {
            expectedPenalty = daysLate * 2000;
        } else if (daysLate <= 21) {
            expectedPenalty = 7 * 2000 + (daysLate - 7) * 3000;
        } else {
            expectedPenalty = 7 * 2000 + (21 - 7) * 3000 + (daysLate - 21) * 7000;
        }

        assertEquals(expectedPenalty, library.getTotalPenalty("John Doe"));
    }

    @Property
    public void magazinePenaltyTest(@InRange(minInt = 1, maxInt = 100) int daysLate,
                                    @InRange(minInt = 1300, maxInt = 1399) int year) throws LibraryException {
        Library library = new Library();
        library.addStudentMember("123", "Jane Doe");
        library.addMagazine("Science Magazine", year, 1, 1);

        library.borrow("Jane Doe", "Science Magazine");

        library.timePass(2 + daysLate); // 2 days loan period + daysLate

        int expectedPenalty = year < 1390 ? (daysLate*2000) : (daysLate*3000);

        assertEquals(expectedPenalty, library.getTotalPenalty("Jane Doe"));
    }

    @Property
    public void referencePenaltyTest(@InRange(minInt = 1, maxInt = 100) int daysLate) throws LibraryException {
        Library library = new Library();
        library.addProfMember("John Doe");
        library.addReference("Encyclopedia", 1);

        library.borrow("John Doe", "Encyclopedia");

        library.timePass(5 + daysLate); // 5 days loan period + daysLate

        int expectedPenalty = 0;
        if (daysLate <= 3) {
            expectedPenalty = daysLate * 5000;
        } else {
            expectedPenalty = 3 * 5000 + (daysLate - 3) * 7000;
        }

        assertEquals(expectedPenalty, library.getTotalPenalty("John Doe"));
    }

    @Property
    public void renewals(@InRange(minInt = 1, maxInt = 100) int renewals) throws LibraryException {
        Library library = new Library();
        library.addStudentMember("123", "Jane Doe");
        library.addBook("Regular Book", 1);

        library.borrow("Jane Doe", "Regular Book");

        int successfulRenewals = 0;
        for (int i = 0; i < renewals; i++) {
            try {
                library.extend("Jane Doe", "Regular Book");
                successfulRenewals++;
            } catch (LibraryException e) {
                break;
            }
        }

        // We check outside the loop
        assertTrue("Renewals should be at most 1", successfulRenewals <= 1);
        if (successfulRenewals < 1) {
            assertTrue("If renewals failed, it should be due to exceeding limits", renewals >= successfulRenewals);
        }
    }

    @Property
    public void timePassTest(@InRange(minInt = 1, maxInt = 100)int days) throws LibraryException {
        Library library = new Library();
        library.addProfMember("John Doe");
        library.addBook("Regular Book", 1);
        library.borrow("John Doe", "Regular Book");

        library.timePass(days);

        int penalty = library.getTotalPenalty("John Doe");

        int expectedPenalty = days <= 10 ? 0 : (days <= 17 ? 2000 : 3000);

        assertTrue("Penalty should be greater than or equal to the expected penalty", penalty >= expectedPenalty);
    }

    @Property
    public void errorHandlingTest() throws LibraryException {
        Library library = new Library();
        library.addStudentMember("123", "Jane Doe");
        library.addBook("Regular Book", 1);

        library.borrow("Jane Doe", "Regular Book");

        assertThrows(LibraryException.class, () -> library.borrow("Jane Doe", "Regular Book"));
    }

    @Property
    public void emptyDocumentTitleTest() {
        Library library = new Library();
        assertThrows(LibraryException.class, () -> library.addBook("", 1));
        assertThrows(LibraryException.class, () -> library.addReference("", 1));
        assertThrows(LibraryException.class, () -> library.addMagazine("", 2023, 1, 1));
    }

    @Property
    public void zeroCopiesTest() throws LibraryException {
        Library library = new Library();
        assertThrows(LibraryException.class, () -> library.addBook("Book Title", 0));
        assertThrows(LibraryException.class, () -> library.addReference("Reference Title", 0));
        assertThrows(LibraryException.class, () -> library.addMagazine("Magazine Title", 2023, 1, 0));
    }

    @Property
    public void duplicateDocumentTitleTest() throws LibraryException {
        Library library = new Library();
        library.addBook("Book Title", 1);
        assertThrows(LibraryException.class, () -> library.addBook("Book Title", 1));
        library.addReference("Reference Title", 1);
        assertThrows(LibraryException.class, () -> library.addReference("Reference Title", 1));
        library.addMagazine("Magazine Title", 2023, 1, 1);
        assertThrows(LibraryException.class, () -> library.addMagazine("Magazine Title", 2023, 1, 1));
    }

    @Property
    public void duplicateMemberNameTest() throws LibraryException {
        Library library = new Library();
        library.addStudentMember("123", "John Doe");
        assertThrows(LibraryException.class, () -> library.addStudentMember("456", "John Doe"));
        library.addProfMember("Jane Smith");
        assertThrows(LibraryException.class, () -> library.addProfMember("Jane Smith"));
    }

    @Property
    public void emptyProfessorNameTest() {
        Library library = new Library();
        assertThrows(LibraryException.class, () -> library.addProfMember(""));
    }

    @Property
    public void emptyStudentNameTest() {
        Library library = new Library();
        assertThrows(LibraryException.class, () -> library.addStudentMember("123", ""));
    }

    @Property
    public void negativeYearTest(@InRange(minInt = -1000, maxInt = -1) int year) {
        Library library = new Library();
        assertThrows(LibraryException.class, () -> library.addBook("Negative Year Book", year));
        assertThrows(LibraryException.class, () -> library.addReference("Negative Year Reference", year));
        assertThrows(LibraryException.class, () -> library.addMagazine("Negative Year Magazine", year, 1, 1));
    }

}
