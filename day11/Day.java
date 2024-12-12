package day11;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day {

    private ArrayList<Long> rocks;
    
    public void part1() {
        ArrayList<Long> currentRocks = new ArrayList<>(rocks);

        HashMap<Long, Long> rockCount = changeStones(currentRocks, 25);

        System.out.println("Number of rocks after 25 blinks: " + countRocks(rockCount));
    }

    public void part2() {
        ArrayList<Long> currentRocks = new ArrayList<>(rocks);

        HashMap<Long, Long> rockCount = changeStones(currentRocks, 75);

        System.out.println("Number of rocks after 75 blinks: " + countRocks(rockCount));
    }

    private HashMap<Long, Long> changeStones(ArrayList<Long> stones, int blinks) {
        HashMap<Long, Long> newRocks = new HashMap<>();

        for (Long val : stones) {
            Long count = newRocks.getOrDefault(val, 0L);
            newRocks.put(val, ++count);
        }

        for (int i = 0; i < blinks; i++) {
            HashMap<Long, Long> blinkRocks = new HashMap<>();
            for (Map.Entry<Long, Long> entry : newRocks.entrySet()) {
                StringBuilder currentString = new StringBuilder();
                Long current = entry.getKey();
                currentString.append(current);
                
                if (current == 0) {
                    blinkRocks.merge(1L, entry.getValue(), Long::sum);
                } else if (currentString.length() % 2 == 0) {
                    int middle = currentString.length() / 2;
                    String firstHalf = currentString.substring(0, middle);
                    String secondHalf = currentString.substring(middle, currentString.length());
                    Long firstH = Long.valueOf(firstHalf);
                    Long secondH = Long.valueOf(secondHalf);
    
                    blinkRocks.merge(firstH, entry.getValue(), Long::sum);
                    blinkRocks.merge(secondH, entry.getValue(), Long::sum);
                } else {
                    blinkRocks.merge(current * 2024L, entry.getValue(), Long::sum);
                }
            }
            newRocks = blinkRocks;
        }

        return newRocks;
    }

    private long countRocks(HashMap<Long, Long> rocks) {
        return rocks.values().stream().reduce(0L, Long::sum);
    }

    public void parseInput(String data) {
        String[] split = data.trim().split(" ");
        List<Long> r = Stream.of(split).map(Long::valueOf).collect(Collectors.toList());
        rocks = new ArrayList<>(r);
    }
}
