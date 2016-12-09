/**
 * Created by jeremy on 30/04/16.
 */
public class Position2D {
    protected int x;
    protected int y;

    public Position2D() {
        x=0;
        y=0;
    }

    public Position2D(int x, int y) {
        this.x=x;
        this.y=y;
    }
    public void setAbs(int x) {
        this.x=x;
    }
    public void setOrd(int y) {
        this.y=y;
    }
    public void setPos2D(int x, int y) {
        this.x=x;
        this.y=y;
    }
    public void setPos2D(Position2D pos) {
        this.x=pos.x;
        this.y=pos.y;
    }
    public int getAbs() {
        return this.x;
    }
    public int getOrd() {
        return this.y;
    }
    public Position2D getPos2D() {
        return new Position2D(this.x,this.y);
    }
    public String toString() {
        return (this.x+","+this.y);
    }
    public boolean equals(Position2D pos) {
        return ((this.x==pos.x) && (this.y==pos.y));
    }
}
