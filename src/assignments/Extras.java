package assignments;

public class Extras {

public static int char2num(char c){
    if (c=='a'||c=='A') return 0;
    else if (c=='b'||c=='B') return 1;
    else if (c=='c'||c=='C') return 2;
    else if (c=='d'||c=='D') return 3;
    else if (c=='e'||c=='E') return 4;
    else if (c=='f'||c=='F') return 5;
    else if (c=='g'||c=='G') return 6;
    else if (c=='h'||c=='H') return 7;
    else if (c=='i'||c=='I') return 8;
    else if (c=='j'||c=='J') return 9;
    else if (c=='k'||c=='K') return 10;
    else if (c=='l'||c=='L') return 11;
    else if (c=='m'||c=='M') return 12;
    else if (c=='n'||c=='N') return 13;
    else if (c=='o'||c=='O') return 14;
    else if (c=='p'||c=='P') return 15;
    else if (c=='q'||c=='Q') return 16;
    else if (c=='r'||c=='R') return 17;
    else if (c=='s'||c=='S') return 18;
    else if (c=='t'||c=='T') return 19;
    else if (c=='u'||c=='U') return 20;
    else if (c=='v'||c=='V') return 21;
    else if (c=='w'||c=='W') return 22;
    else if (c=='x'||c=='X') return 23;
    else if (c=='y'||c=='Y') return 24;
    else if (c=='z'||c=='Z') return 25;
    return -1;
}
public  static char int2_char(int x)
{
    String s = "abcdefghijklmnopqrstuvwxyz";
    for (char c : s.toCharArray()) {
        if (char2num(c)==x) return c;
    }
    return 0;
}
    public static int inst_counter(String str, char s) {
        int counter = 0;
        for (char ch : str.toCharArray()) {
            if (ch == s) counter++;
        }
        return counter;
    }
    public static boolean is_opt(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/';
    }
    public static boolean cont_invalid(String str) {
        String vaild = "=.QWERTYUIOPASDFGHJKLZXCVBNMasdfghjklqwertyuiopzxcvbnm1234567890+/-*()";
        for (char ch : str.toCharArray()) {
            if (vaild.indexOf(ch) == -1) return false;
        }
        return true;
    }

    public static boolean isCellRef(String str) {
        if (str == null) {
            return false;
        }
        if (str.length() > 3 || str.length() < 2) {
            return false;
        }
        if (Character.isLetter(str.charAt(0))) {
            if (Character.isDigit(str.charAt(1)) && str.length() == 2) {
                return true;
            }
            if (str.length() == 3 && Character.isDigit(str.charAt(1)) && Character.isDigit(str.charAt(2))) {
                return true;
            }

        }
        return false;

    }
}
