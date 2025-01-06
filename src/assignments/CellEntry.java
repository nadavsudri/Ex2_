package assignments;
/**
 * this class represents a cordinated value for a cell's location
 * **/
public class CellEntry  implements Index2D {
    private int x;
    private int y;
    @Override
    public boolean isValid() {
        String cords = this.toString();
        if (cords.length()<2||cords.length()>3) {
            return false;
        }
        return true;
    }
    @Override
    public int getX() {return x;}
    @Override
    public int getY() {return y;}
    @Override
    public  String toString() {return""+Extras.int2_char(x)+y;}
}
