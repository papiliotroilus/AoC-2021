package org.example;

import java.util.Arrays;
import java.util.List;

public class BingoBoard {
//    Bingo board contents 2D array
    public int [][] BoardContent;
//    Function for converting tree of integers to bingo board
    public BingoBoard(List<String> RowList) {
        BoardContent = new int [5][5];
        for (int RowPosition = 0; RowPosition < 5; RowPosition++) {
//            Trim leading spaces and split row into number strings by spaces
            String[] RowNumbers = RowList.get(RowPosition)
                    .trim()
                    .split(" +");
//            Convert number strings to integers and add to array
            int[] RowNumbersIntegers = Arrays.stream(RowNumbers)
                    .mapToInt(Integer::parseInt)
                    .toArray();
//            Copy array to bingo board
            System.arraycopy(RowNumbersIntegers, 0, BoardContent[RowPosition], 0, 5);
        }
    }
//    Function for checking bingo board against call numbers
    public int BingoCheck(int CallNumber) {
//        Replace matching numbers in bingo board with -1
        for (int Row = 0; Row < 5; Row++) {
            for (int Column = 0; Column < 5; Column++) {
                if (BoardContent[Row][Column] == CallNumber) {
                    BoardContent[Row][Column] = -1;
                }
            }
        }
//        Calculate BoardSum
        int BoardSum = 0;
        for (int Row = 0; Row < 5; Row++) {
            for (int Column = 0; Column < 5; Column++) {
                if (BoardContent[Row][Column] != -1) {
                    BoardSum += BoardContent[Row][Column];
                }
            }
        }
//        Check each row for bingo and return solution if found
        for (int Row = 0; Row < 5; Row++) {
            int RowMax = -1;
            for (int Column = 0; Column < 5; Column++) {
                if (BoardContent[Row][Column] > RowMax) {
                    RowMax = BoardContent[Row][Column];
                }
            }
            if (RowMax == -1) {
                return (CallNumber * BoardSum);
            }
        }
//        Check each row for bingo and return solution if found
        for (int Column = 0; Column < 5; Column++) {
            int ColumnMax = -1;
            for (int Row = 0; Row < 5; Row++) {
                if (BoardContent[Row][Column] > ColumnMax) {
                    ColumnMax = BoardContent[Row][Column];
                }
            }
            if (ColumnMax == -1) {
                return (CallNumber * BoardSum);
            }
        }
//        Return -1 if bingo not found
        return -1;
    }
}