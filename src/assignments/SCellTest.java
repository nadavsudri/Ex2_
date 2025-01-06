package assignments;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SCellTest {
    SCell testCell = new SCell("");
    @Test
    void isform()
    {   //valid
        assertTrue(SCell.is_form("=1+2"));
        assertTrue(SCell.is_form("=A1+B2"));
        assertTrue(SCell.is_form("=(1+1+(1+1+(1)))"));
        assertTrue(SCell.is_form("=123*456"));
        //invalid
        assertFalse(SCell.is_form(null));
        assertFalse(SCell.is_form(""));
        assertFalse(SCell.is_form("123+456"));
        assertFalse(SCell.is_form("=A1++B2"));
        assertFalse(SCell.is_form("=A1+*B2"));
        assertFalse(SCell.is_form("=A1..B2"));
        assertFalse(SCell.is_form("=A1+."));
        assertFalse(SCell.is_form("=A1+ B2"));
        System.out.println(SCell.is_form("=1j1"));
    }
    @Test
    void isnumber()
    {
        assertTrue(SCell.isNumber("12345432"));
        assertTrue(SCell.isNumber("1234.229473887"));
        assertTrue(SCell.isNumber("000000.00000001"));
        assertFalse(SCell.isNumber("000w000.00000001"));
        assertFalse(SCell.isNumber("=123445688"));
        assertFalse(SCell.isNumber("00000000000000..1"));
    }
    @Test
    void isText()
    {
        assertTrue(SCell.isText("A1"));
        assertTrue(SCell.isText("A======1"));
        assertTrue(SCell.isText("1+1+1+1+1+1"));
        assertFalse(SCell.isText("=1+1+1+1+1+1"));
        assertFalse(SCell.isText("1.1"));
        assertFalse(SCell.isText("=A1+A2"));
    }

    @Test
    void getType()
    {
        testCell.setData("1+1");
        assertEquals(Ex2Utils.TEXT, testCell.getType());
        testCell.setData("=1+1");
        assertEquals(Ex2Utils.FORM, testCell.getType());
        testCell.setData("=1+a+11+w3s");
        assertEquals(Ex2Utils.ERR_FORM_FORMAT, testCell.getType());
        testCell.setData("11");
        assertEquals(Ex2Utils.NUMBER, testCell.getType());
        testCell.setData("=aa==12-311//32+w3s");
        assertEquals(Ex2Utils.ERR_FORM_FORMAT, testCell.getType());
        testCell.setData("1.1.1.1.1.1.1.1");
        assertEquals(Ex2Utils.TEXT, testCell.getType());
    }

}