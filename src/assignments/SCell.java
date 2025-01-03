package assignments;
// Add your documentation below:

import java.util.Objects;

public class SCell implements Cell {
    private String line;
    private int type;
    private String name;
    private int dpth;

    // Add your code here

    public SCell(String s) {

        setData(s);
        this.type = getType();
    }
    public String getName(){return name;}
    public void setName(String n){ this.name = n;}
    @Override
    public int getOrder() {
        return this.dpth;
    }
/**
 * this method receives a string and checks for ivalid chars, combinations and sequences.
 * validates every parenthesis open is being closed
 * @param str and return if the string is a valid formula or not.
 * **/
public static boolean is_form(String str) {
    // Null check
    if (str == null) {
        return false;
    }
    if(str.length() <2) {return false;}

    // Check for invalid characters or absence of '=' at the start
    if (!Extras.cont_invalid(str) || !str.startsWith("=")) {
        return false;
    }

    // Ensure there's only one '=' at the start and not elsewhere
    if (str.substring(1).contains("=")) {
        return false;
    }

    // Check if the last character is a letter (invalid in formulas)
    if (Character.isLetter(str.charAt(str.length() - 1))) {
        return false;
    }

    // Ensure balanced parentheses
    if (Extras.inst_counter(str, '(') != Extras.inst_counter(str, ')')) {
        return false;
    }

    // Iterate through the string to validate its structure
    for (int i = 0; i < str.length() - 1; i++) {
        char currentChar = str.charAt(i);
        char nextChar = str.charAt(i + 1);

        // Prevent consecutive dots ".."
        if (currentChar == '.' && nextChar == '.') {
            return false;
        }

        // Prevent invalid decimal points like "0.a2"
        if (currentChar == '.' && !Character.isDigit(nextChar)) {
            return false;
        }

        // Prevent invalid letters without a digit following
        if (Character.isLetter(currentChar) && !Character.isDigit(nextChar)) {
            return false;
        }

        // Validate operators (must be followed by a letter, digit, or '(')
        if (Extras.is_opt(currentChar) && !(Character.isLetter(nextChar) || Character.isDigit(nextChar) || nextChar == '(')) {
            return false;
        }
    }

    // Special case: If the formula ends with '.', it's invalid
    if (str.endsWith(".")) {
        return false;
    }

    // If all checks passed, the string is a valid formula
    return true;
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
    } // return the data String

    /**
     * return if the given string is a number (ex. 2.4 / 689540 / 0000.0)
     * **/
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
/**
 * returns the type of cell based on its data
 * return Number if data is representing number
 * return Form if data is representing formula
 * return text if data is representing text
 * return Err_Form if the data is formula but invalid (Using isForm)
 * return Err_Cycle if subcell refrence is Endless (ex a1 = "a1 +3")
 * **/
    @Override
    public int getType() {
        if (this.type == Ex2Utils.ERR_FORM_FORMAT) return Ex2Utils.ERR_FORM_FORMAT;
        if(getData()==null){return Ex2Utils.TEXT;}
        if (getData().isEmpty())return Ex2Utils.TEXT;
        if(Objects.equals(getData(), Ex2Utils.ERR_FORM)){return Ex2Utils.ERR_FORM_FORMAT;}
        if(this.dpth==Ex2Utils.ERR_CYCLE_FORM)return Ex2Utils.ERR_CYCLE_FORM;
        String tmp_dta = getData();
        if(getData().charAt(0)=='='&&!is_form(getData())|| tmp_dta.replace("=","").isEmpty()){return Ex2Utils.ERR_FORM_FORMAT;}
        if (isText(getData())) {return Ex2Utils.TEXT;}
        if (isNumber(getData())) {return Ex2Utils.NUMBER;}
        if (is_form(getData())) {return Ex2Utils.FORM;}
        return -1;
    }

    @Override
    public void setType(int t) {
        type = t;
    }
    public static boolean is_valid(Cell cell)
    {
        return (is_form(cell.getData()) || isNumber(cell.getData())|| isText(cell.getData()));
    }

    @Override
    public void setOrder(int t) {
    this.dpth = t;
    }
}
