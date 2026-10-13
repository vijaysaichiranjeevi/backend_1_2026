package com.ayurveda.config;
import com.ayurveda.model.*;import com.ayurveda.repo.*;
import org.springframework.boot.CommandLineRunner;import org.springframework.context.annotation.Bean;import org.springframework.context.annotation.Configuration;import org.springframework.security.crypto.password.PasswordEncoder;
import java.math.BigDecimal;
@Configuration
public class DataSeeder {
 @Bean CommandLineRunner seed(UserRepository users,MedicineRepository medicines,PasswordEncoder encoder){return args->{
  if(!users.existsByEmail("admin@ayurveda.local"))users.save(new AppUser("System Admin","admin@ayurveda.local",encoder.encode("Admin@12345"),Role.ADMIN));
  if(medicines.count()==0){
   add(medicines,"Ashwagandha Capsules","Herbal Supplements","Traditional ashwagandha supplement. Use according to product label.",399,60,"https://images.unsplash.com/photo-1603909223429-69bb7101f420?w=700","Follow label directions");
   add(medicines,"Triphala Churna","Digestive Care","Traditional herbal powder blend.",249,45,"https://images.unsplash.com/photo-1615485500704-8e990f9900f5?w=700","Follow label directions");
   add(medicines,"Neem Tablets","Herbal Supplements","Neem-based herbal product.",299,30,"https://images.unsplash.com/photo-1547887538-e3a2f32cb1cc?w=700","Follow label directions");
   add(medicines,"Turmeric Herbal Balm","External Care","Herbal balm for external use.",179,24,"https://images.unsplash.com/photo-1608571423902-eed4a5ad8108?w=700","For external use only");
  }
 };}
 private void add(MedicineRepository repo,String n,String c,String d,double p,int s,String img,String dose){Medicine m=new Medicine();m.setName(n);m.setCategory(c);m.setDescription(d);m.setPrice(BigDecimal.valueOf(p));m.setStock(s);m.setImageUrl(img);m.setDosage(dose);m.setActive(true);repo.save(m);}
}
