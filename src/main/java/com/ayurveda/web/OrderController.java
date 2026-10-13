package com.ayurveda.web;
import com.ayurveda.model.*; import com.ayurveda.repo.*;
import jakarta.validation.Valid; import org.springframework.http.HttpStatus; import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional; import org.springframework.web.bind.annotation.*; import org.springframework.web.server.ResponseStatusException;
import java.math.BigDecimal; import java.util.List;

@RestController @RequestMapping("/api/orders")
public class OrderController {
 private final OrderRepository orders;private final MedicineRepository medicines;private final UserRepository users;
 public OrderController(OrderRepository o,MedicineRepository m,UserRepository u){orders=o;medicines=m;users=u;}
 @PostMapping @Transactional public CustomerOrder checkout(@Valid @RequestBody ApiDtos.CheckoutRequest r,Authentication auth){
  AppUser customer=users.findByEmail(auth.getName()).orElseThrow();CustomerOrder o=new CustomerOrder();o.setCustomer(customer);o.setShippingAddress(r.shippingAddress().trim());
  BigDecimal total=BigDecimal.ZERO;
  for(ApiDtos.CartLine line:r.items()){
   Medicine m=medicines.findById(line.medicineId()).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Medicine not found"));
   if(!m.isActive())throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Medicine unavailable");
   if(line.quantity()==null||line.quantity()<1)throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Quantity must be positive");
   if(m.getStock()<line.quantity())throw new ResponseStatusException(HttpStatus.CONFLICT,"Insufficient stock for "+m.getName());
   m.setStock(m.getStock()-line.quantity());medicines.save(m);
   o.addItem(new OrderItem(m,line.quantity(),m.getPrice()));total=total.add(m.getPrice().multiply(BigDecimal.valueOf(line.quantity())));
  }
  o.setTotal(total);return orders.save(o);
 }
 @GetMapping public List<CustomerOrder> mine(Authentication auth){AppUser u=users.findByEmail(auth.getName()).orElseThrow();return orders.findByCustomerIdOrderByCreatedAtDesc(u.getId());}
 @GetMapping("/{id}") public CustomerOrder one(@PathVariable Long id,Authentication auth){
  AppUser u=users.findByEmail(auth.getName()).orElseThrow();CustomerOrder o=orders.findById(id).orElseThrow();
  if(!u.getRole().equals(Role.ADMIN)&&!o.getCustomer().getId().equals(u.getId()))throw new ResponseStatusException(HttpStatus.FORBIDDEN);
  return o;
 }
}
