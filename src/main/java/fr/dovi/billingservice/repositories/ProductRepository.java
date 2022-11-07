package fr.dovi.billingservice.repositories;

import fr.dovi.billingservice.entities.ProductItem;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ProductRepository extends JpaRepository<ProductItem, Long> {

  List<ProductItem> findByBillId(Long billId);
}
