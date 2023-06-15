package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;



public class Main {
//    Function for converting strings to set of characters
//    because Java doesn't have one built-in
//    for some reason
    public static Set<Character> strToSet(String str) {
        Set<Character> set;

        if (str == null)
            return new HashSet<>();
        set = new HashSet<>(str.length());
        for (int i = 0; i < str.length(); i++)
            set.add(str.charAt(i));
        return set;
    }
    public static void main(String[] args) throws FileNotFoundException {
//        Initialise variables
        Set<Character> candidatesCorF = new HashSet<>();
        Set<Character> candidatesBorD = new HashSet<>();
        char segmentA, segmentB, segmentC, segmentD, segmentE, segmentF, segmentG;
        segmentA = segmentB = segmentC = segmentD = segmentE = segmentF = segmentG = 0;
        List<Integer> outputDecoded = new ArrayList<>();
//        Read file
        File input = new File("input.txt");
        Scanner inputReader = new Scanner(input);
        while (inputReader.hasNextLine()) {
            String inputString = inputReader.nextLine();
//            Split input string to two halves
            String[] halves = inputString.split(" \\| ");
            List<Set<Character>> signalPatterns = Arrays.stream(halves[0].split(" "))
                    .map(Main::strToSet)
                    .toList();
            List<Set<Character>> outputValue = Arrays.stream(halves[1].split(" "))
                    .map(Main::strToSet)
                    .toList();
//            Determine candidates for segments C or F from digit 1
            for (var pattern : signalPatterns) {
                if (pattern.size() == 2) {
                    candidatesCorF = pattern;
                    break;
                }
            }
//            Determine segment A by subtracting digit 1 from 7
            for (var pattern : signalPatterns) {
                if (pattern.size() == 3) {
                    pattern.removeAll(candidatesCorF);
                    segmentA = pattern.iterator().next();
                    break;
                }
            }
//            Determine candidates for segments B or D by subtracting digit 1 from 4
            for (var pattern : signalPatterns) {
                if (pattern.size() == 4) {
                    candidatesBorD = new HashSet<>(pattern);
                    candidatesBorD.removeAll(candidatesCorF);
                    break;
                }
            }
//            Determine segments G, D, and B from digit 3
            for (var pattern : signalPatterns) {
                if (pattern.size() == 5 & pattern.containsAll(candidatesCorF)) {
//                    Determine segment G
                    HashSet<Character> candidatesG = new HashSet<>(pattern);
                    candidatesG.removeAll(candidatesCorF);
                    candidatesG.removeAll(candidatesBorD);
                    candidatesG.remove(segmentA);
                    segmentG = candidatesG.iterator().next();
//                    Determine segment D
                    HashSet<Character> candidatesD = new HashSet<>(pattern);
                    candidatesD.removeAll(candidatesCorF);
                    candidatesD.remove(segmentA);
                    candidatesD.remove(segmentG);
                    segmentD = candidatesD.iterator().next();
//                    Determine segment B
                    HashSet<Character> candidatesB = new HashSet<>(candidatesBorD);
                    candidatesB.remove(segmentD);
                    segmentB = candidatesB.iterator().next();
                }
            }
//            Determine segments F and C from digit 5
            for (var pattern : signalPatterns) {
                if (pattern.size() == 5 & pattern.containsAll(candidatesBorD) & pattern.contains(segmentA) & pattern.contains(segmentG)) {
//                    Determine segment F
                    HashSet<Character> candidatesF = new HashSet<>(pattern);
                    candidatesF.removeAll(candidatesBorD);
                    candidatesF.remove(segmentA);
                    candidatesF.remove(segmentG);
                    segmentF = candidatesF.iterator().next();
//                    Determine segment C
                    HashSet<Character> candidatesC = new HashSet<>(candidatesCorF);
                    candidatesC.remove(segmentF);
                    segmentC = candidatesC.iterator().next();
                }
            }
//            Determine segment E from digit 8
            for (var pattern : signalPatterns) {
                if (pattern.size() == 7) {
//                    Determine segment F
                    HashSet<Character> candidatesE = new HashSet<>(pattern);
                    candidatesE.removeAll(candidatesBorD);
                    candidatesE.removeAll(candidatesCorF);
                    candidatesE.remove(segmentA);
                    candidatesE.remove(segmentG);
                    segmentE = candidatesE.iterator().next();
                }
            }
//            Generate segment lists for each digit
            List<Set<Character>> digits = List.of(
                    Set.of(segmentA, segmentB, segmentC, segmentE, segmentF, segmentG),
                    Set.of(segmentC, segmentF),
                    Set.of(segmentA, segmentC, segmentD, segmentE, segmentG),
                    Set.of(segmentA, segmentC, segmentD, segmentF, segmentG),
                    Set.of(segmentB, segmentC, segmentD, segmentF),
                    Set.of(segmentA, segmentB, segmentD, segmentF, segmentG),
                    Set.of(segmentA, segmentB, segmentD, segmentE, segmentF, segmentG),
                    Set.of(segmentA, segmentC, segmentF),
                    Set.of(segmentA, segmentB, segmentC, segmentD, segmentE, segmentF, segmentG),
                    Set.of(segmentA, segmentB, segmentC, segmentD, segmentF, segmentG)
            );
//            Identify each output value and add to list of decoded values
            for (var value : outputValue) {
                for (int i = 0; i < 10; i++){
                    if (value.equals(digits.get(i))) {
                        outputDecoded.add(i);
                    }
                }
            }
        }
        inputReader.close();
//        Determine how many 1, 4, 7, and 8 digits there are for part 1
        List<Integer> part1list = List.of(1, 4, 7, 8);
        int part1counter = 0;
        for (var output : outputDecoded) {
            if (part1list.contains(output)) {
                part1counter += 1;
            }
        }
//        Turn output list into string list
        String[] outputStrings = outputDecoded.toString()
                .replaceAll("[\\[\\]]|,| ", "")
                .split("(?<=\\G.{4})");
//        Turn string list to int list
        int[] outputInts = Arrays.stream(outputStrings)
                .mapToInt(Integer::parseInt)
                .toArray();
//        Determine sum of int list for part 2
        int part2sum = Arrays.stream(outputInts).sum();
//        Print solution
        System.out.println("The solution to part 1 is " + part1counter);
        System.out.println("The solution to part 2 is " + part2sum);
    }
}