package icloudberry.carsharing.app;

import icloudberry.carsharing.app.providers.CarToGo;
import icloudberry.carsharing.app.providers.DriveNow;
import icloudberry.carsharing.app.providers.Provider;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class App {

    private static List<Provider> providers = new ArrayList<>();

    public String getGreeting() {
        return "Welcome to the CarSharing Calculator!";
    }

    public static void main(String[] args) {
        System.out.println(new App().getGreeting());
        int dist = Integer.valueOf(args[0]);
        int time = Integer.valueOf(args[1]);
        boolean airport = Boolean.valueOf(args[2]);
        ResultWrapper res = getCheapestCarsharing(dist, time, airport);
        System.out.println(String.format("The trip is %s km long, around %s minutes, to the airport: %s",
                dist, time, airport));
        System.out.println(String.format("The minimal price is %s in package %s, car-sharing %s",
                res.getPrice(), res.getType(), res.getCarSharing()));

    }

    public static ResultWrapper getCheapestCarsharing(int dist, int time, boolean airport) {
        providers.add(new CarToGo());
        providers.add(new DriveNow());

        Result min = new Result("unknown", BigDecimal.valueOf(1000));
        Result tmp = min;
        Provider pr = providers.get(0);
        for (Provider p : providers) {
            tmp = p.calculatePrice(dist, time, airport);
            if (tmp.getPrice().compareTo(min.getPrice()) < 0) {
                min = tmp;
                pr = p;
            }
        }
        return new ResultWrapper(tmp, pr);
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
