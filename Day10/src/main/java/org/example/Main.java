package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        // Initialise bracket lists, maps, and variables
        Set<String> openingBrackets = Set.of("(", "[", "{", "<");
        Set<String> closingBrackets = Set.of(")", "]", "}", ">");
        HashMap<String, String> matchingBracket = new HashMap<>();
        matchingBracket.put(")", "(");
        matchingBracket.put("]", "[");
        matchingBracket.put("}", "{");
        matchingBracket.put(">", "<");
        HashMap<String, Integer> errorScore = new HashMap<>();
        errorScore.put(")", 3);
        errorScore.put("]", 57);
        errorScore.put("}", 1197);
        errorScore.put(">", 25137);
        HashMap<String, Integer> autocompleteScore = new HashMap<>();
        autocompleteScore.put("(", 1);
        autocompleteScore.put("[", 2);
        autocompleteScore.put("{", 3);
        autocompleteScore.put("<", 4);
        int totalErrorScore = 0;
        List<Long> autocompleteScoreList = new ArrayList<>();
        //Read file
        File input = new File("input.txt");
        Scanner inputReader = new Scanner(input);
        while (inputReader.hasNextLine()) {
            // Update variables
            String inputString = inputReader.nextLine();
            // Convert input string to array of strings
            String[] inputStrings = inputString.split("");
            // Parse line
            List<String> bracketBuffer = new ArrayList<>();
            for (var bracket : inputStrings) {
                // Add any opening brackets to bracket list buffer
                if (openingBrackets.contains(bracket)) {
                    bracketBuffer.add(bracket);
                    // If a closing bracket is found, test if it matches the last opening bracket in the buffer
                } else if (closingBrackets.contains(bracket)) {
                    // If so, remove the last opening bracket in the buffer
                    if (Objects.equals(matchingBracket.get(bracket), bracketBuffer.get(bracketBuffer.size() - 1))) {
                        bracketBuffer.remove(bracketBuffer.size() - 1);
                        // If not, determine the error score for that closing bracket and discard the corrupted line
                    } else {
                        totalErrorScore += errorScore.get(bracket);
                        bracketBuffer.clear();
                        break;
                    }
                }
            }
            // If no corruption encountered, determine the score of the pair of all opening brackets remaining
            if (bracketBuffer.size() != 0) {
                long totalLeftoverScore = 0;
                // Reverse leftover brackets list to get the order of the equivalent closing brackets
                Collections.reverse(bracketBuffer);
                for (var unclosedBracket : bracketBuffer) {
                    totalLeftoverScore = totalLeftoverScore * 5 + autocompleteScore.get(unclosedBracket);
                }
                autocompleteScoreList.add(totalLeftoverScore);
            }
        }
        inputReader.close();
        Collections.sort(autocompleteScoreList);
        long medianAutocompleteScore = autocompleteScoreList.get(autocompleteScoreList.size()/2);
        // Print solution
        System.out.println("The solution to part 1 is " + totalErrorScore);
        System.out.println("The solution to part 2 is " + medianAutocompleteScore);
    }
}