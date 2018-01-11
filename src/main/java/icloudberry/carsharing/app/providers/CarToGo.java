package icloudberry.carsharing.app.providers;

import icloudberry.carsharing.app.Result;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Optional;

import static java.math.MathContext.DECIMAL32;

public class CarToGo implements Provider {

    private static final BigDecimal AIRPORT_FEE = new BigDecimal(12, DECIMAL32);
    private final int MAX_INCL_DIST = 200;
    private final BigDecimal extra_dist_per_km = new BigDecimal(0.29, DECIMAL32);
    private final BigDecimal time_per_min = new BigDecimal(0.31, DECIMAL32);

    //todo add package + normal minutes

    @Override
    public String getName() {
        return "Car2Go";
    }

    @Override
    public Result calculatePrice(int dist, int time, boolean airport) {
        if (dist < 0 || time < 0)
            throw new IllegalArgumentException();
        if (dist == 0 || time == 0)
            return new Result("default", BigDecimal.ZERO.setScale(2, RoundingMode.CEILING));

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

    private BigDecimal getPrice(Package aPackage, int dist) {
        BigDecimal price = aPackage.price;
        //distance
        if (dist > aPackage.dist)
            price = price.add(new BigDecimal(dist - aPackage.dist, DECIMAL32).multiply(extra_dist_per_km));

        return price.setScale(2, RoundingMode.CEILING);
}

    @AllArgsConstructor
    enum Package {
        TWO(2 * 60, 80, new BigDecimal(19.9, DECIMAL32).setScale(2, RoundingMode.CEILING)),
        FOUR(4 * 60, 120, new BigDecimal(34.9, DECIMAL32).setScale(2, RoundingMode.CEILING)),
        SIX(6 * 60, 160, new BigDecimal(49.9, DECIMAL32).setScale(2, RoundingMode.CEILING)),
        DAY(24 * 60, 200, new BigDecimal(99, DECIMAL32).setScale(2, RoundingMode.CEILING));

        int time; // in min
        int dist;
        BigDecimal price;
    }

}
