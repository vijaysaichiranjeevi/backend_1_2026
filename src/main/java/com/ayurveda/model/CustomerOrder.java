package com.ayurveda.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="customer_orders")
public class CustomerOrder {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @ManyToOne(optional=false,fetch=FetchType.EAGER) private AppUser customer;
 @Enumerated(EnumType.STRING) @Column(nullable=false) private OrderStatus status=OrderStatus.PLACED;
 @Column(nullable=false,precision=10,scale=2) private BigDecimal total;
 @Column(nullable=false,length=1000) private String shippingAddress;
 @Column(nullable=false) private LocalDateTime createdAt=LocalDateTime.now();
 @OneToMany(mappedBy="order",cascade=CascadeType.ALL,orphanRemoval=true,fetch=FetchType.EAGER)
 private List<OrderItem> items=new ArrayList<>();
 public CustomerOrder(){}
 public Long getId(){return id;} public AppUser getCustomer(){return customer;} public void setCustomer(AppUser v){customer=v;}
 public OrderStatus getStatus(){return status;} public void setStatus(OrderStatus v){status=v;}
 public BigDecimal getTotal(){return total;} public void setTotal(BigDecimal v){total=v;}
 public String getShippingAddress(){return shippingAddress;} public void setShippingAddress(String v){shippingAddress=v;}
 public LocalDateTime getCreatedAt(){return createdAt;} public List<OrderItem> getItems(){return items;}
 public void addItem(OrderItem item){items.add(item);item.setOrder(this);}
}
