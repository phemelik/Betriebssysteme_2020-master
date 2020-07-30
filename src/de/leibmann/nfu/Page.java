package de.leibmann.nfu;

import java.util.Objects;

/**
 * @author 4674215 (Matrikelnummer)
 */
public class Page {

    private int accessed;
    private int modified;
    private final char data;
    private long birth;
    private long death;

    public Page(char data) {
        this.accessed = 0;
        this.modified = 0;
        this.data = data;
        this.birth = System.currentTimeMillis();
        this.death = -1;
    }

    /**
     * Test-only Constructor
     */
    public Page(char data, int references) {
        this.references = references;
        this.data = data;
    }

    public char getData() {
        return data;
    }

    public int getAccessed() {
        return accessed;
    }

    public int getModified() {
        return modified;
    }

    public void setAccessed() {
        this.accessed = 1;
    }

    public void setModified() {
        this.modified = 1;
    }

    /**
     * Stops Page timer and ads a replacement
     * To be used when a Page gets replaced in physical memory
     */
    public void kill() {
        this.death = System.currentTimeMillis();
        Result.addLifetime(death - birth);
        Result.addReplacement();
    }

    /**
     * To be used when a Page that has already been replaced is written into physical memory again
     */
    public void resetLifetime() {
        this.birth = System.currentTimeMillis();
        this.death = -1;
    }

    @Override
    public String toString() {
        return data + ", accessed: " + accessed + ", modified: " + modified;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Page page1 = (Page) o;
        return data == page1.data;
    }

    @Override
    public int hashCode() {
        return Objects.hash(references, data);
    }
}
