package day19;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Day {
    //Used AOC subreddit for direction 

    private Set<String> availableTowels;
    private Set<String> desiredDesigns = new HashSet<>();
    private HashMap<String, Long> patternTimes = new HashMap<>();

    public void part1() {
        int totalDesigns = 0;

        for (String design : desiredDesigns) {
            if (countWaysToReach(design, availableTowels) > 0) {
                ++totalDesigns;
            }
        }
        
        System.out.println("Total Designs: " + totalDesigns);
    }

    public void part2() {
        long totalDesigns = 0;

        for (String design : desiredDesigns) {
            totalDesigns += countWaysToReach(design, availableTowels);
        }
        
        System.out.println("Total Designs Ways: " + totalDesigns);
    }

    private long countWaysToReach(String design, Set<String> availablePatterns) {
        if (design.equals("")) return 1L;
        if (!patternTimes.containsKey(design)) {
            long count = availablePatterns.stream().filter(design::startsWith).map(e -> countWaysToReach(design.substring(e.length()), availablePatterns)).reduce(Long::sum).orElse(0L);
            patternTimes.put(design, count);
            return count;
        }
        return patternTimes.get(design);
    }

    public void parseInput(String data) {
        Scanner scanner = new Scanner(data);

        String line = scanner.nextLine();
        String[] available = line.split(", ");
        availableTowels = Set.of(available);

        while (scanner.hasNextLine()) {
            line = scanner.nextLine();
            if (!line.isEmpty())
                desiredDesigns.add(line);
        }

        scanner.close();
    }
    
}
