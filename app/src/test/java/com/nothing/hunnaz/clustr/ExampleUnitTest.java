package com.nothing.hunnaz.clustr;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    // Months

    @Test
    public void valid_date_month_with_zero() throws Exception {
        String month = "08";
        String day = "30";
        String year = "1996";
        Date testDOB = new Date(month, day, year);
        assertEquals(testDOB.confirmDate(), true);
    }

    @Test
    public void valid_date_month_with_no_zero() throws Exception {
        String month = "8";
        String day = "30";
        String year = "1996";
        Date testDOB = new Date(month, day, year);
        assertEquals(testDOB.confirmDate(), true);
    }

    @Test
    public void valid_date_month_with_two_digits() throws Exception {
        String month = "11";
        String day = "30";
        String year = "1996";
        Date testDOB = new Date(month, day, year);
        assertEquals(testDOB.confirmDate(), true);
    }

    @Test
    public void valid_date_month_with_two_digits_and_zero() throws Exception {
        String month = "10";
        String day = "30";
        String year = "1996";
        Date testDOB = new Date(month, day, year);
        assertEquals(testDOB.confirmDate(), true);
    }

    @Test
    public void invalid_date_month_with_zero() throws Exception {
        String month = "0";
        String day = "30";
        String year = "1996";
        Date testDOB = new Date(month, day, year);
        assertEquals(testDOB.confirmDate(), false);
    }

    @Test
    public void invalid_date_month_with_thirteen() throws Exception {
        String month = "13";
        String day = "30";
        String year = "1996";
        Date testDOB = new Date(month, day, year);
        assertEquals(testDOB.confirmDate(), false);
    }

    @Test
    public void invalid_date_month_with_empty() throws Exception {
        String month = "";
        String day = "30";
        String year = "1996";
        Date testDOB = new Date(month, day, year);
        assertEquals(testDOB.confirmDate(), false);
    }

    @Test
    public void invalid_date_month_with_letter() throws Exception {
        String month = "a";
        String day = "30";
        String year = "1996";
        Date testDOB = new Date(month, day, year);
        assertEquals(testDOB.confirmDate(), false);
    }

    @Test
    public void invalid_date_month_with_num_then_letter() throws Exception {
        String month = "1a";
        String day = "30";
        String year = "1996";
        Date testDOB = new Date(month, day, year);
        boolean answer = testDOB.confirmDate();
        assertEquals(answer, false);
    }

    @Test
    public void invalid_date_month_with_two_letters() throws Exception {
        String month = "aa";
        String day = "30";
        String year = "1996";
        Date testDOB = new Date(month, day, year);
        boolean answer = testDOB.confirmDate();
        assertEquals(answer, false);
    }

    @Test
    public void invalid_date_month_with_num_then_two_letters() throws Exception {
        String month = "1aa";
        String day = "30";
        String year = "1996";
        Date testDOB = new Date(month, day, year);
        boolean answer = testDOB.confirmDate();
        assertEquals(answer, false);
    }

    // Year tests

    @Test
    public void valid_year_with_two_digits() throws Exception {
        String month = "8";
        String day = "30";
        String year = "96";
        Date testDOB = new Date(month, day, year);
        boolean answer = testDOB.confirmDate();
        assertEquals(answer, true);
    }

    @Test
    public void valid_year_with_four_digits_19() throws Exception {
        String month = "8";
        String day = "30";
        String year = "1996";
        Date testDOB = new Date(month, day, year);
        boolean answer = testDOB.confirmDate();
        assertEquals(answer, true);
    }

    @Test
    public void valid_year_with_four_digits_20() throws Exception {
        String month = "8";
        String day = "30";
        String year = "2010";
        Date testDOB = new Date(month, day, year);
        boolean answer = testDOB.confirmDate();
        assertEquals(answer, true);
    }

    @Test
    public void invalid_year_with_too_early() throws Exception {
        String month = "8";
        String day = "30";
        String year = "800";
        Date testDOB = new Date(month, day, year);
        boolean answer = testDOB.confirmDate();
        assertEquals(answer, false);
    }

    @Test
    public void invalid_year_empty() throws Exception {
        String month = "8";
        String day = "30";
        String year = "";
        Date testDOB = new Date(month, day, year);
        boolean answer = testDOB.confirmDate();
        assertEquals(answer, false);
    }

    @Test
    public void invalid_year_not_born_yet() throws Exception {
        String month = "8";
        String day = "30";
        String year = "2020";
        Date testDOB = new Date(month, day, year);
        boolean answer = testDOB.confirmDate();
        assertEquals(answer, false);
    }
}