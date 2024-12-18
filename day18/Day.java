package day18;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;

public class Day {

    private record Coords(int x, int y) {}
    private record State(Coords coords, int steps, Set<Coords> visited) {}

    private ArrayList<Coords> corruptedMemory = new ArrayList<>();
    private char[][] memorySpace = new char[71][71];
    private Set<Coords> seen = new HashSet<>();

    public void part1() {
        corrupt(1024);
        System.out.println("Least steps: " + pathFind());
        printSpace();
    }

    public void part2() {
        for (int i = 1024; i < corruptedMemory.size(); i++) {
            reset();
            corrupt(i);
            int s = pathFind();

            if (s == Integer.MAX_VALUE) {
                System.out.println("Blocked at: " + corruptedMemory.get(i-1));
                break;
            }
        }
        printSpace();
    }

    private void corrupt(int bytes) {
        for (int i = 0; i < bytes; i++) {
            int y = corruptedMemory.get(i).y;
            int x = corruptedMemory.get(i).x;

            memorySpace[y][x] = '#';
        }
    }

    private int pathFind() {
        Queue<State> queue = new LinkedList<>();
        State startState = new State(new Coords(0, 0), 0, new HashSet<>());
        startState.visited.add(startState.coords);
        queue.add(startState);
        int minSteps = Integer.MAX_VALUE;
        
        while (!queue.isEmpty()) {
            State current = queue.poll();

            for (State next : nextStates(current)) {
                if (validMove(next) && !seen.contains(next.coords)) {
                    queue.add(next);
                    seen.add(next.coords);

                    if (isEnd(next)) {
                        minSteps = Math.min(minSteps, next.steps);
                    }
                }
            }
        }

        return minSteps;
    }

    private boolean validMove(State current) {
        Coords currCoords = current.coords;
        int x = currCoords.x;
        int y = currCoords.y;

        return x >= 0 && 
                x < memorySpace[0].length && 
                y >= 0 && 
                y < memorySpace.length &&
                memorySpace[y][x] != '#';
    }

    private boolean isEnd(State current) {
        return current.coords.y == (memorySpace.length - 1) && current.coords.x == (memorySpace.length - 1);
    }

    private List<State> nextStates(State current) {
        State right = addState(current.coords.x + 1 , current.coords.y, current.steps, current.visited);
        State left = addState(current.coords.x - 1 , current.coords.y, current.steps, current.visited);
        State up = addState(current.coords.x, current.coords.y - 1, current.steps, current.visited);
        State down = addState(current.coords.x, current.coords.y + 1, current.steps, current.visited);

        return List.of(right, left, up, down);
    }

    private State addState(int x, int y, int steps, Set<Coords> visited) {
        Coords newCoords = new Coords(x, y);
        Set<Coords> v = new HashSet<>(visited);
        v.add(newCoords);

        return new State(newCoords, steps + 1, v);
    }

    private void reset() {
        seen = new HashSet<>();
        for(int i = 0; i < memorySpace.length; i++) {
            for(int j = 0; j < memorySpace[i].length; j++) {
                memorySpace[i][j] = '.';
            }
        }
    }

    private void printSpace() {
        for(int i = 0; i < memorySpace.length; i++) {
            for(int j = 0; j < memorySpace[i].length; j++) {
                if (memorySpace[i][j] != '#')
                    System.out.print('.');
                else
                    System.out.print('#');
            }
            System.out.println();
        }
    }

    public void parseInput(String data) {
        Scanner scanner = new Scanner(data);

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();

            String[] xy = line.split(",");
            corruptedMemory.add(new Coords(Integer.valueOf(xy[0]), Integer.valueOf(xy[1])));
        }

        scanner.close();
    }
    
}
