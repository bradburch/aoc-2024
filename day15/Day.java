package day15;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day {

    private record Coords(int y, int x) {}
    private char[][] floor;
    private ArrayList<Character> moves = new ArrayList<>();

    private Coords start;

    public void part1() {
        Coords current = start;
        for (Character move : moves) {
            current = move(move, current);
        }

        printFloor();

        System.out.println("Total: " + calculateGPS());
    }

    private Coords move(Character move, Coords current) {
        switch (move) {
            case '<':
                int nextX = current.x - 1;
                
                while (nextX > 0) {
                    int nextTile = floor[current.y][nextX];
                    if (nextTile == 'O') {
                        --nextX;
                    } else if (nextTile == '#') {
                        break;
                    } else {
                        int diff = current.x - nextX;

                        for (int i = diff; i > 0; i--) {
                            swap(current.y, current.x-i, current.y, current.x-i+1);
                        }
                        current = new Coords(current.y, current.x-1);
                        break;
                    }
                }
                break;
            case '>': 
                int nextXR = current.x + 1;
                    
                while (nextXR < floor[current.y].length) {
                    int nextTile = floor[current.y][nextXR];
                    if (nextTile == 'O') {
                        ++nextXR;
                    } else if (nextTile == '#') {
                        break;
                    } else {
                        int diff = nextXR - current.x;

                        for (int i = diff; i > 0; i--) {
                            swap(current.y, current.x+i, current.y, current.x+i-1);
                        }
                        current = new Coords(current.y, current.x+1);
                        break;
                    }
                }
                break;
            case '^': 
                int nextY = current.y - 1;
                    
                while (nextY > 0) {
                    int nextTile = floor[nextY][current.x];
                    if (nextTile == 'O') {
                        --nextY;
                    } else if (nextTile == '#') {
                        break;
                    } else {
                        int diff = current.y - nextY;

                        for (int i = diff; i > 0; i--) {
                            swap(current.y-i, current.x, current.y-i+1, current.x);
                        }
                        current = new Coords(current.y-1, current.x);
                        
                        break;
                    }
                }
                break;
            case 'v':
            int nextYD = current.y + 1;
                    
            while (nextYD < floor.length) {
                int nextTile = floor[nextYD][current.x];
                if (nextTile == 'O') {
                    ++nextYD;
                } else if (nextTile == '#') {
                    break;
                } else {
                    int diff = nextYD - current.y;

                    for (int i = diff; i > 0; i--) {
                        swap(current.y+i, current.x, current.y+i-1, current.x);
                    }
                    current = new Coords(current.y+1, current.x);
                    
                    break;
                }
            }
                break;
            default:
                break;
        }

        return current;
    }

    private void swap(int y, int x, int newY, int newX) {        
        char temp = floor[newY][newX];
        floor[newY][newX] = floor[y][x];
        floor[y][x] = temp;
    }

    private int calculateGPS() {
        int total = 0;

        for (int i = 0; i < floor.length; i++) {
            for (int j = 0; j < floor[i].length; j++) {
                if (floor[i][j] == 'O') {
                    int y = i * 100;
                    total += y + j;
                }
            }
        }

        return total;
    }

    public void part2() {

    }

    private void printFloor() {
        for (int i = 0; i < floor.length; i++) {
            for (int j = 0; j < floor[i].length; j++) {
                System.out.print(floor[i][j]);
            }
            System.out.println();
        }
    }

    public void parseInput(String data) {
        String[] floorMoves = data.split("\n\n");
        parseFloor(floorMoves[0]);
        parseMoves(floorMoves[1]);
    }

    private void parseFloor(String data) {
        Scanner scanner = new Scanner(data);
        
        floor = new char[Math.toIntExact(data.lines().count())][data.length()];
        int y = 0;

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.contains("@")) {
                start = new Coords(y, line.indexOf("@"));
            }
            floor[y] = line.toCharArray();
            ++y;
        }

        scanner.close();
    }

    private void parseMoves(String data) {
        Scanner scanner = new Scanner(data);

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            List<Character> chars = line.chars().mapToObj(c -> (char) c).toList();
            moves.addAll(chars);
        }
        
        scanner.close();
    }
    
}
