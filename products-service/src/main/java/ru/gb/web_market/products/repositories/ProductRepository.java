package ru.gb.web_market.products.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.gb.web_market.products.entities.Product;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {



    @Modifying
//    @Query("select p.manufacturer from Product p where p.subCategory1 like ?1 and p.subCategory2 like ?2 group by p.manufacturer")
//    @Query(nativeQuery = true, value = "select m.title from manufacturer m inner join products p on p.manufacturer_id = m.id inner join categories c on c.id = p.category_id where c.id = ?1 group by m.id")
    @Query(nativeQuery = true, value =
            "select m.title from manufacturer m " +
            "inner join products p " +
            "on p.manufacturer_id = m.id " +
            "inner join categories c " +
            "on c.id = p.category_id " +
            "where c.title like ?1 " +
            "group by m.id")
    List<String> findManufacturer(String cat);

    Optional<Product> findById (Long id);

}
