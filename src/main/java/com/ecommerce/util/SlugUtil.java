package com.ecommerce.util;

import org.springframework.stereotype.Component;
import java.text.Normalizer;
import java.util.Locale;

@Component
public class SlugUtil {

    public String generateSlug(String input) {
        if (input == null || input.isEmpty()) {
            throw new RuntimeException(
                "Input cannot be empty!");
        }

        String slug = Normalizer
            .normalize(input, Normalizer.Form.NFD)
            .replaceAll("[^\\p{ASCII}]", "")
            .toLowerCase(Locale.ENGLISH)
            .replaceAll("[^a-z0-9\\s-]", "")
            .replaceAll("[\\s-]+", "-")
            .replaceAll("^-|-$", "");

        return slug;
    }

    // Unique slug generate karo timestamp se
    public String generateUniqueSlug(String input) {
        String slug = generateSlug(input);
        return slug + "-" + System.currentTimeMillis();
    }
}