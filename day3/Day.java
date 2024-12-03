package day3;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day {

    private String input;

    public void part1() {
        ArrayList<String> multiplyList = new ArrayList<>(findMul(input));

        int total = calcMul(multiplyList);

        System.out.println("Total: " + total);
    }

    public void part2() {
        int total = 0;
        String[] doInput = input.split("do\\(\\)");

        for(String doS : doInput) {
            String[] doNot = doS.split("don\\'t\\(\\)");
            ArrayList<String> multiplyList = new ArrayList<>(findMul(doNot[0]));

            total += calcMul(multiplyList);
        }
        
        System.out.println("Enabled Total: " + total);
    }

    public void parseInput(String data) {
        input = data;
    }

    private ArrayList<String> findMul(String data) {
        ArrayList<String> matches = new ArrayList<>();

        Pattern multiplyPattern = Pattern.compile("mul\\([0-9]+,[0-9]+\\)", Pattern.CASE_INSENSITIVE);
        Matcher multiplyMatcher = multiplyPattern.matcher(data);

        while (multiplyMatcher.find()) {
            matches.add(multiplyMatcher.group());
        }

        return matches;
    }

    private int calcMul(ArrayList<String> multiplyList) {
        int total = 0;

        for (String mul : multiplyList) {
            String sub = mul.substring(4, mul.length()-1);
            String[] nums = sub.split(",");
            int result = Integer.parseInt(nums[0]) * Integer.parseInt(nums[1]);

            total += result;
        }

        return total;
    }
    
}
