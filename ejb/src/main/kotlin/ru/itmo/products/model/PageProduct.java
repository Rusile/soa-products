package ru.itmo.products.model;

import jakarta.json.bind.annotation.JsonbCreator;
import jakarta.json.bind.annotation.JsonbProperty;

import java.util.List;

public class PageProduct {
    @JsonbProperty("content")
    private final List<Product> content;

    @JsonbProperty("pageable")
    private final Pageable pageable;

    @JsonbProperty("totalPages")
    private final int totalPages;

    @JsonbProperty("totalElements")
    private final int totalElements;

    @JsonbCreator
    public PageProduct(List<Product> content, Pageable pageable, int totalPages, int totalElements) {
        this.content = content;
        this.pageable = pageable;
        this.totalPages = totalPages;
        this.totalElements = totalElements;
    }

    public List<Product> getContent() {
        return content;
    }

    public Pageable getPageable() {
        return pageable;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public int getTotalElements() {
        return totalElements;
    }

    @Override
    public String toString() {
        return "PageProduct{" +
                "content=" + content +
                ", pageable=" + pageable +
                ", totalPages=" + totalPages +
                ", totalElements=" + totalElements +
                '}';
    }
}