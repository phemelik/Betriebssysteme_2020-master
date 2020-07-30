package de.leibmann.nfu;

import java.util.Arrays;
import java.util.List;

/**
 * @author 4674215 (Matrikelnummer)
 */
public class Ram {

    public Page[] pages; // Physical memory with set size (Config)
    public PagingArea pagingArea; // Paging Area (Hard drive)

    public Ram(PagingArea pagingArea) {
        this.pagingArea = pagingArea;
        this.pages = new Page[Config.getRamSize()];
    }

    /**
     * @param page Page to write to Memory
     */
    public void write(Page page) {
        for(int i = 0; i < pages.length - 1; i++) {
            if(pages[i] == null) {
                pages[i] = page;
                return;
            }
        }
        Page toReplace = getNotRecentlyUsed();
        pagingArea.add(toReplace);
        replace(toReplace, page);
    }

    /**
     * Writes a list of pages to the memory
     * @param pageList List of pages to be added to memory
     */
    public void write(List<Page> pageList) {
        int i = 0;
        while(i < pages.length && pages[i] == null) {
            pages[i] = pageList.get(i);
            i++;
        }
        if(pageList.size() > pages.length) {
            for (int j = i; j < pageList.size(); j++) {
                pagingArea.add(pageList.get(j));
            }
        }
    }

    /**
     * Reads a Page from memory
     * @param c char of page to be read
     * @return Page requested
     */
    public Page read(char c) {
        Page page = new Page(c);
        for(Page p : pages) {
            if (page.equals(p)) {
                p.setAccessed();
                return p;
            }
        }
        /*
        for(Page paPage : pagingArea.pageList) {
            if (page.equals(paPage)) {
                Page toReplace = getNotRecentlyUsed();
                if (toReplace.getReferences() < paPage.getReferences()) {
                    pagingArea.add(toReplace);
                    paPage.addReference();
                    replace(toReplace, paPage);
                    return paPage;
                }
            }
        }
        return null;
         */
    }

    /**
     * Access Page without returning it
     * @param c char of page to be accessed
     */
    public void access(char c) {
        for (Page page : pages) {
            if (page.getData() == c) {
                page.setModified();
            }
        }
        /*
        if(pagingArea.contains(c)) {
            pagingArea.get(new Page(c)).addReference();
            Page leastReferenced = getNotRecentlyUsed();
            if(leastReferenced.getReferences() < pagingArea.get(new Page(c)).getReferences()) {
                pagingArea.add(leastReferenced);
                replace(leastReferenced, pagingArea.get(new Page(c)));
            }
        }

         */
    }

    /**
     * Gets the least frequently referenced Page from physical memory
     * @return Page with the lowest reference counter
     */
    public Page getNotRecentlyUsed() {
        Page notRecentlyUsed = pages[0];
        for(Page page : pages) {
            if (page.getAccessed == 0 && page.getModified == 0) {
                notRecentlyUsed = page;
                return notRecentlyUsed;
            }
        }
        for(Page page : pages) {
            if (page.getAccessed == 0 && page.getModified == 1) {
                notRecentlyUsed = page;
                return notRecentlyUsed;
            }
        }
        for(Page page : pages) {
            if (page.getAccessed == 1 && page.getModified == 0) {
                notRecentlyUsed = page;
                return notRecentlyUsed;
            }
        }
        for(Page page : pages) {
            if (page.getAccessed == 1 && page.getModified == 1) {
                notRecentlyUsed = page;
                return notRecentlyUsed;
            }
        }
    }

    /**
     * Replaces a page in physical ram and saves it to PagingArea
     * @param toReplace Page to be replaced
     * @param replaceWith Page to replace page with
     */
    private void replace(Page toReplace, Page replaceWith) {
        for(int i = 0; i < pages.length; i++) {
            if(pages[i].equals(toReplace)) {
                pages[i] = replaceWith;
                replaceWith.resetLifetime();
                pagingArea.pageList.remove(replaceWith);
                toReplace.kill();
            }
        }
    }

    /**
     * Checks if physical memory contains a page with parameter char
     * INFO: Does not raise reference counter for modelling reasons
     * @param c char ram is searched for
     * @return boolean whether physical memory contains a page with the character as data
     */
    public boolean contains(char c) {
        if (memoryContains(new Page(c))) {
            return true;
        }
        return pagingArea.contains(c);
    }

    /**
     * Like contains() -> Page as parameter instead of char
     * @param page Page ram is searched for
     * @return boolean whether physical memory contains parameter page
     */
    public boolean memoryContains(Page page) {
        for(Page p : pages) {
            if(page.equals(p)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "Page{" +
                "slots=" + Arrays.toString(pages) +
                '}';
    }
}