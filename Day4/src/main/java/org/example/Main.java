package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
//        Read file
        File input = new File("input.txt");
        Scanner InputReader = new Scanner(input);
//        Add first row to call number list
        String CallNumbersString = InputReader.nextLine();
        List<Integer> CallNumbersList = new ArrayList<>();
        for (String CallNumber : CallNumbersString.split(",")) {
            CallNumbersList.add(Integer.parseInt(CallNumber));
        }
//        Initialise bingo card lists
        List<BingoBoard> BingoBoardList = new ArrayList<>();
        List<String> RowList = new ArrayList<>();
//        Skip row between call number and bingo cards
        InputReader.nextLine();
        while (InputReader.hasNextLine()) {
//            Convert bingo card row to numbers list
            String RowString = InputReader.nextLine();
            if (!Objects.equals(RowString, "")) {
                RowList.add(RowString);
            }
            else {
//                Add column to card list then reinitialise row list
                BingoBoardList.add(new BingoBoard(RowList));
                RowList.clear();
            }
        }
        InputReader.close();
//        Add column to card list one last time
        BingoBoardList.add(new BingoBoard(RowList));
//        Iterate through  bingo card list
        List<Integer> WinList = new ArrayList<>();
        List<BingoBoard> DoneBoardList = new ArrayList<>();
        for (var CallNumber : CallNumbersList) {
            for (var BingoBoard : BingoBoardList) {
//                Register wins and finished boards
                if (BingoBoard.BingoCheck(CallNumber) > -1) {
                    WinList.add(BingoBoard.BingoCheck(CallNumber));
                    DoneBoardList.add(BingoBoard);
                }
            }
//            Remove finished boards from play before next iteration
            for (var DoneBoard : DoneBoardList) {
                BingoBoardList.removeAll(DoneBoardList);
            }
        }
//        Print solution
        System.out.println("The solution to part 1 is " + WinList.get(0));
        System.out.println("The solution to part 2 is " + WinList.get(WinList.size()-1));
    }
}