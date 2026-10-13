package com.ayurveda.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.math.BigDecimal;

@Entity
@Table(name="order_items")
public class OrderItem {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @ManyToOne(optional=false) private CustomerOrder order;
 @ManyToOne(optional=false) private Medicine medicine;
 @Column(nullable=false) private Integer quantity;
 @Column(nullable=false,precision=10,scale=2) private BigDecimal unitPrice;
 public OrderItem(){}
 public OrderItem(Medicine medicine,Integer quantity,BigDecimal unitPrice){this.medicine=medicine;this.quantity=quantity;this.unitPrice=unitPrice;}
 public Long getId(){return id;} @JsonIgnore public CustomerOrder getOrder(){return order;} public void setOrder(CustomerOrder v){order=v;}
 public Medicine getMedicine(){return medicine;} public void setMedicine(Medicine v){medicine=v;}
 public Integer getQuantity(){return quantity;} public void setQuantity(Integer v){quantity=v;}
 public BigDecimal getUnitPrice(){return unitPrice;} public void setUnitPrice(BigDecimal v){unitPrice=v;}
}
