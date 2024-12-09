import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import day9.Day;

public class Main {
    public static void main(String[] args) {
        String dayNum = "day9/";
        String input = "input.txt";
        String test = "test.txt";

        try(BufferedReader br = new BufferedReader(new FileReader(dayNum + input))) {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            String data = sb.toString();

            Day day = new Day();
            day.parseInput(data);
            day.part1();
            day.part2();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}