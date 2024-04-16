package ro.store.management.tool.database.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Product {

    private Integer id;
    private String name;
    private String measurementUnitName;
    private Integer quantity;
    private Double price;
}
