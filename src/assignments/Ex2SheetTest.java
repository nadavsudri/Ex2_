package assignments;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class Ex2SheetTest {

    @Test
    void set_depth() {
    }

    @Test
    void depth() {
        String a = "=a0";
        System.out.println(SCell.is_form(a));

    }
    @Test
    void tester1()
    {
        Sheet s = new Ex2Sheet(2,2);
        String[] ss = {"=1+1","=a0+1","2","11"};
        for (int i =0; i<2; i++)
        {
            for (int j =0; j<2; j++)
            {
                s.set(i,j,ss[i+j]);
                System.out.println(s.get(i,j));
                System.out.println(s.get(i,j).getName());
            }
        }

    }
}