package de.leibmann.nfu;


import java.util.ArrayList;
import java.util.List;

/**
 * @author 4674215 (Matrikelnummer)
 */
public class Executor {

    public static void main(String[] args) {
        Executor executor = new Executor();
        int dataCount = 10;
        Ram ram = new Ram(new PagingArea());
        List<Page> pageSet = executor.generateData(dataCount);
        ram.write(pageSet);

        executor.simulateRamUse(ram);

        executor.printData(ram);
        Result.showResults();
    }

    List<Page> generateData(int count) {
        List<Page> pageSet = new ArrayList<>();
        for(int i = 'a'; i <= ('a' + count - 1); i++) {
            pageSet.add(new Page((char) i));
        }
        return pageSet;
    }

    private void printData(Ram ram) {
        System.out.println("Data in RAM:");
        for(int i = 0; i < ram.pages.length; i++) {
            System.out.println(ram.pages[i].toString());
        }
        System.out.println();
        System.out.println("Data in Paging Area:");
        ram.pagingArea.pageList.forEach(System.out::println);
        System.out.println();
    }

    private void simulateRamUse(Ram ram ) {

        // Ablauf der Simulation, LOC Auskommentieren um Ergebnis zu verändern.

        ram.access('d');
        multipleAccess(ram, 'i', 2);
        multipleAccess(ram, 'a', 4);
        multipleAccess(ram, 'j', 8);
        multipleAccess(ram, 'h', 10);
        multipleAccess(ram, 'f', 3);
        multipleAccess(ram, 'g', 4);
        multipleAccess(ram, 'c', 17);
        multipleAccess(ram, 'b', 25);
        multipleAccess(ram, 'e', 71);
        multipleAccess(ram, 'd', 10);
        multipleAccess(ram, 'i', 10);
        multipleAccess(ram, 'f', 10);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        multipleAccess(ram, 'a', 10);
        multipleAccess(ram, 'j', 300);
        multipleAccess(ram, 'a', 300);
        multipleAccess(ram, 'g', 700);
        multipleAccess(ram, 'c', 70);
        multipleAccess(ram, 'h', 55);

    }

    private void multipleAccess(Ram ram, char toPeek, int count) {
        while(count >= 0) {
            ram.access(toPeek);
            count--;
        }
    }
}
