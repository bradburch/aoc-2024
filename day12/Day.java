package day12;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;

public class Day {

    private record Coords(int y, int x) {}

    private char[][] garden;
    Set<Coords> visited = new HashSet<>();

    public void part1() {
        int price = 0;

        for(int i = 0; i < garden.length; i++) {
            for(int j = 0; j < garden[i].length; j++) {
                Coords coords = new Coords(i, j);
                if (!visited.contains(coords)) {
                    price += calculatePrice(coords, false);
                }
            }
        }
        System.out.println("Price: " + price);
    }

    public void part2() {
        int price = 0;
        visited = new HashSet<>();

        for(int i = 0; i < garden.length; i++) {
            for(int j = 0; j < garden[i].length; j++) {
                Coords coords = new Coords(i, j);
                if (!visited.contains(coords)) {
                    price += calculatePrice(coords, true);
                }
            }
        }
        System.out.println("Discounted price: " + price);
    }

    private int calculatePrice(Coords coords, boolean discount) {
        Queue<Coords> queue = new LinkedList<>();
        addToQueue(queue, coords);
        char currentChar = garden[coords.y][coords.x];
        int area = 0;
        int perimeter = 0;
        Set<Coords> region = new HashSet<>();

        while (!queue.isEmpty()) {
            Coords current = queue.poll();
            region.add(current);
            ++area;

            int y = current.y;
            int x = current.x;

            if (y+1 < garden.length && garden[y+1][x] == currentChar) {
                addToQueue(queue, new Coords(y+1, x));
            } else {
                ++perimeter;
            }

            if (y-1 >= 0 && garden[y-1][x] == currentChar) {
                addToQueue(queue, new Coords(y-1, x));
            } else {
                ++perimeter;
            }

            if (x+1 < garden[y].length && garden[y][x+1] == currentChar) {  
                addToQueue(queue, new Coords(y, x+1));
            } else {
                ++perimeter;
            }

            if (x-1 >= 0 && garden[y][x-1] == currentChar) {
                addToQueue(queue, new Coords(y, x-1));
            } else {
                ++perimeter;
            }
        }

        if (discount) {
            return area * getFenceSides(region);
        } else {
            return area * perimeter;
        }
    }
    
    /* Stolen from https://github.com/michaelmountain-8451/advent-of-code/blob/main/src/main/java/year2024/GardenGroups.java */
    private Integer getFenceSides(Set<Coords> region) {
        boolean[][] foundAbove = new boolean[garden.length][garden.length];
        boolean[][] foundBelow = new boolean[garden.length][garden.length];
        boolean[][] foundLeft = new boolean[garden.length][garden.length];
        boolean[][] foundRight = new boolean[garden.length][garden.length];

        for (int i = 0; i < garden.length; i++) {
            for (int j = 0; j < garden.length; j++) {
                if (region.contains(new Coords(i, j))) {
                    if (i == 0 || !region.contains(new Coords(i-1, j))) foundAbove[i][j] = true;
                    if (i == garden.length - 1 || !region.contains(new Coords(i+1, j))) foundBelow[i][j] = true;
                    if (j == 0 || !region.contains(new Coords(i, j-1))) foundLeft[i][j] = true;
                    if (j == garden.length - 1 || !region.contains(new Coords(i, j+1))) foundRight[i][j] = true;
                }
            }
        }
        return countSides(foundAbove, false) + countSides(foundBelow, false) + countSides(foundLeft, true) + countSides(foundRight, true);
    }

    private Integer countSides(boolean[][] grid, boolean countVert) {
        int count = 0;
        boolean foundFence = false;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if ((countVert ? grid[j][i] : grid[i][j]) && !foundFence) {
                    count++;
                    foundFence = true;
                }
                if (!(countVert ? grid[j][i] : grid[i][j])) foundFence = false;
            }
        }
        return count;
    }
    /** End stolen code */

    private void addToQueue(Queue<Coords> queue, Coords coords) {
        if (!visited.contains(coords)) {
            visited.add(coords);
            queue.add(coords);
        }
    }

    public void parseInput(String data) {
        Scanner scanner = new Scanner(data);
        int y = 0;
        garden = new char[Math.toIntExact(data.lines().count())][data.length()];

        while (scanner.hasNextLine()) {
            char[] line = scanner.nextLine().toCharArray();
            garden[y] = line;
            ++y;
        }
        
        scanner.close();
    }
    
}
