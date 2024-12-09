package day9;

import java.util.ArrayList;
import java.util.HashMap;

public class Day {

    String disk;
    HashMap<Integer, Integer> idSize = new HashMap<>();

    public void part1() {
        ArrayList<Integer> fullDiskMap = new ArrayList<>(populateFullDiskMap());
        ArrayList<Integer> sortedDiskMap = new ArrayList<>(sortDisk(fullDiskMap));

        System.out.println("Checksum: " + calculateChecksum(sortedDiskMap));
    }

    public void part2() {
        ArrayList<Integer> fullDiskMap = new ArrayList<>(populateFullDiskMap());
        ArrayList<Integer> sortedDiskFiles = new ArrayList<>(sortDiskFiles(fullDiskMap));

        System.out.println("Checksum after file sort: " + calculateChecksum(sortedDiskFiles));
    }

    private ArrayList<Integer> populateFullDiskMap() {
        int id = 0;
        ArrayList<Integer> fullDiskMap = new ArrayList<>();

        for (int i = 0; i < disk.length()-1; i++) {
            Integer num = Character.getNumericValue(disk.charAt(i));

            if (i == 0 || i % 2 == 0) {
                idSize.put(id, num);
                for (int j = 0; j < num; j++) {
                    fullDiskMap.add(id);
                }
                ++id;
            } else {
                if (num > 0) {
                    for (int j = 0; j < num; j++) {
                        fullDiskMap.add(-1);
                    }
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

    private ArrayList<Integer> sortDiskFiles(ArrayList<Integer> fullDiskMap) {
        ArrayList<Integer> sortedDisk = new ArrayList<>(fullDiskMap);

        for (int i = sortedDisk.size()-1; i > 0; i--) {
            int id = sortedDisk.get(i);
            if (id != -1) {
                int idLength = idSize.get(id);
                int count = 0;

                for (int j = sortedDisk.indexOf(-1); j < i; j++) {
                    ++count;
                    if (sortedDisk.get(j) != -1) {
                        count = 0;
                    }
                    
                    if (count == idLength) {
                        for (int k = 0; k < idLength; k++) {
                            sortedDisk.set(j-k, id);
                            sortedDisk.set(i-k, -1);
                        }
                        i = i - idLength + 1;
                        break;
                    }
                }
            }
        }

        return sortedDisk;
    }

    private long calculateChecksum(ArrayList<Integer> sortedDisk) {
        long checksum = 0L;
        for (int i = 0; i < sortedDisk.size(); i++) {
            if (sortedDisk.get(i) == -1) continue;
            
            checksum += (i * sortedDisk.get(i));
        }

        return checksum;
    }



    public void parseInput(String data) {
        disk = data;
    }
    
}
