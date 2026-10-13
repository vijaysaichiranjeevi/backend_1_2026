package com.ayurveda.web;

import com.ayurveda.model.Medicine;
import com.ayurveda.repo.MedicineRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/medicines")
public class MedicineController {

    private final MedicineRepository repo;

    public MedicineController(MedicineRepository r) {
        this.repo = r;
    }

    @GetMapping
    public List<Medicine> all(@RequestParam(required = false) String q, @RequestParam(required = false) String category) {
        List<Medicine> all = repo.findByActiveTrueOrderByNameAsc();
        if (category != null && !category.isBlank() && !category.equalsIgnoreCase("all")) {
            all = all.stream().filter(m -> m.getCategory().equalsIgnoreCase(category.trim())).toList();
        }
        if (q == null || q.isBlank()) {
            return all;
        }
        String term = q.toLowerCase().trim();
        return all.stream().filter(m ->
            m.getName().toLowerCase().contains(term) ||
            m.getCategory().toLowerCase().contains(term) ||
            (m.getDescription() != null && m.getDescription().toLowerCase().contains(term))
        ).toList();
    }

    @GetMapping("/categories")
    public List<String> categories() {
        return repo.findByActiveTrueOrderByNameAsc().stream()
            .map(Medicine::getCategory)
            .distinct()
            .sorted()
            .toList();
    }

    @GetMapping("/{id}")
    public Medicine one(@PathVariable Long id) {
        return repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Medicine not found with id: " + id));
    }
}
