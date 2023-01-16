package ru.practicum.element.model;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.data.querydsl.QSort;

import java.util.Objects;

public class PageRequestFromElement implements Pageable {
    private final QPageRequest pageRequest;
    private final int from;

    protected PageRequestFromElement(int from, int size, QSort sort) {
        pageRequest = QPageRequest.of(from / size, size, sort);
        this.from = from;
    }

    public static PageRequestFromElement of(int from, int size, QSort sort) {
        return new PageRequestFromElement(from, size, sort);
    }

    @Override
    public int getPageNumber() {
        return pageRequest.getPageNumber();
    }

    @Override
    public int getPageSize() {
        return pageRequest.getPageSize();
    }

    @Override
    public long getOffset() {
        return from;
    }

    @Override
    public Sort getSort() {
        return pageRequest.getSort();
    }

    @Override
    public Pageable next() {
        return pageRequest.next();
    }

    @Override
    public Pageable previousOrFirst() {
        return pageRequest.previousOrFirst();
    }

    @Override
    public Pageable first() {
        return pageRequest.first();
    }

    @Override
    public Pageable withPage(int pageNumber) {
        return pageRequest.withPage(pageNumber);
    }

    @Override
    public boolean hasPrevious() {
        return pageRequest.hasPrevious();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof PageRequestFromElement)) {
            return false;
        }

        if (!super.equals(o)) {
            return false;
        }

        PageRequestFromElement that = (PageRequestFromElement) o;
        return from == that.from;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), from);
    }
}
