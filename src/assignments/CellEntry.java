package assignments;
/**
 * this class represents a cordinated value for a cell's location
 * **/
public class CellEntry  implements Index2D {
    private int x;
    private int y;
    Cell sCell;
    @Override
    public boolean isValid() {
        String cords = this.toString();
        if (cords.length()<2||cords.length()>3) {
            return false;
        }
        return true;
    }
    CellEntry(SCell sCell) {
        this.sCell = sCell;
    }
    @Override
    public int getX() {return x;}
    @Override
    public int getY() {return y;}
    @Override
    public  String toString() {return sCell.getName();}
}
