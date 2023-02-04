package fr.dovi.billingservice.openfeign;

import fr.dovi.billingservice.entities.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "inventory-service")
public interface InventoryServiceClient {

  @GetMapping("/products/{id}")
  Product findProductById(@PathVariable long id);

  @GetMapping("/products")
  PagedModel<Product> findAll();
}
