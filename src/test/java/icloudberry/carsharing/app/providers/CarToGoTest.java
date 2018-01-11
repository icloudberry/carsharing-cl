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
public class CarToGoTest {

    /**
     * Under test
     */
    private Provider carToGo = new CarToGo();

    @Test(expected = IllegalArgumentException.class)
    public void calculatePrice_WhenBadInput_shouldThrowException() {
        carToGo.calculatePrice(-5, -3, false);
    }

    @Test
    public void calculatePrice_Zero_shouldReturnZero() {
        BigDecimal expected = BigDecimal.ZERO.setScale(2, RoundingMode.CEILING);
        Result actual = carToGo.calculatePrice(0, 0, false);
        assertEquals(new Result("default", expected), actual);
    }

    @Test
    @Parameters({
//            "dist, time, expected price, expected package",
            "250, 150, 61, default",
            "250, 300, 76, SIX",
            "50, 65, 19.9, TWO",
            "50, 210, 34.9, FOUR",
            "80, 180, 34.9, FOUR",
            "80, 90, 19.9, TWO",
            "120, 180, 34.9, FOUR"
    })
    public void calculatePrice(int dist, int time, double exp, String resType) {
        BigDecimal expected = getExpected(exp);
        Result actual = carToGo.calculatePrice(dist, time, false);
        assertEquals(new Result(resType, expected), actual);
    }

    @Test
    public void calculatePrice_Airport() {
        BigDecimal expected = getExpected(22.85);
        Result actual = carToGo.calculatePrice(60, 35, true);
        assertEquals(new Result("default", expected), actual);
    }

    private BigDecimal getExpected(double val) {
        return new BigDecimal(val, DECIMAL32).setScale(2, RoundingMode.CEILING);
    }
}