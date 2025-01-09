package assignments;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
class Ex2SheetTest {
  Ex2Sheet ex2Sheet = new Ex2Sheet(26,99);
  void set_for_test()
  {
      for (int i = 0; i<ex2Sheet.width();i++)
      {
          for(int j=0;j<ex2Sheet.height();j++)
          {
              ex2Sheet.set(i,j,"");
          }
      }
  }
  @Test
  void getSubcells () throws IOException {
      set_for_test();
      ex2Sheet.set(0,0,"=1+1");
      ex2Sheet.set(0,1,"=1+A0");
      ex2Sheet.set(0,2,"=A1+1");
      ex2Sheet.set(0,3,"=A1+A0");
      String []  subCords =  {"a0","a1"};
      assertEquals("a0",ex2Sheet.getSubCells(ex2Sheet.get(0,1)).getName());
      assertEquals("a1",ex2Sheet.getSubCells(ex2Sheet.get(0,2)).getName());
      assertEquals("a1",ex2Sheet.getSubCells(ex2Sheet.get(0,3)).getName());
      ex2Sheet.set(0,3,"=12+A0"); // after recurcive calculation
      assertEquals("a0",ex2Sheet.getSubCells(ex2Sheet.get(0,3)).getName());



  }
  @Test
  void evaluate()
  {
      set_for_test();
      ex2Sheet.set(0,0,"=12+3"); // -> 15
      ex2Sheet.set(1,0,"=2*A0");//  - > 30
      ex2Sheet.set(12,3,"=1+b0"); // -> 31
      ex2Sheet.set(1,3,"=(12+b0)*b0+(10*m3)"); //   -> 1570
      ex2Sheet.set(16,91,"=0.000000"); // -> 0
      Cell[] cells = {ex2Sheet.get(0,0),ex2Sheet.get(1,0),ex2Sheet.get(12,3),ex2Sheet.get(1,3),ex2Sheet.get(16,91)};
      double []ev = {15,30,31,1570,0};
      for (int i=0;i<ev.length;i++)
      {double s = ex2Sheet.eValuate(cells[i]);
          assertEquals(s,ev[i]);
      }

  }
    @Test
    void set_depth() {
        ex2Sheet.set(0,0,"=12+3"); // A0 -> 15
        ex2Sheet.set(1,0,"=2*A0");// B0 - > 30 (dpth -> 1)
        ex2Sheet.set(12,3,"=1+B0"); // M3 -> 31 (dpth ->2)
        ex2Sheet.set(1,3,"=1+B3"); // B3 -> Err (dpth ->-1)
        ex2Sheet.set(4,11,"=e11"); // E72 -> 61 (dpth ->3)
        int [] dpths = {-1,0,1,2,-1};
        Cell [] cells = {ex2Sheet.get(1,3),ex2Sheet.get(0,0),ex2Sheet.get(1,0),ex2Sheet.get(12,3),ex2Sheet.get(4,11)};
        for (int i = 0; i<5;i++)
        {
            assertEquals(dpths[i],ex2Sheet.set_depth(cells[i]));
        }

    }
    @Test
    void computeform()
    {
        String f1 = "(1+1)*12-(1/2)*12-4+(3*2)-(4/2)*5+6";  // Value: 16.0
        String f2 = "5*3+2-(8/4)*6+(9-3)*2-(7/2)*4";  // Value: 3.0
        String f3 = "10+(20-5)*2/5+((8-4)*3)-2*4";  // Value: 20.0
        String f4 = "15/(3+2)*4-7+(2+5)*3-(12/4)";  // Value: 23.0
        String f5 = "(6+2)*3-(12/4)+5+(8-3)*(2+1)";  // Value: 41.0
        String f6 = "9+4*(2-1)*6+(8/2)*3-5";  // Value: 40.0
        String f7 = "(8*3)-(2+1)*4+5+(12/3)*2";  // Value: 25.0
        String f8 = "7*5+(3/3)*4-2+((9-6)*3)";  // Value: 46.0
        String f9 = "(10-5)*(2+3)+6+(15/3)-4";  // Value: 32.0
        String f10 = "4*(9/3)+5-2*(6-4)+(7*3)-9";  // Value: 25.0

        String [] strs = {f1,f2,f3,f4,f5,f6,f7,f8,f9,f10};
        Double[] res = {16.0, 3.0, 20.0, 23.0, 41.0, 40.0, 25.0, 46.0, 32.0, 25.0};
        for (int i = 0; i < strs.length;i++)
        {
            assertEquals(res[i],Ex2Sheet.computeFrom(strs[i]));
        }
    }

    @Test
    void value() {
      set_for_test();
      ex2Sheet.set(0,0,"=12+3");
      ex2Sheet.set(1,0,"=2*a0");
      ex2Sheet.set(12,3,"=1+b0");
      ex2Sheet.set(1,3,"=1-11+10+a0");
      ex2Sheet.set(16,91,"=24=23");
      Cell [] cells = {ex2Sheet.get(0,0),ex2Sheet.get(1,0),ex2Sheet.get(12,3),ex2Sheet.get(1,3),ex2Sheet.get(16,91)};
      String[] vals = {"15.0","30.0","31.0","15.0","ERR_Form_Format"};
      assertEquals(vals[0],ex2Sheet.value(0,0));
      assertEquals(vals[1],ex2Sheet.value(1,0));
      assertEquals(vals[2],ex2Sheet.value(12,3));
      assertEquals(vals[3],ex2Sheet.value(1,3));
      assertEquals(vals[4],ex2Sheet.value(16,91));




    }
    @Test
    void tester1()
    {
        Cell c = new SCell("=");
        System.out.println(SCell.is_form("=1+1"));

    }
}