package day4;

import java.util.ArrayList;
import java.util.Scanner;

public class Day {
    
    private ArrayList<String> puzzle = new ArrayList<>();
    private static final String XMAS = "XMAS";
    private static final String MAS = "MAS";

    public void part1() {
        int count = 0;

        for(int i = 0; i < puzzle.size(); i++) {
            for(int j = 0; j < puzzle.get(i).length(); j++) {
                if (checkHorizontal(XMAS, i, j)) ++count;
                if (checkVertical(XMAS, i, j)) ++count;
                if (checkRightDiagonal(XMAS, i, j)) ++count;
                if (checkLeftDiagonal(XMAS, i, j)) ++count;
            }
        }

        System.out.println("XMAS Count: " + count);
    }

    public void part2() {
        int count = 0;

        for(int i = 1; i < puzzle.size()-1; i++) {
            for(int j = 1; j < puzzle.get(i).length()-1; j++) {
                if (puzzle.get(i).charAt(j) == 'A') {
                    if (checkRightDiagonalMiddle(MAS, i, j) && checkLeftDiagonalMiddle(MAS, i, j)) ++count;
                }
            }
        }

        System.out.println("X-MAS Count: " + count);
    }

    private boolean checkHorizontal(String target, int i, int j) {
        String line = puzzle.get(i);
        StringBuilder horizontal = new StringBuilder();

        if (j + target.length()-1 < line.length()) {
            horizontal.append(line.substring(j, j+target.length()));
        }
        
        return checkEquality(target, horizontal);
    }

    private boolean checkVertical(String target, int i, int j) {
        StringBuilder vertical = new StringBuilder();

        if (i + target.length()-1 < puzzle.size()) {    
            for (int index = 0; index < target.length(); index++) {
                vertical.append(puzzle.get(index+i).charAt(j));
            }
        } 

        return checkEquality(target, vertical);
    }

    private boolean checkRightDiagonal(String target, int i, int j) {
        StringBuilder rightDiagonal = new StringBuilder();
        int len = target.length()-1;

        if (i + len < puzzle.size() && j + len < puzzle.get(i).length()) {
            for (int index = 0; index < target.length(); index++) {
                rightDiagonal.append(puzzle.get(i + index).charAt(j + index));
            }
        }

        return checkEquality(target, rightDiagonal);
    }

    private boolean checkRightDiagonalMiddle(String target, int i, int j) {
        StringBuilder rightDiagonal = new StringBuilder();

        rightDiagonal.append(puzzle.get(i-1).charAt(j-1));
        rightDiagonal.append(puzzle.get(i).charAt(j));
        rightDiagonal.append(puzzle.get(i+1).charAt(j+1));

        return checkEquality(target, rightDiagonal);
    }

    private boolean checkLeftDiagonal(String target, int i, int j) {
        StringBuilder leftDiagonal = new StringBuilder();
        int len = target.length() - 1;

        if (i + len < puzzle.size() && j - len >= 0) {
            for (int index = 0; index < target.length(); index++) {
                leftDiagonal.append(puzzle.get(i+index).charAt(j-index));
            }
        }
    
        return checkEquality(target, leftDiagonal);
    }

    private boolean checkLeftDiagonalMiddle(String target, int i, int j) {
        StringBuilder leftDiagonal = new StringBuilder();

        leftDiagonal.append(puzzle.get(i-1).charAt(j+1));
        leftDiagonal.append(puzzle.get(i).charAt(j));
        leftDiagonal.append(puzzle.get(i+1).charAt(j-1));

        return checkEquality(target, leftDiagonal);
    }

    private boolean checkEquality(String word, StringBuilder puzzleWord) {
        return puzzleWord.toString().equals(word) || puzzleWord.reverse().toString().equals(word);
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
