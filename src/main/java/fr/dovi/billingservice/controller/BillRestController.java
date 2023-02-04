package fr.dovi.billingservice.controller;

import fr.dovi.billingservice.entities.Bill;
import fr.dovi.billingservice.openfeign.CustomerServiceClient;
import fr.dovi.billingservice.openfeign.InventoryServiceClient;
import fr.dovi.billingservice.repository.BillRepository;
import fr.dovi.billingservice.repository.ProductItemRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class BillRestController {
  private BillRepository billRepository;
  private ProductItemRepository productItemRepository;
  private CustomerServiceClient customerServiceClient;
  private InventoryServiceClient inventoryServiceClient;

  @GetMapping("/bills/full/{id}")
  Bill getBill(@PathVariable long id) {
    Bill bill = billRepository.findById(id).get();
    bill.setCustomer(customerServiceClient.findCustomerById(bill.getCustomerID()));
    bill.setProductItems(productItemRepository.findByBillId(id));
    bill.getProductItems().forEach(pi -> {
      pi.setProduct(inventoryServiceClient.findProductById(pi.getProductID()));
    });
    return bill;
  }
}
