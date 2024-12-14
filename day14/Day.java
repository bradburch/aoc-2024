package day14;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Day {

    record Coords(int x, int y) {}
    String input;

    ArrayList<Robot> robots;
    HashMap<Coords, ArrayList<Robot>> coordsRobots = new HashMap<>();

    private static final int AREA_WIDTH = 101;
    private static final int AREA_HEIGHT = 103;

    public void part1() {
        ArrayList<Robot> nrobots = new ArrayList<>(robots);

        for (Robot robot : nrobots) {
            determinePosition(robot, 100);
        }

        int heightHalf = (AREA_HEIGHT / 2);
        int widthHalf = (AREA_WIDTH / 2);

        int numRobots = calculateQuadrant(0, widthHalf, 0, heightHalf) *
            calculateQuadrant(widthHalf+1, AREA_WIDTH, 0, heightHalf) * 
            calculateQuadrant(0, widthHalf, heightHalf+1, AREA_HEIGHT) *
            calculateQuadrant(widthHalf+1, AREA_WIDTH, heightHalf+1, AREA_HEIGHT);
        
        System.out.println("Num Robots: " + numRobots);
    }

    public void part2() {
        parseInput(input);

        for(int i = 1; i < 100000; i++) {
            coordsRobots = new HashMap<>();

            for (Robot robot : robots) {
                determinePosition(robot, 1);
            }

            if (findTree()) {
                printTree();
                System.out.println("This iteration: " + i);
                System.out.println();
                break;
            }
        }
    }

    private void determinePosition(Robot robot, int seconds) {
        int vx = robot.getVelocityX();
        int vy = robot.getVelocityY();

        for (int i = 0; i < seconds; i++) {
            int px = robot.getX();
            int py = robot.getY();

            int newX = px + vx;
            int newY = py + vy;
        
            int x = newX >= AREA_WIDTH ? newX % AREA_WIDTH : newX < 0 ? AREA_WIDTH + newX : newX;
            int y = newY >= AREA_HEIGHT ? newY % AREA_HEIGHT : newY < 0 ? AREA_HEIGHT + newY : newY;

            robot.setPosition(x, y);
        }
        Coords c = new Coords(robot.getX(), robot.getY());
        ArrayList<Robot> robots = coordsRobots.getOrDefault(c, new ArrayList<>());
        robots.add(robot);
        coordsRobots.put(c, robots);
    }

    private int calculateQuadrant(int xStart, int xEnd, int yStart, int yEnd) {
        int numRobots = 0;
        for (int i = yStart;i < yEnd; i++) {
            for(int j = xStart;j < xEnd; j++) {
                Coords c = new Coords(j, i);
                numRobots += coordsRobots.getOrDefault(c, new ArrayList<>()).size();
            }
        }

        return numRobots;
    }

    private boolean findTree() {
        for (ArrayList<Robot> robots : coordsRobots.values()) {
            if (robots.size() > 1) {
                return false;
            }
        }
        return true;
    }

    private void printTree() {
        for (int i = 0;i < AREA_HEIGHT; i++) {
            for(int j = 0;j < AREA_WIDTH; j++) {
                Coords c = new Coords(j, i);
                if (coordsRobots.containsKey(c)) {
                    System.out.print("X");
                } else {
                    System.out.print(".");
                }
            }
            System.out.println();
        }
    }

    public void parseInput(String data) {
        input = data;
        Scanner scanner = new Scanner(data);
        robots = new ArrayList<>();

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] str = line.replaceAll("[^-?0-9]+", " ").trim().split(" "); 

            int posX = Integer.valueOf(str[0]);
            int posY = Integer.valueOf(str[1]);

            int velX = Integer.valueOf(str[2]);
            int velY = Integer.valueOf(str[3]);

            Robot robot = new Robot(posX, posY, velX, velY);
            
            robots.add(robot);
        }

        scanner.close();

    }
    
}
