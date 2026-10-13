package com.ayurveda.web;
import com.ayurveda.model.Medicine; import com.ayurveda.repo.MedicineRepository;
import org.springframework.web.bind.annotation.*; import java.util.List;
@RestController @RequestMapping("/api/medicines")
public class MedicineController {
 private final MedicineRepository repo; public MedicineController(MedicineRepository r){repo=r;}
 @GetMapping public List<Medicine> all(@RequestParam(required=false) String q){
  List<Medicine> all=repo.findByActiveTrueOrderByNameAsc();
  if(q==null||q.isBlank())return all;
  String term=q.toLowerCase();return all.stream().filter(m->m.getName().toLowerCase().contains(term)||m.getCategory().toLowerCase().contains(term)).toList();
 }
 @GetMapping("/{id}") public Medicine one(@PathVariable Long id){return repo.findById(id).orElseThrow(()->new IllegalArgumentException("Medicine not found"));}
}
