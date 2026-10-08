package com.example.demo.model;

public enum Player {
    RED,
    BLUE;

    public Player next() {
        if (this == RED) {
            return BLUE;
        }
        return RED;
    }
}
