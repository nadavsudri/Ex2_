package assignments;
// Add your documentation below:

public class SCell implements Cell {
    private String line;
    private int type;
    private String name;
    private int dpth;

    // Add your code here

    public SCell(String s) {
        setData(s);
    }
    public String getName(){return name;}
    public void setName(String n){ this.name = n;}
    @Override
    public int getOrder() {
        return this.dpth;

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

        if (getData().isEmpty())return Ex2Utils.TEXT;
        if(this.dpth==Ex2Utils.ERR_CYCLE_FORM)return Ex2Utils.ERR_CYCLE_FORM;
        if(getData().charAt(0)=='='&&!is_form(getData())){return Ex2Utils.ERR_FORM_FORMAT;}
        if (isText(getData())) {return Ex2Utils.TEXT;}
        if (isNumber(getData())) {return Ex2Utils.NUMBER;}
        if (is_form(getData())) {return Ex2Utils.FORM;}
        return -1;
    }

    @Override
    public void setType(int t) {
        type = t;
    }

    @Override
    public void setOrder(int t) {
    this.dpth = t;
    }
}
