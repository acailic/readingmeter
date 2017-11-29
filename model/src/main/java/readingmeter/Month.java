package readingmeter;

import java.time.DateTimeException;

/*
 Just changed names of 1.8 java time enum.
 */
public enum Month {
    JAN,
    FEB,
    MAR,
    APR,
    MAY,
    JUN,
    JUL,
    AUG,
    SEP,
    OCT,
    NOV,
    DEC;

    private static final  Month[] ENUMS =  Month.values();
    public int getValue() {
        return ordinal() + 1;
    }
    public static  Month of(int month) {
        if (month < 1 || month > 12) {
            throw new DateTimeException("Invalid value for MonthOfYear: " + month);
        }
        return ENUMS[month - 1];
    }

}
