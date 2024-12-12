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
                    price += calculatePrice(coords);
                }
            }
        }
        System.out.println("Price: " + price);
    }

    public void part2() {

    }

    private int calculatePrice(Coords coords) {
        Queue<Coords> queue = new LinkedList<>();
        addToQueue(queue, coords);
        char currentChar = garden[coords.y][coords.x];
        int area = 0;
        int perimeter = 0;

        while (!queue.isEmpty()) {
            Coords current = queue.poll();
            if (current == null) System.out.println("IT IS NULL");

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
        System.out.println("perimeter: " + perimeter);
        System.out.println("area: " + area);
        return area * perimeter;
    }

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
