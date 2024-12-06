package day6;

import java.util.Set;

import day6.Day.Coords;

public class Result {

    private Set<Coords> positions;
    private int cycleCount;

    public Result(Set<Coords> positions, int cycleCount) {
        this.positions = positions;
        this.cycleCount = cycleCount;
    }

    public void incrementCycleCount() {
        ++this.cycleCount;
    }

    public int getCycleCount() {
        return this.cycleCount;
    }
    
    public Set<Coords> getPositions() {
        return this.positions;
    }
}
