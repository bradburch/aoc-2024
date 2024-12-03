package day2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Day {

    private ArrayList<List<Integer>> reports = new ArrayList<>();

    public void part1() {
        int safe = 0;

        for (List<Integer> report : reports) {
            if (isSafe(report))
                ++safe;
        }

        System.out.println("Safe: " + safe);
    }

    // Brute force for now. Might change later.
    public void part2() {
        int safe = 0;

        for (List<Integer> report : reports) {
            if (!isSafe(report)) {
                for(int i = 0; i < report.size(); i++) {
                    ArrayList<Integer> noIndex = new ArrayList<>(report);
                    noIndex.remove(i);
                    if (isSafe(noIndex)) {
                        ++safe;
                        break;
                    }
                }
            } else {
                ++safe;
            }
        }

        System.out.println("Safe: " + safe);
    }

    // Verbose for debugging
    private boolean isSafe(List<Integer> report) {
        boolean increasing = report.get(0) < report.get(1);
        for(int i = 1; i < report.size(); i++) {
            if (report.get(i) < report.get(i-1) && increasing) {
                return false;
            } else if (report.get(i) > report.get(i-1) && !increasing) {
                return false;
            }

            int diff = Math.abs(report.get(i) - report.get(i-1));

            if (diff < 1 || diff > 3) {
                return false;
            }
        }

        return true;
    }

    public void parseInput(String data) {
        Scanner scanner = new Scanner(data);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] sp = line.split(" ");
            List<Integer> report = Arrays.stream(sp)
                                        .map(Integer::valueOf)
                                        .collect(Collectors.toList());

            reports.add(report);
        }
        scanner.close();
    }
}
