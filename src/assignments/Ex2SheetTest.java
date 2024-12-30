package assignments;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class Ex2SheetTest {

    @Test
    void set_depth() {
        Ex2Sheet ex2Sheet = new Ex2Sheet(1,1);
        ex2Sheet.set(0,0,"=a0");
        System.out.println((ex2Sheet.get(0,0).getOrder()));
        System.out.println((ex2Sheet.get(0,0).getType()));
        System.out.println(ex2Sheet.set_depth(ex2Sheet.get(0,0)));

    }

    @Test
    void depth() {
        String a = "=a0+a1";
        System.out.println(SCell.is_form(a));

    }
    @Test
    void tester1()
    {
        Sheet s = new Ex2Sheet(2,2);
        String[] ss = {"=1+1","=a1+1","=a0+a1","11"};
        int inde = 0;
        for (int i =0; i<2; i++)
        {
            for (int j =0; j<2; j++)
            {
                s.set(i,j,ss[inde]);
                //System.out.println(s.get(i,j));
                System.out.println(s.get(i,j).getName()+" -> "+s.get(i,j)+" -> "+ s.eval(i,j));
                //System.out.println(set_depth(s.get(1,0)));
                inde ++;
                //System.out.println(s.get(i,j).getName());
            }
        }

    }
}