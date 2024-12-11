package day10;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Stream;

public class Day {

    private record Coords(int y, int x) {}

    private int[][] topographicMap;

    public void part1() {
        int count = 0;
        for(int i = 0; i < topographicMap.length; i++) {
            for(int j = 0; j < topographicMap[i].length; j++) {
                int current = topographicMap[i][j];
                if (current == 0) {
                    ArrayList<Coords> ninesVisited = new ArrayList<>();
                    createPath(new Coords(i, j), ninesVisited);
                    Set<Coords> set = new HashSet<>(ninesVisited);

                    count += set.size();
                }
            }
        }

        System.out.println("Unique Count: " + count);
    }


    public void part2() {
        int count = 0;
        for(int i = 0; i < topographicMap.length; i++) {
            for(int j = 0; j < topographicMap[i].length; j++) {
                int current = topographicMap[i][j];
                if (current == 0) {
                    ArrayList<Coords> ninesVisited = new ArrayList<>();
                    createPath(new Coords(i, j), ninesVisited);

                    count += ninesVisited.size();
                }
            }
            
        }

        System.out.println("Total Count: " + count);
    }

    private void createPath(Coords current, ArrayList<Coords> ninesVisited) {
        int y = current.y;
        int x = current.x;
        int currentVal = topographicMap[y][x];

        if (y+1 < topographicMap.length && (topographicMap[y+1][x] - currentVal) ==  1) {
            createPath(new Coords(y+1, x), ninesVisited);
        }

        if (y-1 >= 0 && (topographicMap[y-1][x] - currentVal) ==  1) {
            createPath(new Coords(y-1, x), ninesVisited);
        }

        if (x+1 < topographicMap[y].length && (topographicMap[y][x+1] - currentVal) ==  1) {
            createPath(new Coords (y, x+1), ninesVisited);
        }

        if (x-1 >= 0 && (topographicMap[y][x-1] - currentVal) ==  1) {
            createPath(new Coords(y, x-1), ninesVisited);
        }

        if (currentVal == 9) {
            ninesVisited.add(current);
        }
    }

    public void parseInput(String data) {
        Scanner scanner = new Scanner(data);
        topographicMap = new int[Math.toIntExact(data.lines().count())][data.length()];
        int y = 0;
        
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            topographicMap[y] = Stream.of(line.split("")).mapToInt(Integer::parseInt).toArray();
            ++y;
        }

        scanner.close();
    }
    
}
