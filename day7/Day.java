package day7;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Day {

    private ArrayList<Long> targets = new ArrayList<>();
    private ArrayList<List<Long>> equations = new ArrayList<>();

    public void part1() {
        Long sum = 0L;

        for (int i = 0; i < targets.size(); i++) {
            List<Long> equation = equations.get(i);
            sum += calculate(equation, 1, equation.get(0), targets.get(i), false);
        }

        System.out.println("Total Calibration Result: " + sum);
    }

    public void part2() {
        Long sum = 0L;

        for (int i = 0; i < targets.size(); i++) {
            List<Long> equation = equations.get(i);
            sum += calculate(equation, 1, equation.get(0), targets.get(i), true);
        }

        System.out.println("Total Concat Calibration Result: " + sum);
    }

    private Long calculate(List<Long> eq, int index, Long prev, Long target, boolean useConcat) {
        if (index == eq.size() && prev.equals(target)) {
            return prev;
        }

        if (index == eq.size() || prev > target) {
            return 0L;
        }

        Long addPrev = prev + eq.get(index);;
        Long add = calculate(eq, index+1, addPrev, target, useConcat);
        
        Long mulPrev = prev * eq.get(index);;
        Long mul = calculate(eq, index+1, mulPrev, target, useConcat);

        Long concat = 0L;
        if (useConcat) {
            String concatPrevString = String.valueOf(prev) + String.valueOf(eq.get(index));
            Long concatPrev = Long.valueOf(concatPrevString);
            concat = calculate(eq, index+1, concatPrev, target, useConcat);
        }

        if (add.equals(target)) {
            return add;
        } else if (mul.equals(target)) {
            return mul;
        } else if (concat.equals(target)) {
            return concat;
        }

        return 0L;
    }

    public void parseInput(String data) {
        Scanner scanner = new Scanner(data);

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] spl = line.split(":");

            targets.add(Long.valueOf(spl[0]));

            String[] eq = spl[1].trim().split(" ");

            List<Long> list = Arrays.stream(eq).map(Long::valueOf).collect(Collectors.toList());
            equations.add(list);
        }

        scanner.close();
    }
}
