package assignments;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;
// Add your documentation below:

public class Ex2Sheet implements Sheet {
    private Cell[][] table;


    public Ex2Sheet(int x, int y) {
        table = new SCell[x][y];
        for(int i=0;i<x;i=i+1) {
            for(int j=0;j<y;j=j+1) {
                table[i][j] = new SCell("");
                table[i][j].setName(Extras.int2_char(i)+""+j);
                table[i][j].setType(table[i][j].getType());
                //  System.out.println(this.table[i][j].getName());
            }
        }
        eval();
    }
    public Ex2Sheet() {
        this(Ex2Utils.WIDTH, Ex2Utils.HEIGHT);
    }

    @Override
    public String value(int x, int y) {
        String ans = "ERR";
        Cell c = get(x,y);
        if (c.getType() == Ex2Utils.ERR_CYCLE_FORM) {
            return "ERR_Cycle_Form";
        }
        return switch (c.getType()) {
            case Ex2Utils.ERR_FORM_FORMAT -> "ERR_Form_Format";
            case Ex2Utils.TEXT -> c.getData();
            case Ex2Utils.NUMBER -> String.valueOf(Double.parseDouble(c.getData()));
            case Ex2Utils.FORM -> String.valueOf(eval(x, y));
            default -> throw new IllegalArgumentException("Unknown cell type: " + c.getType());
        };

    }

    @Override
    public Cell get(int x, int y) {
        return table[x][y];
    }

    @Override
    public Cell get(String cords) {
        Cell ans = null;
        ans =  this.table[Extras.char2num(cords.charAt(0))][Integer.parseInt(cords.substring(1))];
        return ans;
    }

    @Override
    public int width() {
        return table.length;
    }
    @Override
    public int height() {
        return table[0].length;
    }
    @Override
    public void set(int x, int y, String s) {
        Cell c = new SCell(s);
        table[x][y] = c;
        c.setName(Extras.int2_char(x)+""+y);
        // Add your code here

        /////////////////////
    }
    @Override
    public void eval() {
        int[][] dd = depth();
        for (int i = 0; i < width(); i++) {
            for (int j = 0; j < height(); j++) {
                table[i][j].setOrder(dd[i][j]);
                table[i][j].setType(table[i][j].getType());
                if (getSubCells(table[i][j])!=null)
                {Cell sub = getSubCells(table[i][j] );
                    if(!SCell.is_valid(sub))
                        table[i][j].setType(Ex2Utils.ERR_FORM_FORMAT);
                }
            }

        }
    }

    @Override
    public boolean isIn(int xx, int yy) {
        boolean ans = xx>=0 && yy>=0;
        // Add your code here

        /////////////////////
        return ans;
    }
    public int set_depth (Cell a)
    {   if (a.getType() == Ex2Utils.TEXT) {return 0;}
       if (a.getType() == Ex2Utils.NUMBER) {return 0;}
       if (!SCell.is_form(a.getData())) {return Ex2Utils.ERR_FORM_FORMAT;}
        try{
        int depth = 0;
        String str = a.getData();
        for (int i = 0; i < str.length(); i++)
        {
            if (Character.isLetter(str.charAt(i)))
            {
                if(Extras.isCellRef(str.substring(i,i+1))||Extras.isCellRef(str.substring(i,i+2)))
                {   String b_name = Extras.isCellRef(str.substring(i,i+1)) ? str.substring(i,i+1) : str.substring(i,i+2);
                    depth++;
                    int sub_depth = set_depth(table[Extras.char2num(b_name.charAt(0))][Integer.parseInt(b_name.substring(1))]);
                    if (sub_depth ==-1) {return -1;}
                }

            }
        }
        return depth;}
    catch (StackOverflowError e){return -1;}

    }
    @Override
    public int[][] depth() {
        int[][] ans = new int[width()][height()];
        for (int i = 0; i < width(); i++) {
            for (int j = 0; j < height(); j++) {
                ans[i][j] = set_depth(table[i][j]);
                this.table[i][j].setOrder(ans[i][j]);
            }
        }
        return ans;
    }

    @Override
    public void load(String fileName) throws IOException {
        FileReader fr = new FileReader(fileName);




    }

