package ru.gb.web_market.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.gb.web_market.entities.Category;
import ru.gb.web_market.entities.Product;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoriesRepository extends JpaRepository<Category, Long>, JpaSpecificationExecutor<Category> {

    @Modifying
//    @Query("select p.subCategory1 from Product p group by p.subCategory1")
    @Query(nativeQuery = true, value =
            "select c2.id, c2.title from categories c2 " +
                    "inner join categories c1 " +
                    "on c1.id = c2.parent_id " +
                    "where c1.id = 1")
    List<String> findAllCategories();

    @Modifying
//    @Query("select p.subCategory2 from Product p where p.subCategory1 = ?1 group by p.subCategory2")
    @Query(nativeQuery = true, value =
            "select c2.id, c2.title from categories c2 " +
                    "inner join categories c1 " +
                    "on c1.id = c2.parent_id " +
                    "where c1.id = ?1")
    List<String> findAllSubCategories(long cat);

    Optional<Category> findFirstByTitle(String title);

    List<Category> findAllByParentCategory(Category parentCategory);

}
