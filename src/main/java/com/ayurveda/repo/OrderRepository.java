package com.ayurveda.repo;
import com.ayurveda.model.CustomerOrder; import org.springframework.data.jpa.repository.JpaRepository; import java.util.List;
public interface OrderRepository extends JpaRepository<CustomerOrder,Long>{List<CustomerOrder> findByCustomerIdOrderByCreatedAtDesc(Long customerId); List<CustomerOrder> findAllByOrderByCreatedAtDesc(); long countBy();}