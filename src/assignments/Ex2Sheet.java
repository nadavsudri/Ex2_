package assignments;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
/**
 * Ex2Sheet Class Documentation
 * Overview:
 * The Ex2Sheet class represents a digital spreadsheet with cells that can contain text, numbers, or formulas.
 * It provides methods for manipulating and evaluating the spreadsheet, handling circular references, and performing arithmetic operations.
 * ___________________________________
 * ___Methods___:
 * value(int x, int y)
 * get(int x, int y)
 * get(String cords)
 * width()
 * height()
 * set(int x, int y, String s)
 * eval()
 * isIn(int xx, int yy)
 * set_depth(Cell a)
 * depth()
 * clear()
 * load(String fileName) throws IOException
 * save(String fileName) throws IOException
 * eval(int x, int y)
 * eValuate(Cell a)
 * getSubCells(Cell cell)
 * getSubCells(String str)
 * contCellRef(String str)
 * computeFrom(String str)
 *
 * documentation for each method exists next to the method itself.
 * **/
public class Ex2Sheet implements Sheet {
    private Cell[][] table;
   private Set<Cell> visitedCells = new HashSet<>(); //  (used in set_Depth) Track Subcells that was chacked

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
/**
 * returns the printed value of a cell
 * if Err - return the ERR string
 * if number - return the number as a double
 * id text - return the text
 * if formula - calculate it (with dependencies)
 * **/
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
    public int width() { // returns this.length
        return table.length;
    }
    @Override
    public int height() // returns this.height
    {
        return table[0].length;
    }
    @Override
    public void set(int x, int y, String s) {
        Cell c = new SCell(s);
        table[x][y] = c;
        c.setName(Extras.int2_char(x)+""+y);
    }
    /**uses the main evaluate function **below** to calculate values for all @table
     * the void update the printed value of each cell.
     * **/
    @Override
    public void eval() {
        int[][] dd = depth();
        for (int i = 0; i < width(); i++) {
            for (int j = 0; j < height(); j++) {
                table[i][j].setOrder(dd[i][j]);
                table[i][j].setType(table[i][j].getType());
                if (SCell.is_form(table[i][j].getData())&&getSubCells(table[i][j])!=null)
                {
                    Cell sub = getSubCells(table[i][j] );
                    if(!SCell.is_valid(sub)||sub.getData().isEmpty())
                        table[i][j].setType(Ex2Utils.ERR_FORM_FORMAT);
                }
            }
        }
    }
    @Override
    public boolean isIn(int xx, int yy) {
        boolean ans = xx>=0 && yy>=0;
        if(ans)
        {Cell cell = get(xx, yy);
        return cell!=null;}
        return false;
    }
    /**
     * this method recives a Cell and return the Depth of it.
     * the method uses a Set (defined above) to detect circular references before an STOF is accuring
     * @param a is being checked recursively when each the depth subcell that 'a' contains ia added to it.
     * for "1+a2" when a2 is: "=12*12" the function returns 1.
     *
     * **/
    public int set_depth(Cell a) {
        if (a.getType() == Ex2Utils.TEXT) {return 0;}
        if (a.getType() == Ex2Utils.NUMBER) {return 0;}
        if (!SCell.is_form(a.getData())) {return Ex2Utils.ERR_FORM_FORMAT;}
        if (visitedCells.contains(a)) {return -1;}
        try {
            visitedCells.add(a); // mark the current cell as checked
            int depth = 0;
            String str = a.getData();
            for (int i = 0; i < str.length(); i++) {
                if (Character.isLetter(str.charAt(i))) {
                    int start = i;
                    while (i < str.length() && Character.isLetter(str.charAt(i))) {i++;}
                    String column = str.substring(start, i);
                    start = i;
                    while (i < str.length() && Character.isDigit(str.charAt(i))) {i++;}
                    String row = str.substring(start, i);
                    if (!row.isEmpty() && Extras.isCellRef(column + row)) {
                        String b_name = column + row;
                        Cell subCell = table[Extras.char2num(b_name.charAt(0))][Integer.parseInt(b_name.substring(1))];
                        if (subCell == a) {return -1;}
                        int sub_depth = set_depth(subCell);
                        if (sub_depth == -1) {return -1;}
                        depth = Math.max(depth, sub_depth + 1);
                    }
                }
            }
            visitedCells.remove(a);
            return depth;
        } catch (StackOverflowError e) {
            return -1;
        }
    }
/**
 * this method returns an int array that reflects the depth of each cell at @table.
 * uses @set_depth method
 * **/
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
    //clear this.table
    private void clear()
    {
        for (int i = 0; i < width(); i++) {
            for (int j = 0; j < height(); j++) {
                this.table[i][j] = new SCell("");
            }
        }
    }
    /**
     * this method loads the saved txt file and assignes each cell with its correct value.
     * uses java's Filereader and Java's Bufferreader
     * **/
    @Override
    public void load(String fileName) throws IOException  {
        clear();
        FileReader fr = new FileReader(fileName);
        BufferedReader br = new BufferedReader(fr);
        String line = br.readLine();
        int count = 0;
        while (line != null)
        {
            line = br.readLine();
            count++;
            if(line != null&&!line.isEmpty()&&count!=0&&Extras.isCellRef(line.substring(0,2).replace(" ","")))
            {
                String cord = line.substring(0,2).replace(" ","");
                int x = Extras.get_x(cord);
                int y = Extras.get_y(cord);
                set(x, y,line.replace(cord,"").replace(" ",""));
            }

        }
    }
    /**
     * this method saves the spreadsheet as a text file using java's filereader.
     * ex:
     *      This is Txt File for /Users/nadavsudri/Desktop/School \ Study/UntitledUsing Ex2_Cs101 Sol
     *      a0 1
     *      a1 2
     *      a2 3
     *      a3 =5*a1
     *      a4 =123
     *      a5 123
     *      a6 axa32dd23d=
     *      a7 321wsdx3x
     *      a8 =a2
     *      a9 =1as21
     * **/
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
      *          if a contais a subcell - it calculate the subcell (ex: 1+A1 [A1="12"] ->  1+calc(A1) -> 1+12 -> 13)
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
        {   Cell c = getSubCells(a);
            String name = c.getName();
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
    public Cell getSubCells(Cell cell) {
        if (cell == null) return null;

        for (int i = 0; i < cell.getData().length(); i++) {
            char currentChar = cell.getData().charAt(i);
            if (Character.isLetter(currentChar)) {
                currentChar = Character.toUpperCase(currentChar); // Normalize to uppercase
                if (currentChar >= 'A' && currentChar <= 'Z') {
                    if (i + 1 < cell.getData().length() && Character.isDigit(cell.getData().charAt(i + 1))) {
                        int rowEnd = i + 2;
                        if (rowEnd < cell.getData().length() && Character.isDigit(cell.getData().charAt(rowEnd))) {
                            rowEnd++;
                        }
                        int row = Integer.parseInt(cell.getData().substring(i + 1, rowEnd));
                        if (row >= 0 && row <= 99) {
                            return table[Extras.char2num(currentChar)][row];
                        }
                    }
                }
            }
        }

        // No valid subcell reference found
        return null;
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
