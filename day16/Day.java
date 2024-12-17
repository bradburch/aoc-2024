package day16;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;

public class Day {
    /** Inspired by looking at examples on AOC subreddit */
    private enum Direction {
        NORTH, 
        SOUTH, 
        EAST,
        WEST
    }

    private record Coord(int y, int x) {}
    private record Reindeer(Coord position, Direction direction) {}
    private record State(Reindeer reindeer, int price, HashSet<Coord> visited) {
        static Comparator<State> PRICE_COMPARATOR = Comparator.comparing(State::price);
    }

    private char[][] maze;
    private Reindeer start;
    private HashMap<Reindeer, Integer> reindeerPrice = new HashMap<>();
    private Set<Coord> bestSeats = new HashSet<>();

    public void part1() {
        System.out.println("Score: " + search());
    }

    public void part2() {
        search();

        System.out.println("Best seats: " + bestSeats.size());
    }

    private int search() {
        Queue<State> queue = new PriorityQueue<>(State.PRICE_COMPARATOR);
        State startState = new State(start, 0, new HashSet<>(List.of(start.position)));
        queue.add(startState);
        reindeerPrice.put(start, 0);
        int minPrice = Integer.MAX_VALUE;
        boolean bestPathsFound = false;
        
        while (!queue.isEmpty() && !bestPathsFound) {
            State current = queue.poll();

            for (State next : nextStates(current)) {
                if (validMove(next.reindeer) && next.price <= reindeerPrice.getOrDefault(next.reindeer, Integer.MAX_VALUE)) {
                    queue.add(next);
                    reindeerPrice.put(next.reindeer, next.price);
                    if (isEnd(next)) {
                        bestPathsFound = minPrice < next.price;
                        minPrice = Math.min(minPrice, next.price);
                        bestSeats.addAll(next.visited);
                    }
                }
            }
        }

        return minPrice;
    }

    private boolean validMove(Reindeer reindeer) {
        Coord pos = reindeer.position;
        return maze[pos.y][pos.x] != '#';
    }

    private boolean isEnd(State next) {
        Coord position = next.reindeer.position;
        return maze[position.y][position.x] == 'E';
    }

    private ArrayList<State> nextStates(State current) {
        ArrayList<State> nextStates = turn(current);
        nextStates.add(moveForward(current));

        return nextStates;
    }

    private State moveForward(State current) {
        Reindeer currentReindeer = current.reindeer;
        Coord position = currentReindeer.position;
        Coord newPosition = position;
        switch (currentReindeer.direction) {
            case NORTH:
                newPosition = new Coord(position.y-1, position.x);
                break;
            case EAST: 
                newPosition = new Coord(position.y, position.x+1);
                break;

            case SOUTH: 
                newPosition = new Coord(position.y+1, position.x);
                break;

            case WEST:
                newPosition = new Coord(position.y, position.x-1);
                break;
        }

        HashSet<Coord> v = new HashSet<>(current.visited);
        v.add(newPosition);

        return new State(new Reindeer(newPosition, currentReindeer.direction), current.price+1, v);
    }

    private ArrayList<State> turn(State current) {
        ArrayList<Reindeer> reindeers = new ArrayList<>();
        ArrayList<State> states = new ArrayList<>();

        Reindeer currentReindeer = current.reindeer;
        Coord position = currentReindeer.position;

        switch (currentReindeer.direction) {
            case NORTH:
            case SOUTH: 
                reindeers.add(new Reindeer(position, Direction.EAST));
                reindeers.add(new Reindeer(position, Direction.WEST));
                break;
            case EAST: 
            case WEST:
                reindeers.add(new Reindeer(position, Direction.NORTH));
                reindeers.add(new Reindeer(position, Direction.SOUTH));
                break;
        }

        for (Reindeer r : reindeers) {
            states.add(new State(r, current.price + 1000, current.visited));
        }

        return states;
    }

    public void parseInput(String data) {
        Scanner scanner = new Scanner(data);
        maze = new char[Math.toIntExact(data.lines().count())][data.length()];
        int y = 0;

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.contains("S")) {
                Coord position = new Coord(y, line.indexOf("S"));
                start = new Reindeer(position, Direction.EAST);
            }
            
            maze[y] = line.toCharArray();
            ++y;
        }

        scanner.close();
    }
    
}
