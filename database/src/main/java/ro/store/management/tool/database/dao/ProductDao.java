package ro.store.management.tool.database.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import ro.store.management.tool.database.model.Product;

import java.sql.Types;
import java.util.List;

@RequiredArgsConstructor
public class ProductDao {

    private final JdbcTemplate jdbcTemplate;

    public Product findProductByName(String name) {
        String sql = "SELECT id, name, measurement_unit_name, quantity, price FROM product WHERE UPPER(name) = UPPER(?)";

        int[] argTypes = new int[] {Types.VARCHAR};
        Object[] args = new Object[] {name};
        ResultSetExtractor<Product> rse = rs -> {
            Product p = null;
            if (rs.next()) {
                p = new Product();
                p.setId(rs.getInt("id"));
                p.setQuantity(rs.getInt("quantity"));
                p.setName(rs.getString("name"));
                p.setMeasurementUnitName(rs.getString("measurement_unit_name"));
                p.setPrice(rs.getDouble("price"));
            }
            return p;
        };
        return this.jdbcTemplate.query(sql, args, argTypes, rse);
    }

    public void saveProduct(Product productToSave) {
        String sql = "SELECT id FROM product WHERE UPPER(name) = UPPER(?)";

        int[] argTypes = new int[] {Types.VARCHAR};
        Object[] args = new Object[] {productToSave.getName()};
        ResultSetExtractor<Integer> rse = rs -> {
            if (rs.next()) {
                return rs.getInt("id");
            }
            return null;
        };
        Integer existingProductId = this.jdbcTemplate.query(sql, args, argTypes, rse);
        if (productToSave.getId() == null) {
            // insert
            if (existingProductId == null) {
                insertProduct(productToSave);
            } else {
                throw new RuntimeException("Can not insert the product.");
            }
        } else {
            // update
            if (existingProductId != null && existingProductId.intValue() == productToSave.getId().intValue()) {
                // the same id
                updateProduct(productToSave);
            } else {
                throw new RuntimeException("Can not update the product.");
            }
        }
    }

    private void insertProduct(Product productToSave) {
        String sql = "INSERT INTO product(name, measurement_unit_name, quantity, price) VALUES (?, ?, ?, ?)";
        int[] argTypes = new int[] {Types.VARCHAR, Types.VARCHAR, Types.INTEGER, Types.DOUBLE};
        Object[] args = new Object[] {productToSave.getName(), productToSave.getMeasurementUnitName(), productToSave.getQuantity(), productToSave.getPrice()};
        this.jdbcTemplate.update(sql, args, argTypes);
    }

    private void updateProduct(Product productToSave) {
        String sql = "UPDATE product SET name = ?, measurement_unit_name = ?, quantity = ?, price = ? WHERE id = ?";
        int[] argTypes = new int[] {Types.VARCHAR, Types.VARCHAR, Types.INTEGER, Types.DOUBLE, Types.INTEGER};
        Object[] args = new Object[] {productToSave.getName(), productToSave.getMeasurementUnitName(), productToSave.getQuantity(), productToSave.getPrice(), productToSave.getId()};
        this.jdbcTemplate.update(sql, args, argTypes);
    }

    public List<Product> getAllProducts() {
        String sql = "SELECT id, name, measurement_unit_name, quantity, price FROM product";

        int[] argTypes = new int[] {};
        Object[] args = new Object[] {};
        RowMapper<Product> rowMapper = (rs, rowNum) -> {
            Product p = new Product();
            p.setId(rs.getInt("id"));
            p.setQuantity(rs.getInt("quantity"));
            p.setName(rs.getString("name"));
            p.setMeasurementUnitName(rs.getString("measurement_unit_name"));
            p.setPrice(rs.getDouble("price"));
            return p;
        };
        return this.jdbcTemplate.query(sql, args, argTypes, rowMapper);
    }
}