    @Override
    public void save(String fileName) throws IOException {
        FileWriter fw = new FileWriter(fileName);
        fw.write("This is Txt File for " + fileName + "Using Ex2_Cs101 Sol");
        fw.write("\n");
        for (int i = 0; i < width(); i++) {
            for (int j = 0; j < height(); j++) {
                if (!table[i][j].getData().isEmpty())
                { fw.write(table[i][j].getName()+" "+table[i][j].getData());}
                fw.write("\n");
            }
        }
        fw.close();
    }
    /**Uses Evaluate to det the printed value for call (x,y)**/
    @Override
    public String eval(int x, int y) {
        Cell ans = null;
        if(get(x,y)!=null) {ans = get(x,y);}
        return String.valueOf(eValuate(ans));
        }

     /**
      * this method calculates the final value of a cell (with dependencies)
      * @param a is checked - if a formula return from computeform. if contains subcells - recursivly calculates the subcells first.
      * finally returns the value as a double.
      *
      * **/
    public double eValuate(Cell a)
    {
        double value=0;
        String str2eval;
        int a_depth = set_depth(a);
        if (SCell.isNumber(a.getData())){return Double.parseDouble(a.getData());}
        if (SCell.is_form(a.getData())&&a_depth==0&&!a.getData().isEmpty())
        {   str2eval = a.getData();
            return computeFrom(str2eval);
        }
        if (SCell.is_form(a.getData())&&a_depth!=0&&!a.getData().isEmpty())
        {
            str2eval = a.getData().replace(getSubCells(a).getName(),String.valueOf(eValuate(getSubCells(a))));
            Cell sub = new SCell(str2eval);
            if (contCellRef(str2eval)){ value = value + eValuate(sub);}
            if (!contCellRef(str2eval)) value =  value + computeFrom(str2eval);
        }
        return value;

    }
    /**
     * this function return a Cell value for cell's subcell (if exists)
     * @param cell's data is checked.
     * **/
    public Cell getSubCells(Cell cell)
    {   if(cell == null)return null;

        for (int i = 0; i < cell.getData().length(); i++)
        {
            if (Character.isLetter(cell.getData().charAt(i))&&(Extras.isCellRef(cell.getData().substring(i,i+1))||Extras.isCellRef(cell.getData().substring(i,i+2))))
                return table[Extras.char2num(cell.getData().charAt(i))][Integer.parseInt(cell.getData().substring(i+1,i+2))];
        }
        return null;
    }
    public Cell getSubCells(String str)
    {
        Cell ans = new SCell(str);
        return getSubCells(ans);
    }
    /**
     * boolean method - return if a given string contains a cell ref
     * Checks if a given string contains a valid cell reference.
     * @param str The input string to be checked. The string may contain letters, numbers, and other characters.
     *           Examples: "A1", "12+A2", "abc".
     **/
    private boolean contCellRef(String str)
    {  boolean ans = false;
        for (int i = 0; i < str.length(); i++)
        {
            if (Character.isLetter(str.charAt(i)))
            {ans =  Extras.isCellRef(str.substring(i,i+2));
                if (ans)return ans;
            }
            ans = Extras.isCellRef(str.substring(i,i+1));
        }
        return ans;
    }
/**
 * Computes the result of a mathematical expression given as a string.
 * The function supports basic arithmetic operations (+, -, *, /) and handles parentheses.
 * @param str The mathematical expression as a string.
 *            The expression can include numbers, arithmetic operators, and parentheses.
 *            Examples: "1 + 2", "(3 + 5) * 2", "12 / (4 - 2)"
 **/
    public static double computeFrom(String str) {
        String strP = str;
        strP = strP.replaceAll("\\s", ""); // remove all spaces
        strP = strP.replaceAll("=", ""); // remove all equal signs
        if (SCell.isNumber(strP))return Double.parseDouble(strP);
        if (strP.contains("(")) {
            int open = strP.lastIndexOf('(');
            int close = strP.indexOf(')', open);
            String inside = strP.substring(open + 1, close);
            double value = computeFrom(inside);
            return computeFrom(strP.substring(0, open) + value + strP.substring(close + 1));
        }

        for (int i = strP.length() - 1; i >= 0; i--) {
            char c = strP.charAt(i);
            if (c == '+' || c == '-') {
                double left = computeFrom(strP.substring(0, i));
                double right = computeFrom(strP.substring(i + 1));
                if (c == '+') return left + right;
                else return left - right;
            }
        }

        for (int i = strP.length() - 1; i >= 0; i--) {
            char c = strP.charAt(i);
            if (c == '*' || c == '/') {
                double left = computeFrom(strP.substring(0, i));
                double right = computeFrom(strP.substring(i + 1));
                if (c == '*') return left * right;
                else return left / right;
            }
        }
        return Double.parseDouble(strP);
    }
}
