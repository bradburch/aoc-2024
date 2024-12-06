package day6;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Day {

    private ArrayList<String> area = new ArrayList<>();
    private Coords start;

    private enum Direction {
        RIGHT, 
        LEFT, 
        UP, 
        DOWN
    }
    
    public void part1() {
        Set<Coords> positions = new HashSet<>();

        int x = start.x();
        int y = start.y();
        Direction direction = Direction.UP;

        while (y >= 0 && y < area.size() && x >= 0 && x < area.get(y).length()) {
            positions.add(new Coords(x, y));
            switch (direction) {
                case UP:
                    if (y-1 >=0 && area.get(y-1).charAt(x) == '#') {
                        direction = Direction.RIGHT;
                    } else {
                        --y;
                    }
                    break;
                case DOWN: 
                    if (y+1 < area.size() && area.get(y+1).charAt(x) == '#') {
                        direction = Direction.LEFT;
                    } else {
                        ++y;
                    }
                    break;
                case LEFT: 
                    if (x-1 >=0 && area.get(y).charAt(x-1) == '#') {
                        direction = Direction.UP;
                    } else {
                        --x;
                    }
                    break;
                case RIGHT: 
                    if (x+1 < area.get(y).length() && area.get(y).charAt(x+1) == '#') {
                        direction = Direction.DOWN;
                    } else {
                        ++x;
                    }
                    break;
                default:
                    break;
            }
        }

        System.out.println("Distinct Areas: " + positions.size());
    }

    public void part2() {

    }

    public void parseInput(String data) {
        Scanner scanner = new Scanner(data);
        int y = 0;

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();

            if (line.contains("^")) {
                int x = line.indexOf("^");
                start = new Coords(x, y);
            }

            area.add(line);
            ++y;
        }

        scanner.close();
    }
}
