package assignments;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SCellTest {
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

}