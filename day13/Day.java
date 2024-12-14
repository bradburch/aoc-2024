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
            totalPrice += solve(machine, false);
        }

        System.out.println("Total Price: " + totalPrice);
    }

    public void part2() {
        long totalPrice = 0;
        for (Machine machine : machines) {
            totalPrice += solve(machine, true);
        }

        System.out.println("Total Price: " + totalPrice);
    }

    private long solve(Machine machine, boolean unitError) {
        Coords buttonA = machine.buttonA;
        Coords buttonB = machine.buttonB;
        Coords prize = machine.prize;

        long prizeY = prize.y;
        long prizeX = prize.x;
        if (unitError) {
            prizeX += 10000000000000L;
            prizeY += 10000000000000L;
        }

        long buttonAPress = 0;
        long buttonBPress = 0;
        
        /* Logic from  https://topaz.github.io/paste/#XQAAAQB1CgAAAAAAAAA4GEiZzRd1JAhcKpma7J4gVovyUcIKu8WpspOgG8gikktWiBCyFdFrWfRpvCGjscRJH6AiHaEyEkn/JnSt1Y4/62YYOmOBvF4TqZAygbB7MQeQqFWW2cANstrrSxHIIOwJ2IxVFtdqG0DlSYPLdQ6D3MOo+kC6cnYNQ4P12PLQX9FD8PJ4H3Q2h+S4HQcHhTUcHJ9Y9qYMn1E2cepN74u7IxqZbgnolpquevo+xlJkC8r7pjPBpBVHtH2XOYMdPqNiSyJAdVIALSlxmUkTC4Mu2/IEe9NV8iBhrTxDUEDcfIfRM5S+pWRxSqI4Tkfp4syAQ9K32aou95NnDRINevTH3bm/a6dkuRulkGk8jQiQASnLM3cAZWpBAGe+Z7P3T8/hw5ftr12nVDfQHTrLsHv8AN4NHHU2OEXvjgul/ACOP17m32qgi+yFCYl3kpPsMvXhAHBRBfrwwcUGzjxljyGbSdAGQk6gw+NFcHCAdfNAaRwzMbFBnHZ8VjKO8IP/HQhruZbvSqpqbWdHetKVIJgpK7Ifsca5F9oW5Dt7lCrS2Wd8oMNqdp1r+Bl4QhhU8TWtDKuSsKcD8G291XvdDPvgr22l74qwADWpbBCCWd23iCeyZ8hVDGaSvjhqKzPFf3qGxzP48Wx3sHI7vz2oLSY5h+h7PNi5D9vnivvsWVlF6nX/bQeXf0Hf4SYVmXtuUmZS9SMPuOOLLFm/skpuD7pdveHlRXaR9PGUKMaNodtkhS+AOsCiVheiqVmZ8F4I17YE4gwHNGd5EqYChgkSqBAPCb9A2MmLkcUrwWcJf+S6DDF4jGIhczJeT4kvY1XWLUriaPixa9bdezmb7oGQzSMTesgfncT2NwwEK0jXr8K0wz4uNopYc57PQPRBa4AYxaFYQ70Ebb78rF8e+Sr+tsAHrtHhhC3E0Wx9iIQwSuzXwZJtdUUb92q8dRZlZNm2nPTwMzw8nsKpBYm82kGAfw+O8mFF+1BkN73rFQ3Pw4bYlBSJICSW0JkSNvfN6DQpD8WUuhkWzyMH20ek9pa1mrTFTmxhmKS7Qbg4hBNRyTnyNoqd9AiuUXyrV08M04vPJnv+FatS5OyhTBMdRjZWXYrtR++SxLoD40Rwdr4WLLnDfDLuzogsC+VTS5mZWSjsyGkXs4ws3jPG+LjXIX6Vgnh8KncnXX/E1YXtBsA21q9P+FbrY2Y34Vg8IuRUHv+wuVZ94oPUqD7BHdobwb7lXedNU+SdNKi/zf6uTXw= */
        long newY = -buttonA.y * prizeX + buttonA.x * prizeY;
        long newX = buttonB.y * prizeX - buttonB.x * prizeY;

        long determinant = buttonA.x * buttonB.y - buttonA.y * buttonB.x;

        if (newY % determinant == 0 && newX % determinant == 0) {
            buttonBPress = newY / determinant;
            buttonAPress = newX / determinant;
        }
        /*  */

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
