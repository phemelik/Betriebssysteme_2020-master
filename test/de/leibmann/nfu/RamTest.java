package de.leibmann.nfu;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author 4674215 (Matrikelnummer)
 */
class RamTest {

    private Ram ram;
    private PagingArea pagingArea;

    @BeforeEach
    void SetUp() {
        Executor executor = new Executor();
        pagingArea = new PagingArea();
        ram = new Ram(pagingArea);
        List<Page> pageSet = executor.generateData(5);
        ram.write(pageSet);
    }

    @Test
    void itAddsDataToPage() {
        assertTrue(ram.contains('a'));
        assertTrue(ram.contains('b'));
        assertTrue(ram.contains('c'));
        assertTrue(ram.contains('d'));
        assertTrue(ram.contains('e'));
    }

    @Test
    void itSortsMostReferencedToTop() {
        for (int i = 0; i <= 5; i++) {
            ram.access('d');
        }

        assertEquals('d', ram.read('d'));
    }

    @Test
    void itAddsHighReferencedToTop() {
        ram.write(new Page('x', 50));

        assertEquals('x', ram.read('d'));
    }

    @Test
    void itLocatesNFUToPagingArea() {

        ram.access('a');
        ram.access('b');
        ram.access('c');
        ram.access('d');

        // constructor adds one reference so 'x' has more references than 'e'
        ram.write(new Page('x', 1));

        assertTrue(ram.memoryContains(new Page('x')));
        assertTrue(pagingArea.contains('e'));

        ram.access('b');
        ram.access('c');
        ram.access('d');
        ram.access('x');
        ram.access('e');
        ram.access('e');

        assertTrue(ram.memoryContains(new Page('e')));
        assertTrue(pagingArea.contains('a'));
    }
}
