package day14;

public class Robot {

    private int x;
    private int y;
    private int velX;
    private int velY;

    public Robot(int x, int y, int velX, int velY) {
        this.x = x;
        this.y = y;
        this.velX = velX;
        this.velY = velY;
    }

    public void move(final int sizeX, final int sizeY) {
        final int newX = this.x + this.velX;
        final int newY = this.y + this.velY;

        this.x = newX >= sizeX ? newX % sizeX : newX < 0 ? sizeX + newX : newX;
        this.y = newY >= sizeY ? newY % sizeY : newY < 0 ? sizeY + newY : newY;
    }

    public int getVelocityX() {
        return this.velX;
    }

    public int getVelocityY() {
        return this.velY;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
}
