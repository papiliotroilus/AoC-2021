package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Objects;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
//        Initialise count
        int LineCounter = 0;
        int Digit;
        int Pointer;
        ArrayList<Integer> Part1DigitCount = new ArrayList<>();
        File input = new File("input.txt");

//        Read file for part 1
        Scanner Part1Reader = new Scanner(input);
        while (Part1Reader.hasNextLine()) {
            String CurrentLine = Part1Reader.nextLine();
            int CurrentLineLength = CurrentLine.length();
            if (!Objects.equals(CurrentLine, " ")) {
//            Count "1" digits for each position
                for (Pointer = 0; Pointer < CurrentLineLength; Pointer++) {
                    Digit = Integer.parseInt(CurrentLine.substring(Pointer, Pointer + 1));
                    try {
                        Part1DigitCount.set(Pointer, Part1DigitCount.get(Pointer) + Digit);
                    } catch (Exception e) {
                        Part1DigitCount.add(0);
                        Part1DigitCount.set(Pointer, Part1DigitCount.get(Pointer) + Digit);
                    }
                }
                LineCounter += 1;
            }
        }
        Part1Reader.close();

//        Read File and add to O2 list
        Scanner O2Reader = new Scanner(input);
        ArrayList<String> O2Strings = new ArrayList<>();
        while (O2Reader.hasNextLine()) {
            O2Strings.add(O2Reader.nextLine());
        }
//        Determine first O2 digit
        ArrayList<Integer> O2Digits = new ArrayList<>();
        if (Part1DigitCount.get(0) > LineCounter / 2) {
            O2Digits.add(1);
        } else {
            O2Digits.add(0);
        }
//        Determine O2 string
        for (Pointer = 0; Pointer < Part1DigitCount.size() - 1; Pointer++) {
            int O2StringsCounter = 0;
            int O2DigitCount = 0;
            while ((O2Strings.size() > 1) & (O2StringsCounter < O2Strings.size())) {
//            Compare digit of string with known digit
                if (Integer.parseInt(O2Strings.get(O2StringsCounter).substring(Pointer, Pointer + 1)) == O2Digits.get(Pointer)) {
                    O2DigitCount += Integer.parseInt(O2Strings.get(O2StringsCounter).substring(Pointer + 1, Pointer + 2));
                    O2StringsCounter += 1;
                }
                else {
                    O2Strings.remove(O2StringsCounter);
                }
            }
//            Add dominant digit to list of known digits
            if (O2DigitCount * 2 > O2Strings.size()) {
                O2Digits.add(1);
            }
            if (O2DigitCount * 2 < O2Strings.size()) {
                O2Digits.add(0);
            }
            if (O2DigitCount * 2 == O2Strings.size()) {
                O2Digits.add(1);
            }
        }
        O2Reader.close();
//        Determine decrypted value of O2
        int O2;
        if (O2Strings.size() == 1) {
            String CO2String = O2Strings.stream().map(Object::toString).collect(Collectors.joining(""));
            O2 = Integer.parseInt(CO2String, 2);
        }
        else {
            String EpsilonString = O2Digits.stream().map(Object::toString).collect(Collectors.joining(""));
            O2 = Integer.parseInt(EpsilonString, 2);
        }

//        Read File and add to CO2 list
        Scanner CO2Reader = new Scanner(input);
        ArrayList<String> CO2Strings = new ArrayList<>();
        while (CO2Reader.hasNextLine()) {
            CO2Strings.add(CO2Reader.nextLine());
        }
//        Determine first CO2 digit
        ArrayList<Integer> CO2Digits = new ArrayList<>();
        if (Part1DigitCount.get(0) > LineCounter / 2) {
            CO2Digits.add(0);
        } else {
            CO2Digits.add(1);
        }
//        Determine CO2 string
        for (Pointer = 0; Pointer < Part1DigitCount.size() - 1; Pointer++) {
            int O2StringsCounter = 0;
            int O2DigitCount = 0;
            while ((CO2Strings.size() > 1) & O2StringsCounter < CO2Strings.size()) {
//            Compare pointed digit of string with known digit
                if (Integer.parseInt(CO2Strings.get(O2StringsCounter).substring(Pointer, Pointer + 1)) == CO2Digits.get(Pointer)) {
                    O2DigitCount += Integer.parseInt(CO2Strings.get(O2StringsCounter).substring(Pointer + 1, Pointer + 2));
                    O2StringsCounter += 1;
                }
                else {
                    CO2Strings.remove(O2StringsCounter);
                }
            }
//            Add dominant digit to list of known digits
            if (O2DigitCount * 2 > CO2Strings.size()) {
                CO2Digits.add(0);
            }
            if (O2DigitCount * 2 < CO2Strings.size()) {
                CO2Digits.add(1);
            }
            if (O2DigitCount * 2 == CO2Strings.size()) {
                CO2Digits.add(0);
            }
        }
        CO2Reader.close();
//        Determine decrypted value of CO2
        int CO2;
        if (CO2Strings.size() == 1) {
            String CO2String = CO2Strings.stream().map(Object::toString).collect(Collectors.joining(""));
            CO2 = Integer.parseInt(CO2String, 2);
        }
        else {
            String EpsilonString = CO2Digits.stream().map(Object::toString).collect(Collectors.joining(""));
            CO2 = Integer.parseInt(EpsilonString, 2);
        }

//        Determine binary number from count
        ArrayList<Integer> GammaList = new ArrayList<>();
        ArrayList<Integer> EpsilonList = new ArrayList<>();
        for (Pointer = 0; Pointer < Part1DigitCount.size(); Pointer++) {
            if (Part1DigitCount.get(Pointer) < LineCounter / 2) {
                GammaList.add(0);
                EpsilonList.add(1);
            } else {
                GammaList.add(1);
                EpsilonList.add(0);
            }
        }
//        Determine Gamma and Epsilon
        String GammaString = GammaList.stream().map(Object::toString).collect(Collectors.joining(""));
        int Gamma = Integer.parseInt(GammaString, 2);
        String EpsilonString = EpsilonList.stream().map(Object::toString).collect(Collectors.joining(""));
        int Epsilon = Integer.parseInt(EpsilonString, 2);

//        Print solution
        System.out.println("The solution to part 1 is " + Gamma * Epsilon);
        System.out.println("The solution to part 2 is " + O2 * CO2);
    }
}