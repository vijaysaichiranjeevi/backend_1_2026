package com.ayurveda.web;
import com.ayurveda.model.*; import com.ayurveda.repo.*;
import jakarta.validation.Valid; import org.springframework.http.HttpStatus; import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*; import org.springframework.web.server.ResponseStatusException;
import java.math.BigDecimal; import java.util.*;

@RestController @RequestMapping("/api/admin")
public class AdminController {
 private final MedicineRepository medicines; private final OrderRepository orders; private final UserRepository users;
 public AdminController(MedicineRepository m,OrderRepository o,UserRepository u){medicines=m;orders=o;users=u;}
 @GetMapping("/dashboard") public Map<String,Object> dashboard(){
  return Map.of("medicineCount",medicines.count(),"orderCount",orders.count(),"customerCount",users.findAll().stream().filter(u->u.getRole()==Role.CUSTOMER).count(),
   "lowStock",medicines.findAll().stream().filter(m->m.getStock()<10).count(),"recentOrders",orders.findAllByOrderByCreatedAtDesc().stream().limit(8).toList());
 }
 @GetMapping("/medicines") public List<Medicine> allMedicines(){return medicines.findAll();}
 @PostMapping("/medicines") public Medicine create(@Valid @RequestBody ApiDtos.MedicineRequest r){return medicines.save(map(new Medicine(),r));}
 @PutMapping("/medicines/{id}") public Medicine update(@PathVariable Long id,@Valid @RequestBody ApiDtos.MedicineRequest r){Medicine m=medicines.findById(id).orElseThrow();return medicines.save(map(m,r));}
 @DeleteMapping("/medicines/{id}") public void delete(@PathVariable Long id){Medicine m=medicines.findById(id).orElseThrow();m.setActive(false);medicines.save(m);}
 private Medicine map(Medicine m,ApiDtos.MedicineRequest r){m.setName(r.name());m.setCategory(r.category());m.setPrice(r.price());m.setStock(r.stock());m.setDescription(r.description());m.setImageUrl(r.imageUrl());m.setDosage(r.dosage());m.setActive(r.active()==null||r.active());return m;}
 @GetMapping("/orders") public List<CustomerOrder> allOrders(){return orders.findAllByOrderByCreatedAtDesc();}
 @PatchMapping("/orders/{id}/status") @Transactional public CustomerOrder status(@PathVariable Long id,@Valid @RequestBody ApiDtos.StatusRequest r){
  CustomerOrder o=orders.findById(id).orElseThrow();OrderStatus next;
  try{next=OrderStatus.valueOf(r.status().toUpperCase());}catch(Exception e){throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Invalid status");}
  if(o.getStatus()==OrderStatus.CANCELLED&&next!=OrderStatus.CANCELLED)throw new ResponseStatusException(HttpStatus.CONFLICT,"Cancelled orders cannot be reopened");
  if(next==OrderStatus.CANCELLED&&o.getStatus()!=OrderStatus.CANCELLED){for(OrderItem i:o.getItems()){Medicine m=i.getMedicine();m.setStock(m.getStock()+i.getQuantity());medicines.save(m);}}
  o.setStatus(next);return orders.save(o);
 }
}
