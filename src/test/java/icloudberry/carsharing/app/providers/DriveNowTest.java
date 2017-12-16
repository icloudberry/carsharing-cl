package icloudberry.carsharing.app.providers;

import icloudberry.carsharing.app.Result;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static java.math.MathContext.DECIMAL32;
import static org.junit.Assert.*;

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
    public void calculatePrice_ValidInput_BigDist_ShouldReturnValidValue() {
        DriveNow cut = new DriveNow();
        BigDecimal expected = new BigDecimal(73.3, DECIMAL32).setScale(2, RoundingMode.CEILING);
        assertEquals(new Result("default", expected),
                cut.calculatePrice(250, 150, false));
    }

    @Test
    public void calculatePrice_ValidInput_BigDist_LongDrive_ShouldReturnValidValue() {
        DriveNow cut = new DriveNow();
        BigDecimal expected = new BigDecimal(73.3, DECIMAL32).setScale(2, RoundingMode.CEILING);
        assertEquals(new Result("default", expected),
                cut.calculatePrice(250, 300, false));
    }

    @Test
    public void calculatePrice_ValidInput_ShortDist_ShouldReturnValidValue() {
        DriveNow cut = new DriveNow();
        BigDecimal expected = new BigDecimal(18.2, DECIMAL32).setScale(2, RoundingMode.CEILING);
        assertEquals(new Result("default", expected), cut.calculatePrice(50, 65, false));
    }

    @Test
    public void calculatePrice_ValidInput_ShortDist_LongDrive_ShouldReturnValidValue() {
        DriveNow cut = new DriveNow();
        BigDecimal expected = new BigDecimal(54, DECIMAL32).setScale(2, RoundingMode.CEILING);
        assertEquals(new Result("SIX", expected), cut.calculatePrice(50, 210, false));
    }

    @Test
    public void calculatePrice_ValidInput_Airport_ShouldReturnValidValue() {
        DriveNow cut = new DriveNow();
        BigDecimal expected = new BigDecimal(21.8, DECIMAL32).setScale(2, RoundingMode.CEILING);
        assertEquals(new Result("default", expected), cut.calculatePrice(60, 35, true));
    }

}