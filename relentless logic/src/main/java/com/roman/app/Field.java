package com.roman.app;

import com.roman.app.cells.Base;
import com.roman.app.cells.Bomb;
import com.roman.app.cells.Player;
import com.roman.app.cells.Unknown;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Field {
    private List<List<Cell>> matrix;
    private int rows;
    private int bombs;
    private List<Cell> badBombList;
    private List<Cell> baseBlockBombList;

    public Field(int rows, int bombs){
        this.rows = rows;
        this.bombs = bombs;
        this.matrix = new ArrayList<>();

        this.badBombList = List.of(new Cell(new Bomb(), 0, 0),
                new Cell(new Bomb(), 0, 1),
                new Cell(new Bomb(), 1, 0),
                new Cell(new Bomb(), 1, 1),
                new Cell(new Bomb(), rows - 1, rows - 1));

        this.baseBlockBombList = List.of(new Cell(new Bomb(), rows - 2, rows - 2),
                new Cell(new Bomb(), rows - 2, rows - 1),
                new Cell(new Bomb(), rows - 1, rows - 2));

        List<Cell> bombList = createBombList();

        for(int i = 0; i < rows; i ++){
            matrix.add(new ArrayList<>());

            for(int j = 0; j < rows; j++){
                if(i == 0 && j == 0)
                    matrix.get(i).add(new Cell(new Player(), i, j));
                else if( i == rows-1 && j == rows-1)
                    matrix.get(i).add(new Cell(new Base(), i, j));
                else
                    matrix.get(i).add(new Cell(new Unknown(), i, j));
            }
        }

        for(Cell bomb : bombList){
            matrix.get(bomb.getX()).set(bomb.getY(), bomb);
        }
    }

    public List<Cell> createBombList(){
        List<Cell> bombList = new ArrayList<>();
        do{
            bombList.clear();
            for(int i = 0; i < bombs; i++){
                int x = new Random().nextInt(rows);
                int y = new Random().nextInt(rows);
                bombList.add(new Cell(new Bomb(), x, y));
            }
            //цикл крутиться поки є дублікати або погані бомби або база заблокована
        }while(!isFieldBombsCorrect(bombList));
        return bombList;
    }
    private boolean isFieldBombsCorrect(List<Cell> bombList){
        return bombList.stream().distinct().count() == bombList.size()
                && isNotHasBadBombs(bombList)
                && isBaseOpen(bombList);
    }
    private boolean isNotHasBadBombs(List<Cell> bombList){
        for(Cell badBomb : badBombList){
            if(bombList.contains(badBomb))
                return false;
        }
        return true;
    }
    private boolean isBaseOpen(List<Cell> bombList){
        return !bombList.containsAll(baseBlockBombList);
    }
    public void printField(){
        char verBorder = '|';
        String header = "-".repeat(rows * 2 + 2);

        System.out.println(header);
        matrix.forEach(cellRow -> {
                    cellRow.forEach(cell -> {
                        System.out.print(verBorder);
                        cell.printCellType();
                    });

                    System.out.println(verBorder);
                });
        System.out.println(header);
    }
}
