package day4;

import java.util.ArrayList;
import java.util.Scanner;

public class Day {
    
    private ArrayList<String> puzzle = new ArrayList<>();
    private static final String XMAS = "XMAS";

    public void part1() {
        int count = 0;

        for(int i = 0; i < puzzle.size(); i++) {
            for(int j = 0; j < puzzle.get(i).length(); j++) {
                if (checkHorizontal(i, j)) ++count;
                if (checkVertical(i, j)) ++count;
                if (checkRightDiagonal(i, j)) ++count;
                if (checkLeftDiagonal(i, j)) ++count;
            }
        }

        System.out.println("XMAS Count: " + count);

    }

    private boolean checkHorizontal(int i, int j) {
        String line = puzzle.get(i);
        StringBuilder horizontal = new StringBuilder();

        if (j + 3 < line.length()) {
            horizontal.append(line.substring(j, j+4));
        }

        return checkEquality(XMAS, horizontal);
    }

    private boolean checkVertical(int i, int j) {
        StringBuilder vertical = new StringBuilder();

        if (i + 3 < puzzle.size()) {    
            for (int index = 0; index < 4; index++) {
                vertical.append(puzzle.get(index+i).charAt(j));
            }
        } 

        return checkEquality(XMAS, vertical);
    }

    private boolean checkRightDiagonal(int i, int j) {
        StringBuilder rightDiagonal = new StringBuilder();

        if (i + 3 < puzzle.size() && j + 3 < puzzle.get(i).length()) {
            for (int index = 0; index < 4; index++) {
                rightDiagonal.append(puzzle.get(i + index).charAt(j + index));
            }
        }

        return checkEquality(XMAS, rightDiagonal);
    }

    private boolean checkLeftDiagonal(int i, int j) {
        StringBuilder leftDiagonal = new StringBuilder();

        if (i + 3 < puzzle.size() && j - 3 >= 0) {
            for (int index = 0; index < 4; index++) {
                leftDiagonal.append(puzzle.get(i+index).charAt(j-index));
            }
        }
    
        return checkEquality(XMAS, leftDiagonal);
    }

    private boolean checkEquality(String word, StringBuilder puzzleWord) {
        return puzzleWord.toString().equals(word) || puzzleWord.reverse().toString().equals(word);
    }

    public void part2() {

    }

    public void parseInput(String data) {
        Scanner scanner = new Scanner(data);

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            puzzle.add(line);
        }

        scanner.close();
    }
    
}
