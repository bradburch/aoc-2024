package day14;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Day {

    record Coords(int x, int y) {}

    ArrayList<Robot> robots = new ArrayList<>();
    HashMap<Coords, ArrayList<Robot>> coordsRobots = new HashMap<>();

    private static final int AREA_WIDTH = 101;
    private static final int AREA_HEIGHT = 103;

    public void part1() {

        for (Robot robot : robots) {
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

    }

    private void determinePosition(Robot robot, int seconds) {
        Coords velocity = robot.getVelocity();

        int vx = velocity.x();
        int vy = velocity.y();

        for (int i = 0; i < seconds; i++) {
            int px = robot.getPosition().x();
            int py = robot.getPosition().y();

            int newPX = px + vx;
            int newPY = py + vy;

            if (newPX < 0) {
                newPX = AREA_WIDTH + newPX;
            }
            if (newPX >= AREA_WIDTH) {
                newPX = newPX - AREA_WIDTH;
            }

            if (newPY < 0) {
                newPY = AREA_HEIGHT + newPY;
            }
            if (newPY >= AREA_HEIGHT) {
                newPY = newPY - AREA_HEIGHT;
            }

            robot.setPosition(new Coords(newPX, newPY));
        }
        
        ArrayList<Robot> robots = coordsRobots.getOrDefault(robot.getPosition(), new ArrayList<>());
        robots.add(robot);
        coordsRobots.put(robot.getPosition(), robots);
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

    public void parseInput(String data) {
        Scanner scanner = new Scanner(data);

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] str = line.replaceAll("[^-?0-9]+", " ").trim().split(" "); 

            int posX = Integer.valueOf(str[0]);
            int posY = Integer.valueOf(str[1]);

            int velX = Integer.valueOf(str[2]);
            int velY = Integer.valueOf(str[3]);

            Robot robot = new Robot(new Coords(posX, posY), new Coords(velX, velY));
            
            robots.add(robot);
        }

        scanner.close();

    }
    
}
