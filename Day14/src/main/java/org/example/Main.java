package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        // Read file
        File input = new File("input.txt");
        Scanner inputReader = new Scanner(input);
        // Process template string to list of element strings
        String template = inputReader.nextLine();
        String[] templateArray = Arrays.stream(template.split(""))
                .toArray(String[]::new);
        List<String> initialPolymer = new ArrayList<>();
        Collections.addAll(initialPolymer, templateArray);
        // Process rule strings to rule map entries, create buffer of each rule for part 2
        Map<String, String> rulesMap = new HashMap<>();
        Map<String, Long> frequencyMapBuffer = new HashMap<>();
        while (inputReader.hasNextLine()) {
            String inputString = inputReader.nextLine();
            if (!inputString.isBlank()) {
                String[] inputStrings = inputString.split(" -> ");
                rulesMap.put(inputStrings[0], inputStrings[1]);
                frequencyMapBuffer.put(inputStrings[0], 0L);
            }
        }
        inputReader.close();

        // Naive solution for part 1
        // Modify initial polymer for given number of cycles
        List<String> currentPolymer = new ArrayList<>(initialPolymer);
        for (int cycle = 1; cycle <= 10; cycle++) {
            List<String> polymerBuffer = new ArrayList<>(currentPolymer);
            int indexOffset = 1;
            for (int polymerIndex = 0; polymerIndex < currentPolymer.size() - 1; polymerIndex++) {
                String elementPair = currentPolymer.get(polymerIndex) + currentPolymer.get(polymerIndex + 1);
                if (rulesMap.containsKey(elementPair)) {
                    polymerBuffer.add(polymerIndex + indexOffset, rulesMap.get(elementPair));
                    indexOffset += 1;
                }
            }
            currentPolymer.clear();
            currentPolymer = polymerBuffer;
        }
        // Count most and least common element and calculate their difference for part 1
        Map.Entry<String, Long> mostCommonElement1 = currentPolymer.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .get();
        Map.Entry<String, Long> leastCommonElement1 = currentPolymer.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet()
                .stream()
                .min(Map.Entry.comparingByValue())
                .get();
        int part1 = (int) (mostCommonElement1.getValue() - leastCommonElement1.getValue());

        // Scalable solution for part 2
        // Map each rule to a list of which new pairs each initial pair splits into by adding the element in the middle
        Map<String, String[]> rulesSplit = new HashMap<>();
        for (var ruleStart : rulesMap.keySet()) {
            String firstSplit = ruleStart.charAt(0) + rulesMap.get(ruleStart);
            String secondSplit = rulesMap.get(ruleStart) + ruleStart.charAt(1);
            if (rulesMap.containsKey(firstSplit) & rulesMap.containsKey(secondSplit)) {
                String[] splitArray = {firstSplit, secondSplit};
                rulesSplit.put(ruleStart, splitArray);
            } else if (rulesMap.containsKey(firstSplit) & !rulesMap.containsKey(secondSplit)){
                String[] splitArray = {firstSplit};
                rulesSplit.put(ruleStart, splitArray);
            } else if (!rulesMap.containsKey(firstSplit) & rulesMap.containsKey(secondSplit)){
                String[] splitArray = {secondSplit};
                rulesSplit.put(ruleStart, splitArray);
            }
        }
        // Initialise the pair frequency map from the buffer and count pair frequencies found in initial polymer
        Map<String, Long> frequencyMap = new HashMap<>();
        for (var bufferPair : frequencyMapBuffer.keySet()) {
            frequencyMap.put(bufferPair, frequencyMapBuffer.get(bufferPair));
            frequencyMapBuffer.put(bufferPair, 0L);
        }
        for (int polymerIndex = 0; polymerIndex < initialPolymer.size() - 1; polymerIndex++) {
            String elementPair = String.valueOf(initialPolymer.get(polymerIndex)) + initialPolymer.get(polymerIndex + 1);
            if (rulesMap.containsKey(elementPair)) {
                frequencyMap.put(elementPair, frequencyMap.get(elementPair) + 1);
            }
        }
        // Initialise the element frequency map from the rules map and count element frequencies found in initial polymer
        Map<String, Long> elementFrequency = new HashMap<>();
        for (var rule : rulesMap.keySet()) {
            if (!elementFrequency.containsKey(rulesMap.get(rule))) {
                elementFrequency.put(rulesMap.get(rule), 0L);
            }
        }
        for (var element : initialPolymer) {
            elementFrequency.put(element, elementFrequency.get(element) + 1);
        }
        // For given number of cycles, copy number of each rule pair into number of split pairs using buffer and increment element count
        for (int cycle = 1; cycle <= 40; cycle++) {
            for (var currentPair : frequencyMap.keySet()) {
                if (frequencyMap.get(currentPair) != 0) {
                    for (var splitPair : rulesSplit.get(currentPair)) {
                        frequencyMapBuffer.put(splitPair, frequencyMapBuffer.get(splitPair) + frequencyMap.get(currentPair));
                    }
                    elementFrequency.put(rulesMap.get(currentPair), elementFrequency.get(rulesMap.get(currentPair)) + frequencyMap.get(currentPair));
                }
            }
            for (var bufferPair : frequencyMapBuffer.keySet()) {
                frequencyMap.put(bufferPair, frequencyMapBuffer.get(bufferPair));
                frequencyMapBuffer.put(bufferPair, 0L);
            }
        }
        // Count most and least common element and calculate their difference for part 2
        Map.Entry<String, Long> mostCommonElement2 = elementFrequency
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .get();
        Map.Entry<String, Long> leastCommonElement2 = elementFrequency
                .entrySet()
                .stream()
                .min(Map.Entry.comparingByValue())
                .get();
        long part2 = mostCommonElement2.getValue() - leastCommonElement2.getValue();

        // Print solution
        System.out.println("The solution to part 1 is " + part1);
        System.out.println("The solution to part 2 is " + part2);
    }
}