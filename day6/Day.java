package day6;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Day {

    private char[][] defaultArea;
    private CoordsDirection start;

    private enum Direction {
        RIGHT, 
        LEFT, 
        UP, 
        DOWN
    }

    record Coords(int x, int y) { }
    record CoordsDirection(int x, int y, Direction direction) { }
    
    public void part1() {
        Result result = move(defaultArea, false);

        System.out.println("Distinct Positions: " + result.getPositions().size());
    }

    public void part2() {
        char[][] loopArea = defaultArea.clone();
        Result result = move(loopArea, false);
        Set<Coords> positions = new HashSet<>(result.getPositions());
        int cycleCount = 0;

        for(Coords c : positions) {
            loopArea[c.y][c.x] = '#';

            result = move(loopArea, true);
            cycleCount += result.getCycleCount();
            
            loopArea[c.y][c.x] = '.';
        }

        System.out.println("Obstruction Positions: " + cycleCount);
    }

    private Result move(char[][] area, boolean checkLoop) {
        Set<Coords> positions = new HashSet<>();
        Set<CoordsDirection> positionsD = new HashSet<>();
        Result result = new Result(positions, 0);

        int x = start.x();
        int y = start.y();
        Direction direction = start.direction;

        while (y >= 0 && y < area.length && x >= 0 && x < area[y].length) {
            if (!checkLoop) positions.add(new Coords(x, y));
            
            switch (direction) {
                case UP:
                    if (y-1 >=0 && area[y-1][x] == '#') {
                        direction = Direction.RIGHT;
                    } else {
                        --y;
                    }
                    break;
                case DOWN: 
                    if (y+1 < area.length && area[y+1][x] == '#') {
                        direction = Direction.LEFT;
                    } else {
                        ++y;
                    }
                    break;
                case LEFT: 
                    if (x-1 >=0 && area[y][x-1] == '#') {
                        direction = Direction.UP;
                    } else {
                        --x;
                    }
                    break;
                case RIGHT: 
                    if (x+1 < area[y].length && area[y][x+1] == '#') {
                        direction = Direction.DOWN;
                    } else {
                        ++x;
                    }
                    break;
                default:
                    break;
            }

            if (checkLoop) {
                CoordsDirection cd = new CoordsDirection(x, y, direction);
                Coords c = new Coords(x, y);
                if (positionsD.contains(cd)) {
                    result.incrementCycleCount();
                    break;
                }
                positionsD.add(new CoordsDirection(x, y, direction));
            }
        }

        return result;
    }

    public void parseInput(String data) {
        Scanner scanner = new Scanner(data);
        defaultArea = new char[Math.toIntExact(data.lines().count())][data.length()];
        int y = 0;

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            char[] arr = line.toCharArray();
            defaultArea[y] = arr;

            if (line.contains("^")) {
                int x = line.indexOf("^");
                start = new CoordsDirection(x, y, Direction.UP);
            }

            ++y;
        }

        scanner.close();
    }
}
