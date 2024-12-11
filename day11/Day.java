package day11;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day {

    private ArrayList<Long> rocks;
    
    public void part1() {
        ArrayList<Long> currentRocks = new ArrayList<>(rocks);

        for (int blink = 0; blink < 25; blink++) {
            currentRocks = new ArrayList<>(changeStones(currentRocks));
        }

        System.out.println("Number of rocks: " + currentRocks.size());
    }

    private ArrayList<Long> changeStones(ArrayList<Long> prevRocks) {
        ArrayList<Long> newRocks = new ArrayList<>();

        for(int i = 0; i < prevRocks.size(); i++) {
            Long current = prevRocks.get(i);
            StringBuilder currentString = new StringBuilder();
            currentString.append(current);

            if (current == 0) {
                newRocks.add(1L);
            } else if (currentString.length() % 2 == 0) {
                int middle = currentString.length() / 2;
                String firstHalf = currentString.substring(0, middle);
                String secondHalf = currentString.substring(middle, currentString.length());
                Long firstH = Long.valueOf(firstHalf);
                Long secondH = Long.valueOf(secondHalf);

                newRocks.add(firstH);
                newRocks.add(secondH);
            } else {
                newRocks.add(current * 2024L);
            }
        }

        return newRocks;
    }

    public void part2() {

    }

    public void parseInput(String data) {
        String[] split = data.trim().split(" ");
        List<Long> r = Stream.of(split).map(Long::valueOf).collect(Collectors.toList());
        rocks = new ArrayList<>(r);
    }
}
