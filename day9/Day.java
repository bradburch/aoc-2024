package day9;

import java.util.ArrayList;

public class Day {

    String disk;

    public void part1() {
        long checksum = 0;

        ArrayList<Integer> fullDiskMap = new ArrayList<>(populateFullDiskMap());
        ArrayList<Integer> sortedDiskMap = new ArrayList<>(sortDisk(fullDiskMap));

        for (int i = 0; i < sortedDiskMap.size(); i++) {
            if (sortedDiskMap.get(i) == -1) break;
            
            checksum += (i * sortedDiskMap.get(i));
        }

        System.out.println("Checksum: " + checksum);
    }

    private ArrayList<Integer> populateFullDiskMap() {
        int id = 0;
        ArrayList<Integer> fullDiskMap = new ArrayList<>();

        for (int i = 0; i < disk.length(); i++) {
            Integer num = Character.getNumericValue(disk.charAt(i));

            if (i == 0 || i % 2 == 0) {
                for (int j = 0; j < num; j++) {
                    fullDiskMap.add(id);
                }
                ++id;
            } else {
                for (int j = 0; j < num; j++) {
                    fullDiskMap.add(-1);
                }
            }
        }

        return fullDiskMap;
    }

    private ArrayList<Integer> sortDisk(ArrayList<Integer> fullDiskMap) {
        ArrayList<Integer> sortedDisk = new ArrayList<>(fullDiskMap);
        for (int i = sortedDisk.size()-1; i >0; i--) {
            int freeIndex = sortedDisk.indexOf(-1);
            if (sortedDisk.get(i) != -1 && freeIndex < i) {
                sortedDisk.set(freeIndex, sortedDisk.get(i));
                sortedDisk.set(i, -1);
            }
        }

        return sortedDisk;
    }

    public void part2() {

    }

    public void parseInput(String data) {
        disk = data;
    }
    
}
