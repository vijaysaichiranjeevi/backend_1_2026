package com.ayurveda.web;
import jakarta.validation.constraints.*; import java.math.BigDecimal; import java.util.List;
public class ApiDtos {
 public record RegisterRequest(@NotBlank String name,@Email @NotBlank String email,@Size(min=8) String password){}
 public record LoginRequest(@Email @NotBlank String email,@NotBlank String password){}
 public record AuthResponse(String token,String name,String email,String role){}
 public record MedicineRequest(@NotBlank String name,@NotBlank String category,@NotNull @DecimalMin("0.01") BigDecimal price,@NotNull @Min(0) Integer stock,String description,String imageUrl,String dosage,Boolean active){}
 public record CartLine(Long medicineId,@Min(1) Integer quantity){}
 public record CheckoutRequest(@NotEmpty List<CartLine> items,@NotBlank String shippingAddress){}
 public record StatusRequest(@NotBlank String status){}
}
