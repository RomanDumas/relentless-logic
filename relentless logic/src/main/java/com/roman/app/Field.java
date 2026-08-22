package com.roman.app;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Field {
    private List<List<Cell>> field;
    private int rows;
    private int bombs;
    private List<Cell> badBombList;

    public Field(int rows, int bombs){
        this.rows = rows;
        this.bombs = bombs;
        this.field = new ArrayList<>();

        this.badBombList = List.of(new Cell(CellType.BOMB, 0, 0),
                new Cell(CellType.BOMB, 0, 1),
                new Cell(CellType.BOMB, 1, 0),
                new Cell(CellType.BOMB, 1, 1),
                new Cell(CellType.BOMB, rows - 1, rows - 1));

        List<Cell> bombList = createBombList();

        for(int i = 0; i < rows; i ++){
            field.add(new ArrayList<>());

            for(int j = 0; j < rows; j++){
                if(i == 0 && j == 0)
                    field.get(i).add(new Cell(CellType.PLAYER, i, j));
                else if( i == rows-1 && j == rows-1)
                    field.get(i).add(new Cell(CellType.BASE, i, j));
                else
                    field.get(i).add(new Cell(CellType.UNKNOWN, i, j));
            }
        }

        for(Cell bomb : bombList){
            field.get(bomb.getX()).set(bomb.getY(), bomb);
        }
    }

    public List<Cell> createBombList(){
        List<Cell> bombList = new ArrayList<>();
        do{
            bombList.clear();
            for(int i = 0; i < bombs; i++){
                int x = new Random().nextInt(rows);
                int y = new Random().nextInt(rows);
                bombList.add(new Cell(CellType.BOMB, x, y));
            }
            //цикл крутиться поки є дублікати або погані бомби
        }while(bombList.stream().distinct().count() != bombList.size() && !isNotHasBadBombs(bombList));
        return bombList;
    }
    private boolean isNotHasBadBombs(List<Cell> bombList){
        for(Cell badBomb : badBombList){
            if(bombList.contains(badBomb))
                return false;
        }
        return true;
    }
    public void printField(){
        char verBorder = '|';
        char horBorder = '-';
        String header = "-".repeat(rows * 2 + 2);

        System.out.println(header);
        field.forEach(cellRow -> {
                    cellRow.forEach(cell -> {
                        System.out.print(verBorder);
                        cell.printCellType();
                    });

                    System.out.println(verBorder);
                });
        System.out.println(header);
    }
}
