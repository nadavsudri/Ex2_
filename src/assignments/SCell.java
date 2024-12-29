package assignments;
// Add your documentation below:

public class SCell implements Cell {
    private String line;
    private int type;
    private String name;

    // Add your code here

    public SCell(String s) {
        // Add your code here
        setData(s);
    }
    public String getName(){return name;}
    public void setName(String n){ this.name = n;}
    @Override
    public int getOrder() {
        if (isNumber(line)||isText(line) )return 0;
        return 1;

    }
    public static boolean is_form(String str) // fails when a1.1 is enterd (cell ref with dec point)
    {
        boolean ans = true;
        if (str == null) {
            return false;
        }
        if (!Extras.cont_invalid(str)) return false;
        if (!str.startsWith("=")) return false;
        if (str.substring(1).contains("=")) return false;
        if (Character.isLetter(str.charAt(str.length() - 1))) {
            return false;
        }
        if (Extras.inst_counter(str, '(') != Extras.inst_counter(str, ')')) {
            return false;
        }
        for (int i = 0; i < str.length() - 1; i++) {
            boolean notdigit = !Character.isDigit(str.charAt(i + 1));
            if (str.charAt(i) == '.' & str.charAt(i + 1) == '.') {
                return false;
            }
            if (str.charAt(i + 1) == '.' & !Character.isDigit(str.charAt(i))) {
                return false;
            }
            if (Character.isLetter(str.charAt(i)) && notdigit) return false;
            if ((Extras.is_opt(str.charAt(i)) && !Character.isLetter(str.charAt(i + 1))) && (Extras.is_opt(str.charAt(i)) && notdigit) && str.charAt(i + 1) != '(')
                return false;
        }
        return ans;
    }

    //@Override
    @Override
    public String toString() {
        return getData();
    }

    @Override
public void setData(String s) {
        line = s;

    }
    @Override
    public String getData() {
        return line;
    }
    public static boolean isNumber(String str) {
        try {
            double s = Double.parseDouble(str);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
    public static boolean isText(String str) {
        return !isNumber(str) && !str.startsWith("=");
    }

    @Override
    public int getType() {
        if (getData().isEmpty())return 1;
        if(getData().charAt(0)=='='&&!is_form(getData())){return -2;}
        if (isText(getData())) {return 1;}
        if (isNumber(getData())) {return 2;}
        if (is_form(getData())) {return 3;}
        return -1;
    }

    @Override
    public void setType(int t) {
        type = t;
    }

    @Override
    public void setOrder(int t) {
        // Add your code here

    }
}
