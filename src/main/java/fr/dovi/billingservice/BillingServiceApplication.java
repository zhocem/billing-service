package fr.dovi.billingservice;

import fr.dovi.billingservice.entities.Bill;
import fr.dovi.billingservice.entities.Customer;
import fr.dovi.billingservice.entities.Product;
import fr.dovi.billingservice.entities.ProductItem;
import fr.dovi.billingservice.openfeign.CustomerServiceClient;
import fr.dovi.billingservice.openfeign.InventoryServiceClient;
import fr.dovi.billingservice.repository.BillRepository;
import fr.dovi.billingservice.repository.ProductItemRepository;
import java.util.Collection;
import java.util.Date;
import java.util.Random;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.hateoas.PagedModel;

@SpringBootApplication
@EnableFeignClients
public class BillingServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BillingServiceApplication.class, args);
	}

	@Bean
	CommandLineRunner start(
			BillRepository billRepository,
			ProductItemRepository productItemRepository,
			CustomerServiceClient customerServiceClient,
			InventoryServiceClient inventoryServiceClient
	) {
		return args -> {
			Customer customer = customerServiceClient.findCustomerById(1L);
			Bill bill = billRepository.save(new Bill(null, new Date(), null, null, customer.getId()));
			PagedModel<Product> productPagedModel = inventoryServiceClient.findAll();
			productPagedModel.forEach(product -> {
				ProductItem productItem = new ProductItem();
				productItem.setPrice(product.getPrice());
				productItem.setProductID(product.getId());
				productItem.setQuantity(1 + new Random().nextInt(100));
				productItem.setBill(bill);
				productItemRepository.save(productItem);
			});
		};
	}

}
