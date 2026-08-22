package com.roman.app;

import lombok.Data;

import java.util.Objects;

@Data
public class Cell {
    private CellType cellType;
    private int x;
    private int y;

    public Cell(CellType cellType, int x, int y){
        this.cellType = cellType;
        this.x = x;
        this.y = y;
    }
    public void printCellType(){
        switch (cellType) {
            case BOMB -> System.out.print("o");
            case CLEAR -> System.out.print("/");
            case PLAYER -> System.out.print("*");
            case UNKNOWN -> System.out.print(" ");
            case BASE -> System.out.print("B");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Cell cell = (Cell) o;
        return x == cell.x && y == cell.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
