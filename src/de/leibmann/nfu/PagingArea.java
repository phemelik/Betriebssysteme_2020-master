package de.leibmann.nfu;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 4674215 (Matrikelnummer)
 */
public class PagingArea {

    List<Page> pageList;

    public PagingArea() {
        this.pageList = new ArrayList<>();
    }

    public void add(Page page) {
        pageList.add(page);
    }

    public Page get(Page page) {
        return pageList.get(pageList.indexOf(page));
    }

    public boolean contains(char c) {
        return pageList.contains(new Page(c));
    }

}