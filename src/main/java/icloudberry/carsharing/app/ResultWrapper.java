package icloudberry.carsharing.app;

import icloudberry.carsharing.app.providers.Provider;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Optional;

@Data
@AllArgsConstructor
class ResultWrapper {

    private String type;
    private BigDecimal price;
    private String carSharing;

    ResultWrapper(Result tmp, Provider pr) {
        this.type = tmp.getType();
        this.price = tmp.getPrice();
        this.carSharing = pr.getName();
    }
}
