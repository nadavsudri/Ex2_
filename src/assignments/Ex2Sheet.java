package assignments;
import java.io.IOException;
// Add your documentation below:

public class Ex2Sheet implements Sheet {
    private Cell[][] table;
    // Add your code here

    // ///////////////////
    public Ex2Sheet(int x, int y) {
        table = new SCell[x][y];
        for(int i=0;i<x;i=i+1) {
            for(int j=0;j<y;j=j+1) {
                table[i][j] = new SCell("");
                table[i][j].setName(Extras.int2_char(i)+""+j);
                System.out.println(this.table[i][j].getName());
                table[i][j].setOrder(set_depth(table[i][j]));
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
       switch (c.getType())
       {
           case Ex2Utils.TEXT : return c.getData();
           case Ex2Utils.NUMBER: return String.valueOf(Double.parseDouble(c.getData()));
           case Ex2Utils.FORM: return String.valueOf(eValuate(c));
           case Ex2Utils.ERR_FORM_FORMAT: return "ERR_Form_Format";
           case Ex2Utils.ERR_CYCLE_FORM: return "ERR_Cycle_Form";

       }

        /////////////////////
        return ans;
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
        // Add your code here

        // ///////////////////
    }

    @Override
    public boolean isIn(int xx, int yy) {
        boolean ans = xx>=0 && yy>=0;
        // Add your code here

        /////////////////////
        return ans;
    }
    public  int set_depth (Cell a)
    {   if (a.getType() == Ex2Utils.TEXT) {return 0;}
       if (a.getType() == Ex2Utils.NUMBER) {return 0;}
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
                    return depth+ sub_depth;
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
            }
        }
        return ans;
    }

    @Override
    public void load(String fileName) throws IOException {
        // Add your code here

        /////////////////////
    }

    @Override
    public void save(String fileName) throws IOException {
        // Add your code here

        /////////////////////
    }

    @Override
    public String eval(int x, int y) {
        String ans = null;
        if(get(x,y)!=null) {ans = get(x,y).getData();}
        return String.valueOf(computeFrom(ans));
        }

    public double eValuate(Cell a)
    {
        double value = 0;
        String str2eval;
        int a_depth = set_depth(a);
        if (SCell.isNumber(a.getData())){return Double.parseDouble(a.getData());}
        if (SCell.is_form(a.getData())&&a_depth==0)
        {   str2eval = a.getData();
            return computeFrom(str2eval);
        }
        if (SCell.is_form(a.getData())&&a_depth!=0)
        {
            str2eval = a.getData().replace(getSubCells(a).getName(),String.valueOf(eValuate(getSubCells(a))));
            if (contCellRef(str2eval)){ value = value + eValuate(getSubCells(str2eval));}
            value =  value +computeFrom(str2eval);
        }
        return value;
    }
    public Cell getSubCells(Cell cell)
    {
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

    public static double computeFrom(String str) {
        str = str.replaceAll("\\s", ""); // remove all spaces
        str = str.replaceAll("=", ""); // remove all equal signs


        if (str.contains("(")) {
            int open = str.lastIndexOf('(');
            int close = str.indexOf(')', open);
            String inside = str.substring(open + 1, close);
            double value = computeFrom(inside);
            return computeFrom(str.substring(0, open) + value + str.substring(close + 1));
        }

        for (int i = str.length() - 1; i >= 0; i--) {
            char c = str.charAt(i);
            if (c == '+' || c == '-') {
                double left = computeFrom(str.substring(0, i));
                double right = computeFrom(str.substring(i + 1));
                if (c == '+') return left + right;
                else return left - right;
            }
        }

        for (int i = str.length() - 1; i >= 0; i--) {
            char c = str.charAt(i);
            if (c == '*' || c == '/') {
                double left = computeFrom(str.substring(0, i));
                double right = computeFrom(str.substring(i + 1));
                if (c == '*') return left * right;
                else return left / right;
            }
        }
        return Double.parseDouble(str);
    }
}
