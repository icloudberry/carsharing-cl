package icloudberry.carsharing.app;

import java.math.BigDecimal;

import static java.math.MathContext.DECIMAL32;

public class App {
    public String getGreeting() {
        return "Hello world.";
    }

    public static void main(String[] args) {
        System.out.println(new App().getGreeting());
        int dist = Integer.valueOf(args[0]);
        int time = Integer.valueOf(args[1]);
        boolean airport = Boolean.valueOf(args[2]);
        String res = getCheapestCarsharing(dist, time, airport);
        System.out.println(String.format("The minimal price is %s in car-sharing %s", res));

    }

    public static String getCheapestCarsharing(int dist, int time, boolean airport) {
        BigDecimal min = BigDecimal.valueOf(10000);
        String res = "unknown";
//        for (String cs : sharings) {
        BigDecimal pr = calculatePrise("DRIVENOW", dist, time, airport);
        if (pr.compareTo(min) < 0) {
            min = pr;
            res = "DN";
        }
        System.out.println("Prise: " + pr);
        return res;
    }

    private static BigDecimal calculatePrise(String cs, int dist, int time, boolean airport) {
        BigDecimal price = BigDecimal.ZERO;
        // airport
        if (airport)
            price = price.add(new BigDecimal(12, DECIMAL32));
        // time
        price = price.add(new BigDecimal(time, DECIMAL32).multiply(new BigDecimal(0.28, DECIMAL32)));
        //distance
        price = price.add(new BigDecimal(0.29, DECIMAL32).multiply(new BigDecimal(dist, DECIMAL32)));

        return price;
    }


//
//    Carsharing
//
//    Drive now:
//
//1. Assume that you are registered already
//2. BMW price: 34 ct/Min
//3. Mini price: 31 ct/Min
//4. insurance: 1 for each ride
//5. Keep: 15 ct/Min
//6. Reservation for 8 hours - 10 ct/Min
//7. AIRPORT fee: 12
//
//    Packages:
//1. 500 min (139.99) => 28 ct/min
//3 - 29, 80 km
//6 - 54, 120 km
//9 - 79, 200 km
//24 - 109, 200 km
//+ 29 ct/km
//
//
//    Input: distance (km), expected driving time (mins)
//
}
