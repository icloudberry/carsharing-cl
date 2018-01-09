package icloudberry.carsharing.app.providers;

import icloudberry.carsharing.app.Result;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static java.math.MathContext.DECIMAL32;
import static org.junit.Assert.*;

@RunWith(JUnitParamsRunner.class)
public class DriveNowTest {

    @Test(expected = IllegalArgumentException.class)
    public void calculatePrice_WhenBadInput_shouldThrowException() {
        DriveNow cut = new DriveNow();
        cut.calculatePrice(-5, -3, false);
    }

    @Test
    public void calculatePrice_Zero_shouldReturnZero() {
        DriveNow cut = new DriveNow();
        BigDecimal expected = BigDecimal.ZERO.setScale(2, RoundingMode.CEILING);
        assertEquals(new Result("default", expected), cut.calculatePrice(0, 0, false));
    }

    @Test
    @Parameters({
//            "dist, time, expected price, expected package",
            "250, 150, 56.5, default",
            "250, 300, 91.7, SIX",
            "50, 65, 18.2, default",
            "50, 210, 54.0, SIX",
            "80, 180, 29, THREE",
            "80, 90, 25.2, default",
            "120, 180, 40.6, THREE"
    })
    public void calculatePrice(int dist, int time, double exp, String resType) {
        DriveNow cut = new DriveNow();
        BigDecimal expected = getExpected(exp);
        assertEquals(new Result(resType, expected),
                cut.calculatePrice(dist, time, false));
    }

    @Test
    public void calculatePrice_Airport() {
        DriveNow cut = new DriveNow();
        BigDecimal expected = getExpected(21.8);
        assertEquals(new Result("default", expected), cut.calculatePrice(60, 35, true));
    }

    private BigDecimal getExpected(double val) {
        return new BigDecimal(val, DECIMAL32).setScale(2, RoundingMode.CEILING);
    }

}