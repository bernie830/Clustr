package com.nothing.hunnaz.clustr;

import org.junit.Test;

import static org.junit.Assert.*;

public class ClustrJUnitTests {

    @Test
    public void testDateConstructor(){
        String month = "1";
        String day = "25";
        String year = "1990";

        Date testDate = new Date(month, day, year);

        assertEquals(Integer.parseInt(month), testDate.getMonth());
        assertEquals(Integer.parseInt(day), testDate.getDay());
        assertEquals(Integer.parseInt(year), testDate.getYear());
    }
}
