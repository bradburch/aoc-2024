package day7;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Day {

    private ArrayList<Long> testValues = new ArrayList<>();
    private ArrayList<List<Long>> equations = new ArrayList<>();

    public void part1() {
        Long sum = 0L;

        for (int i = 0; i < testValues.size(); i++) {
            List<Long> equation = equations.get(i);
            sum += calculate(equation, 1, equation.get(0), testValues.get(i));
        }

        System.out.println("Total Calibration Result: " + sum);
    }

    private Long calculate(List<Long> eq, int index, Long prev, Long target) {
        if (index == eq.size() && prev.equals(target)) {
            return prev;
        }

        if (index == eq.size()) {
            return 0L;
        }

        Long addPrev = prev + eq.get(index);;
        Long add = calculate(eq, index+1, addPrev, target);
        
        Long mulPrev = prev *= eq.get(index);;
        Long mul =  calculate(eq, index+1, mulPrev, target);

        if (add.equals(target)) {
            return add;
        } else if (mul.equals(target)) {
            return mul;
        }

        return 0L;
    }

    public void part2() {

    }

    public void parseInput(String data) {
        Scanner scanner = new Scanner(data);

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] spl = line.split(":");

            testValues.add(Long.valueOf(spl[0]));

            String[] eq = spl[1].trim().split(" ");

            List<Long> list = Arrays.stream(eq).map(Long::valueOf).collect(Collectors.toList());
            equations.add(list);
        }

        scanner.close();
    }
}
