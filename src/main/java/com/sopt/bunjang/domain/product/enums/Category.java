package com.sopt.bunjang.domain.product.enums;

public enum Category {
    GLASSES("안경"),
    KIDULT("키덜트");

    private final String label;

    Category(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}