package test;

import org.jfree.data.time.Year;
import org.junit.Test;
import org.jfree.data.time.TimePeriodFormatException;

import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.*;

public class YearTest {
    Year year;

    private void arrange() {
        year = new Year();
    }
    private void arrange(int y) {
        year = new Year(y);
    }

    @Test
    public void testYearDefaultCtor() {
        arrange();
        assertEquals(2025, year.getYear());
    }

    @Test
    public void testYearIntCtor(){
        arrange(2000);
        assertEquals(2000,year.getYear());
    }

    @Test
    public void testGetFirstMillisecond() {
        arrange(2023);
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2023);
        cal.set(Calendar.MONTH, Calendar.JANUARY);
        cal.set(Calendar.DATE, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        long expected = cal.getTimeInMillis();
        assertEquals(expected, year.getFirstMillisecond(cal));
    }


    @Test
    public void testGetLastMillisecond() {
        arrange(2023);
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2023); // fixed from 2022 to 2023
        cal.set(Calendar.MONTH, Calendar.DECEMBER);
        cal.set(Calendar.DATE, 31);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 999);
        long expected = cal.getTimeInMillis();
        assertEquals(expected, year.getLastMillisecond(cal));
    }


    @Test
    public void testPreviousYear() {
        arrange(2020);
        Year prev = (Year) year.previous(); //(Year) to cast it to the year type
        assertEquals(2019, prev.getYear());
    }

    @Test
    public void testNextYear() {
        arrange(2020);
        Year next = (Year) year.next();
        assertEquals(2021,  next.getYear());
    }

    @Test
    public void testEqualYears() {
        Year y1 = new Year(2023);
        Year y2 = new Year(2023);
        Year y3 = new Year(2024);
        assertEquals(y1, y2);
        assertNotEquals(y1, y3);
    }

    @Test
    public void testCompareTo() {
        Year y1 = new Year(2010);
        Year y2 = new Year(2015);
        Year y3 = new Year(2010); // Same as y1

        assertTrue(y1.compareTo(y2) < 0);  // 2010 < 2015 → Negative
        assertTrue(y2.compareTo(y1) > 0);  // 2015 > 2010 → Positive
        assertEquals(0, y1.compareTo(y3)); // 2010 == 2010 → 0
    }

    @Test
    public void testToString(){
        year = new Year(1999);
        assertEquals("1999",year.toString());
    }

    @Test
    public void testParseYear() {
        year = Year.parseYear("2010");
        assertEquals(2010, year.getYear());
    }

    @Test
    public void testConstructorWithDate() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2008);
        cal.set(Calendar.MONTH, Calendar.JUNE);
        cal.set(Calendar.DATE, 15);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        Date date = cal.getTime();

        year = new Year(date);
        assertEquals(2008, year.getYear());
    }

    @Test
    public void testConstructorWithDateTimeZoneLocale() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, 2015);
        cal.set(Calendar.MONTH, Calendar.MARCH);
        cal.set(Calendar.DATE, 10);
        Date date = cal.getTime();

        year = new Year(date, java.util.TimeZone.getTimeZone("UTC"), java.util.Locale.US);
        assertEquals(2015, year.getYear());
    }

    @Test
    public void testMinimumYearBoundary() {
        year = new Year(Year.MINIMUM_YEAR);
        assertEquals(-9999, year.getYear());
    }

    @Test
    public void testMaximumYearBoundary() {
        year = new Year(Year.MAXIMUM_YEAR);
        assertEquals(9999, year.getYear());
    }


    @Test(expected = TimePeriodFormatException.class)
    public void testInvalidParseYear() {
        Year.parseYear("invalidYear");
    }

    @Test
    public void testFailCompareDifferentYearsExpectEqual() {
        Year y1 = new Year(2000);
        Year y2 = new Year(2020);
        assertEquals(0, y1.compareTo(y2));
    }

    @Test(expected = NumberFormatException.class)
    public void testFailExpectWrongException() {
        Year.parseYear("InvalidYearString");
    }

    @Test(expected = NullPointerException.class)
    public void testFailCompareToNull() {
        Year y = new Year(2020);
        y.compareTo(null);
    }

    @Test
    public void testFailEqualsWithDifferentYears() {
        Year y1 = new Year(1999);
        Year y2 = new Year(2001);
        assertTrue(y1.equals(y2));
    }

    @Test
    public void testFailEqualsWithWrongObjectType() {
        Year y = new Year(2023);
        String s = "2023";
        assertTrue(y.equals(s));
    }

}