package icloudberry.carsharing.app;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class Result {

    private String type;
    private BigDecimal price;
}
