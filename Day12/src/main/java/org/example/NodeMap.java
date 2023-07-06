package org.example;

import java.util.*;

public class NodeMap {
    // Map contents
    private List<List<String>> nodeLinks = new ArrayList<>(); // List of lists of links for each node
    private HashMap<String, Integer> nodeIndex = new HashMap<>(); // Hashmap of cave name strings to indices
    private int indexIterator = 0; // Iterator for indices within the node links list
    public Set<String> part1pathSet = new HashSet<>(); // Set of paths as strings for part 1
    public Set<String> part2pathSet = new HashSet<>(); // Set of paths as strings for part 2
    public List<String> currentPath = new ArrayList<>(); // Current path as list of strings
    private String extraNode = "available";

    // Constructor method
    public NodeMap() {
    }

    // Method for charting nodes
    public void chart(List<String> nodeLink) {
        String origin = nodeLink.get(0);
        String destination = nodeLink.get(1);
        // Make sure that destination is not start
        if (!destination.equals("start")) {
            // Test if origin key is not already present in node index
            if (!nodeIndex.containsKey(origin)) { // If not:
                nodeLinks.add(new ArrayList<>()); // Add new sublist for origin links
                nodeIndex.put(origin, indexIterator); // Create new index for origin from iterator
                indexIterator += 1; // Increment iterator by one for next new origin
            }
            // Test if origin key is not the end node
            if (!origin.equals("end")) {
                // If not, add destination to link sublist of index corresponding to origin
                nodeLinks.get(nodeIndex.get(origin)).add(destination);
            }
        }
    }

    // Method for printing all node links
    public void printLinks() {
        for (var node : nodeIndex.keySet()) {
            System.out.println(node + nodeLinks.get(nodeIndex.get(node)));
        }
    }

    // Method for building path set by recursively walking through all node links (for part 1)
    public void nodeWalk1(String currentNode) {
        currentPath.add(currentNode);
        for (var linkedNode : nodeLinks.get(nodeIndex.get(currentNode))) {
            // If linked node is single-use, test if it's already been used, and if so skip it
            if(linkedNode.equals(linkedNode.toLowerCase())) {
                if (currentPath.contains(linkedNode)) {
                    continue;
                }
            }
            // If valid linked node is found, walk through it next
            nodeWalk1(linkedNode);
        }
        // If the end was found, stringify path and add to set of paths, otherwise discard path as dead end
        if (currentPath.get(currentPath.size()-1).equals("end")) {
            part1pathSet.add(currentPath.toString());
        }
        // Backtrack one step then keep iterating
        currentPath.remove(currentPath.size()-1);
    }

    // Method for building path set by recursively walking through all node links with one extra node (for part 2)
    public void nodeWalk2(String currentNode) {
        currentPath.add(currentNode);
        for (var linkedNode : nodeLinks.get(nodeIndex.get(currentNode))) {
            if(linkedNode.equals(linkedNode.toLowerCase())) {
                if (currentPath.contains(linkedNode)) {
                    // If the extra node wasn't taken yet, set it as the current linked node and keep going
                    if (extraNode.equals("available")) {
                        extraNode = linkedNode;
                    // If the extra node was taken by another node or expired, skip
                    } else {
                        continue;
                    }
                }
            }
            // If valid linked node is found, walk through it next
            nodeWalk2(linkedNode);
        }
        // If the end was found, stringify path and add to set of paths, otherwise discard path as dead end
        if (currentPath.get(currentPath.size()-1).equals("end")) {
            part2pathSet.add(currentPath.toString());
        }
        // If the last step used the extra node, rollback the state of the extra node
        if (extraNode.equals(currentPath.get(currentPath.size()-1))) {
            extraNode = "available";
        }
        // Backtrack one step then keep iterating
        currentPath.remove(currentPath.size()-1);
    }

    // Method for printing all paths
    public void printPaths() {
        for (var path : part1pathSet) {
            System.out.println(path);
        }
    }
}
