package icloudberry.carsharing.app.providers;

import icloudberry.carsharing.app.Result;

public class BeeZero implements Provider{

    @Override
    public Result calculatePrice(int dist, int time, boolean airport) {
        return null;
    }

    @Override
    public String getName() {
        return "BeeZero";
    }

}
