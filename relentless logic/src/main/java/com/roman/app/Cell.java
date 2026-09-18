package com.roman.app;

import com.roman.app.cells.*;
import com.roman.app.cells.Player;
import lombok.Data;

import java.util.Objects;

@Data
public class Cell {
    private Entity entity;
    private int x;
    private int y;

    public Cell(Entity entity, int x, int y){
        this.entity = entity;
        this.x = x;
        this.y = y;
    }
    public void printCellType(){
        if(entity instanceof Bomb) System.out.print("0");
        if(entity instanceof Clear) System.out.print("/");
        if(entity instanceof Start) System.out.print("*");
        if(entity instanceof Unknown) System.out.print(" ");
        if(entity instanceof Base) System.out.print("B");
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
