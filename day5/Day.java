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
        ArrayList<ArrayList<Integer>> valids = retrieveOrders(true);
        int sum = 0;

        for (List<Integer> valid : valids) {
            sum += valid.get(valid.size()/2);
        }

        System.out.println("Valid: " + sum);
    }

    public void part2() {
        ArrayList<ArrayList<Integer>> invalids = retrieveOrders(false);
        int sum = 0;

        for (List<Integer> valid : invalids) {
            sum += valid.get(valid.size()/2);
        }

        System.out.println("Newly valid: " + sum);
    }

    private ArrayList<ArrayList<Integer>> retrieveOrders(boolean returnValid) {
        ArrayList<ArrayList<Integer>> result = new ArrayList<>();

        for (ArrayList<Integer> printOrder : printOrders) {
            boolean invalid = false;
            for (int i = 0; i < printOrder.size(); i++) {
                Set<Integer> pagesBefore = orderingRules.getOrDefault(printOrder.get(i), new HashSet<>());
                List<Integer> pagesAfter = printOrder.subList(i, printOrder.size());

                for (int j = 0; j < pagesAfter.size(); j++) {
                    if (pagesBefore.contains(pagesAfter.get(j))) {
                        if (!returnValid) {
                            result.add(swap(printOrder));
                        }
                        invalid = true;
                        break;
                    }
                }

                if (invalid) break;
            }
            if (returnValid && !invalid) result.add(printOrder);
        }

        return result;
    }

    private ArrayList<Integer> swap(ArrayList<Integer> invalid) {
        ArrayList<Integer> swapped = new ArrayList<>(invalid);
        int[] swapIndices = getSwapIndicies(invalid);

        while (swapIndices[0] != swapIndices[1]) {
            Integer temp = swapped.get(swapIndices[0]);

            swapped.set(swapIndices[0], swapped.get(swapIndices[1]));
            swapped.set(swapIndices[1], temp);

            swapIndices = getSwapIndicies(swapped);
        }
        
        return swapped;
    }

    private int[] getSwapIndicies(ArrayList<Integer> printOrder) {
        boolean invalid = false;
        int[] swap = new int[2];

        for (int i = 0; i < printOrder.size(); i++) {
            Set<Integer> pagesBefore = orderingRules.getOrDefault(printOrder.get(i), new HashSet<>());
            List<Integer> pagesAfter = printOrder.subList(i, printOrder.size());

            for (int j = 0; j < pagesAfter.size(); j++) {
                if (pagesBefore.contains(pagesAfter.get(j))) {
                    swap[0] = j+i;
                    swap[1] = i;
                    invalid = true;
                }
            }

            if (invalid) break;
        }
        return swap;
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
