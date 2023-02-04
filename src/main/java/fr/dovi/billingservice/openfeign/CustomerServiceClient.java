package fr.dovi.billingservice.openfeign;

import fr.dovi.billingservice.entities.Customer;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "customer-service")
public interface CustomerServiceClient {

  @GetMapping("/customers/{id}")
  Customer findCustomerById(@PathVariable Long id);

}
