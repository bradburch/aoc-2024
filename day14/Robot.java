package day14;

import day14.Day.Coords;

public class Robot {
    
    private Coords position;
    private Coords velocity;

    public Robot(Coords position, Coords velocity) {
        this.position = position;
        this.velocity = velocity;
    }

    public Coords getVelocity() {
        return this.velocity;
    }

    public Coords getPosition() {
        return this.position;
    }

    public void setPosition(Coords position) {
        this.position = position;
    }
    
}
