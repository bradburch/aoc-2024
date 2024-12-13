package day13;

import java.util.ArrayList;
import java.util.Scanner;

public class Day {

    private static final int BUTTON_A_COST = 3;

    private record Coords(long x, long y) {}
    private record Machine(Coords buttonA, Coords buttonB, Coords prize) {}

    ArrayList<Machine> machines = new ArrayList<>();

    public void part1() {
        int totalPrice = 0;
        for (Machine machine : machines) {
            totalPrice += solve(machine);
        }

        System.out.println("Total Price: " + totalPrice);
    }

    public void part2() {

    }

    private long solve(Machine machine) {
        Coords aCoords = machine.buttonA;
        Coords bCoords = machine.buttonB;
        Coords pCoords = machine.prize;

        long buttonAY = aCoords.y;
        long buttonAX = aCoords.x;

        long buttonBY = bCoords.y;
        long buttonBX = bCoords.x;

        long prizeY = pCoords.y;
        long prizeX = pCoords.x;

        int buttonAPress = 0;
        int buttonBPress = 0;
        boolean found = false;

        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 100; j++) {
                long aa = buttonAX * i;
                long bb = buttonBX * j;

                long aaa = buttonAY * i;
                long bbb = buttonBY * j;
                if ((aa + bb) == prizeX && (aaa + bbb) == prizeY) {
                    buttonAPress = i;
                    buttonBPress = j;
                    found = true;
                }
            }
            if (found) break;
        }

        return buttonAPress*BUTTON_A_COST + buttonBPress;
    }

    public void parseInput(String data) {
        Scanner scanner = new Scanner(data);

        while (scanner.hasNextLine()) {
            String buttonA = scanner.nextLine();
            if (buttonA.isEmpty()) continue;
            String buttonB = scanner.nextLine();
            String prize = scanner.nextLine();
            
            Coords a = parseCoords(buttonA);
            Coords b = parseCoords(buttonB);
            Coords p = parseCoords(prize);

            machines.add(new Machine(a, b, p));
        }

        scanner.close();
    }

    private Coords parseCoords(String configuration) {
        int Xstart = configuration.indexOf("X") + 2;
        int Xend = configuration.indexOf(",");
        int Ystart = configuration.indexOf("Y") + 2;

        int x = Integer.valueOf(configuration.substring(Xstart, Xend));
        int y = Integer.valueOf(configuration.substring(Ystart));

        return new Coords(x, y);
    }
    
}
