package com.ayurveda.config;

import com.ayurveda.model.*;
import com.ayurveda.repo.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;

@Configuration
public class DataSeeder {

    @Bean
    CommandLineRunner seed(UserRepository users, MedicineRepository medicines, PasswordEncoder encoder) {
        return args -> {
            // Seed Admin User
            if (!users.existsByEmail("admin@ayurveda.local")) {
                users.save(new AppUser("System Admin", "admin@ayurveda.local", encoder.encode("Admin@12345"), Role.ADMIN));
            }

            // Seed Customer Demo User
            if (!users.existsByEmail("customer@ayurveda.local")) {
                users.save(new AppUser("Priya Sharma", "customer@ayurveda.local", encoder.encode("Customer@12345"), Role.CUSTOMER));
            }

            // Seed Rich Ayurvedic Catalog if empty or low
            if (medicines.count() < 10) {
                add(medicines, "Ashwagandha Ghan Vati", "Herbal Supplements",
                    "Potent adaptogenic formulation to alleviate daily stress, support nervous system tranquility, and rejuvenate vital energy (Ojas).",
                    399.00, 65, "https://images.unsplash.com/photo-1603909223429-69bb7101f420?w=700",
                    "1-2 tablets twice daily with warm milk or water after meals.");

                add(medicines, "Triphala Churna Pure Blend", "Digestive Care",
                    "Traditional blend of Amalaki, Bibhitaki, and Haritaki. Gentle colon cleanser that optimizes digestive fire (Agni) and daily regularity.",
                    249.00, 50, "https://images.unsplash.com/photo-1615485500704-8e990f9900f5?w=700",
                    "1 teaspoon (3-5g) before bed with warm water.");

                add(medicines, "Brahmi Vati (Cognitive Support)", "Herbal Supplements",
                    "Classical Medhya Rasayana herb formulated to enhance mental clarity, memory retention, focus, and restful sleep patterns.",
                    349.00, 40, "https://images.unsplash.com/photo-1584308666744-24d5c474f2ae?w=700",
                    "1 tablet twice daily with honey or warm water.");

                add(medicines, "Mahanarayan Taila (Joint Oil)", "External Care",
                    "Classical medicated sesame oil prepared with over 30 botanical extracts. Relieves joint stiffness, muscle soreness, and Vata discomfort.",
                    380.00, 35, "https://images.unsplash.com/photo-1608571423902-eed4a5ad8108?w=700",
                    "Apply gently on affected joints and massage in circular motion.");

                add(medicines, "Kumkumadi Tailam Radiant Serum", "Skin & Hair",
                    "Precious saffron oil facial formulation that nourishes skin texture, fades blemishes, and imparts natural luminosity and hydration.",
                    599.00, 28, "https://images.unsplash.com/photo-1608248597359-322d7d5669b2?w=700",
                    "3-4 drops gently pressed into cleansed face before sleep.");

                add(medicines, "Chyawanprash Special Rasayana", "Immunity & Rasayana",
                    "Ancient amla-based restorative jam infused with 48 therapeutic herbs. Boosts cellular immunity, respiratory vitality, and winter stamina.",
                    450.00, 55, "https://images.unsplash.com/photo-1547887538-e3a2f32cb1cc?w=700",
                    "1 tablespoon (12g) morning and evening followed by warm milk.");

                add(medicines, "Sitopaladi Churna Cough Care", "Respiratory Care",
                    "Soothing formula containing Vanshalochan, Pippali, and Cardamom. Soothes dry throats, clears congestion, and pacifies Pitta-Kapha chest imbalance.",
                    199.00, 48, "https://images.unsplash.com/photo-1512069772995-ec65ed45afd6?w=700",
                    "Half teaspoon mixed with raw honey, 2-3 times a day.");

                add(medicines, "Giloy Ghan Vati (Guduchi)", "Immunity & Rasayana",
                    "Pure Guduchi extract known as Amrita (nectar of life). Supports balanced immune response, blood purification, and healthy temperature regulation.",
                    279.00, 60, "https://images.unsplash.com/photo-1577937927133-66ef06acdf18?w=700",
                    "1 tablet twice daily with lukewarm water.");

                add(medicines, "Shatavari Kalpa Granules", "Women's Wellness",
                    "Nutritive herbal tonic supporting female hormonal equilibrium, vitality, and digestive soothing.",
                    360.00, 32, "https://images.unsplash.com/photo-1527799820374-dcf8d9d4a388?w=700",
                    "1-2 teaspoons dissolved in warm milk twice a day.");

                add(medicines, "Bhringraj Kesh Taila", "Skin & Hair",
                    "Traditional scalp nourishment oil enriched with Bhringraj, Amla, and Brahmi to strengthen hair roots and reduce hair thinning.",
                    299.00, 45, "https://images.unsplash.com/photo-1522337360788-8b13dee7a37e?w=700",
                    "Gently massage into scalp and roots twice weekly before wash.");

                add(medicines, "Draksharishta Digestive Elixir", "Classical Asava-Arishta",
                    "Naturally fermented classical preparation with raisins, spices, and dhataki flowers for strengthening sluggish digestion and fatigue.",
                    220.00, 25, "https://images.unsplash.com/photo-1514432324607-a09d9b4aefdd?w=700",
                    "15-20 ml with equal quantity of water after lunch and dinner.");

                add(medicines, "Neem Purifying Tablets", "Skin & Hair",
                    "Azadirachta indica whole herb extract. Detoxifies systemic impurities, supports clear skin clarity, and promotes metabolic health.",
                    250.00, 50, "https://images.unsplash.com/photo-1584308666744-24d5c474f2ae?w=700",
                    "1 tablet in the morning on an empty stomach with warm water.");
            }
        };
    }

    private void add(MedicineRepository repo, String n, String c, String d, double p, int s, String img, String dose) {
        Medicine m = new Medicine();
        m.setName(n);
        m.setCategory(c);
        m.setDescription(d);
        m.setPrice(BigDecimal.valueOf(p));
        m.setStock(s);
        m.setImageUrl(img);
        m.setDosage(dose);
        m.setActive(true);
        repo.save(m);
    }
}
