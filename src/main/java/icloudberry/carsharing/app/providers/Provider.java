package icloudberry.carsharing.app.providers;

import icloudberry.carsharing.app.Result;

public interface Provider {

    Result calculatePrice(int dist, int time, boolean airport);
}
