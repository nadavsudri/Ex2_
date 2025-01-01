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
        Cell c = new SCell("=");
        System.out.println(SCell.is_form("=0.a2"));

    }
}