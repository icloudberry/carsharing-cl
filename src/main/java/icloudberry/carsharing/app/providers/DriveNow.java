package icloudberry.carsharing.app.providers;

import icloudberry.carsharing.app.Result;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Optional;

import static java.math.MathContext.DECIMAL32;

public class DriveNow implements Provider {

    private static final BigDecimal AIRPORT_FEE = new BigDecimal(12, DECIMAL32);
    private final int MAX_INCL_DIST = 200;
    private final BigDecimal extra_dist_per_km = new BigDecimal(0.29, DECIMAL32);
    private final BigDecimal time_per_min = new BigDecimal(0.28, DECIMAL32);


    @Override
    public Result calculatePrice(int dist, int time, boolean airport) {
        if (dist < 0 || time < 0)
            throw new IllegalArgumentException("Wrong input data! Distance and time cannot be negative");

        BigDecimal defaultPrice = getDefaultPrice(dist, time, airport);

        Optional<Package> packageOptional = Optional.empty();
        if (!airport) {
            packageOptional = Arrays.stream(Package.values())
                    .filter(aPackage -> aPackage.time >= time && getPrice(aPackage, dist).compareTo(defaultPrice) < 0)
                    .min(Comparator.comparing(o -> o.price));
        }

        return packageOptional.map(aPackage -> new Result(aPackage.name(), getPrice(aPackage, dist)))
                .orElseGet(() -> new Result("default", defaultPrice));
    }

    private BigDecimal getPrice(Package aPackage, int dist) {
        BigDecimal price = aPackage.price;
        //distance
        if (dist > aPackage.dist)
            price = price.add(new BigDecimal(dist - aPackage.dist, DECIMAL32).multiply(extra_dist_per_km));

        return price.setScale(2, RoundingMode.CEILING);
    }

    private BigDecimal getDefaultPrice(int dist, int time, boolean airport) {
        BigDecimal price = BigDecimal.ZERO;

        // airport
        if (airport)
            price = price.add(AIRPORT_FEE);

        // time
        price = price.add(new BigDecimal(time, DECIMAL32).multiply(time_per_min));

        //distance
        if (dist > MAX_INCL_DIST)
            price = price.add(new BigDecimal(dist - MAX_INCL_DIST, DECIMAL32).multiply(extra_dist_per_km));

        return price.setScale(2, RoundingMode.CEILING);
    }

    @AllArgsConstructor
    enum Package {
        THREE(3 * 60, 80, new BigDecimal(29, DECIMAL32).setScale(2, RoundingMode.CEILING)),
        SIX(6 * 60, 120, new BigDecimal(54, DECIMAL32).setScale(2, RoundingMode.CEILING)),
        NINE(9 * 60, 200, new BigDecimal(79, DECIMAL32).setScale(2, RoundingMode.CEILING)),
        DAY(24 * 60, 200, new BigDecimal(109, DECIMAL32).setScale(2, RoundingMode.CEILING));

        int time; // in min
        int dist;
        BigDecimal price;
    }

//    3 - 29, 80 km
//6 - 54, 120 km
//9 - 79, 200 km
//24 - 109, 200 km

}
