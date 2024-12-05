package day5;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class Day {

    private HashMap<Integer, Set<Integer>> orderingRules = new HashMap<>();
    private ArrayList<ArrayList<Integer>> printOrders = new ArrayList<>();

    public void part1() {
        ArrayList<Integer> valid = new ArrayList<>();

        for (ArrayList<Integer> printOrder : printOrders) {
            boolean invalid = false;
            for (int i = 0; i < printOrder.size(); i++) {
                Set<Integer> pagesBefore = orderingRules.getOrDefault(printOrder.get(i), new HashSet<>());
                List<Integer> pagesAfter = printOrder.subList(i, printOrder.size());

                for (int j = 0; j < pagesAfter.size(); j++) {
                    if (pagesBefore.contains(pagesAfter.get(j))) {
                        invalid = true;
                    }
                }

                if (invalid) break;
            }
            if (!invalid) valid.add(printOrder.get(printOrder.size()/2));
        }

        int sum = valid.stream().mapToInt(Integer::intValue).sum();

        System.out.println("Valid: " + sum);
    }

    public void part2() {

    }

    public void parseInput(String data) {
        String[] inputs = data.split("\\n\\n");
        Scanner scanner = new Scanner(inputs[0]);

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] numStrings = line.split("\\|");
            Integer first = Integer.valueOf(numStrings[0]);
            Integer second = Integer.valueOf(numStrings[1]);

            Set<Integer> values = orderingRules.getOrDefault(second, new HashSet<>());
            values.add(first);

            orderingRules.put(second, values);
        }
        
        scanner.close();

        scanner = new Scanner(inputs[1]);

        while (scanner.hasNext()) {
            String line = scanner.nextLine();

            ArrayList<Integer> order = new ArrayList<>(
                                    Arrays.stream(line.split(",")).map(Integer::valueOf).toList());

            printOrders.add(order);
        }

        scanner.close();
    }
}
