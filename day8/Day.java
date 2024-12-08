package day8;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Day {
    
    private record Coords(int y, int x) {}

    private char[][] map;
    private HashMap<Character, ArrayList<Coords>> antennas = new HashMap<>();
    
    public void part1() {
        for(int i = 0; i < map.length; i++) {
            for(int j = 0; j < map[i].length; j++) {
                if (map[i][j] != '.') {
                    ArrayList<Coords> coords = antennas.getOrDefault(map[i][j], new ArrayList<>());
                    coords.add(new Coords(i, j));
                    antennas.put(map[i][j], coords);
                }
            }
        }
        Set<Coords> antinodes = new HashSet<>();

        for (ArrayList<Coords> value : antennas.values()) {
            if (value.size() > 1) {
                for (int i = 0; i < value.size(); i++) {
                    int y = value.get(i).y;
                    int x = value.get(i).x;
                    for (int j = i+1; j < value.size(); j++) {
                        int compareY = value.get(j).y;
                        int compareX = value.get(j).x;
                        
                        int yDiff = y -  compareY;
                        int xDiff = x - compareX;

                        int antinode1Y = y + yDiff;
                        int antinode1X = x + xDiff;

                        int antinode2Y = compareY - yDiff;
                        int antinode2X = compareX - xDiff;

                        if (antinode1Y < map.length && antinode1Y >= 0 && antinode1X < map[y].length && antinode1X >= 0) {
                            antinodes.add(new Coords(antinode1Y, antinode1X));
                        }

                        if (antinode2Y < map.length && antinode2Y >= 0 && antinode2X < map[y].length && antinode2X >= 0) {
                            antinodes.add(new Coords(antinode2Y, antinode2X));
                        }
                    }
                }
            }
        }

        System.out.println("Unique Antinode Locations: " + antinodes.size());
    }

    public void part2() {

    }

    public void parseInput(String data) {
        Scanner scanner = new Scanner(data);
        map = new char[Math.toIntExact(data.lines().count())][data.length()];
        int y = 0;
        
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();

            map[y] = line.trim().toCharArray();
            y++;
        }

        scanner.close();
    }
}
