package org.uml.eshopas.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.uml.eshopas.Models.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
