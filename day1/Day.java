package day1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;

public class Day {

    private ArrayList<Integer> list1 = new ArrayList<>();
    private ArrayList<Integer> list2 = new ArrayList<>();

    public void part1() {
        Collections.sort(list1);
        Collections.sort(list2);

        int totalDistance = 0;

        for(int i = 0; i < list1.size(); i++) {
            int distance = Math.abs(list1.get(i) - list2.get(i));
            totalDistance += distance;
        }

        System.out.println("total distance: " + totalDistance);
    }

    public void part2() {
        HashMap<Integer, Integer> list2Counts = new HashMap<>();
        int totalSimilarity = 0;

        for(Integer i : list2) {
            list2Counts.merge(i, 1, Integer::sum);
        }

        for(Integer i : list1) {
            int count = list2Counts.getOrDefault(i, 0);
            int similarity = i * count;
            totalSimilarity += similarity;
        }

        System.out.println("total similarity: " + totalSimilarity);
    }

    public void parseInput(String data) {
        Scanner scanner = new Scanner(data);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] spl = line.split("   ");
            list1.add(Integer.parseInt(spl[0]));
            list2.add(Integer.parseInt(spl[1]));
        }
        scanner.close();
    }
}
