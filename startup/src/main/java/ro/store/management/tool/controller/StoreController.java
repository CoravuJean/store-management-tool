package ro.store.management.tool.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.store.management.tool.database.dao.ProductDao;
import ro.store.management.tool.database.model.Product;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("store/v1/")
@RequiredArgsConstructor
public class StoreController {

    private final ProductDao productDao;

    @GetMapping(value="/find-product", produces = "application/json")
    public ResponseEntity<Product> findProduct(@RequestParam(value="name") String productName) {
        if (log.isInfoEnabled()) {
            log.info("Find product buy name: {}", productName);
        }

        Product product = this.productDao.findProductByName(productName);
        if (product == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(product);
    }

    @GetMapping(value="/all-products", produces = "application/json")
    public ResponseEntity<List<Product>> getAllProducts() {
        if (log.isInfoEnabled()) {
            log.info("Get all products stored into the database.");
        }

        List<Product> allProducts = this.productDao.getAllProducts();
        return ResponseEntity.ok(allProducts);
    }

    @PostMapping(value="/save-product", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Boolean> saveProduct(@RequestBody Product productToSave) {
        if (log.isInfoEnabled()) {
            log.info("Save product into the database.");
        }

        this.productDao.saveProduct(productToSave);
        return ResponseEntity.ok(true);
    }
}
