package ru.gb.web_market.products.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.gb.web_market.products.entities.Manufacturer;

import java.util.List;

@Repository
public interface ManufacturerRepository extends JpaRepository<Manufacturer, Long>, JpaSpecificationExecutor<Manufacturer> {
    @Modifying
//    @Query("select p.manufacturer from Product p where p.subCategory1 like ?1 and p.subCategory2 like ?2 group by p.manufacturer")
//    @Query(nativeQuery = true, value = "select m.title from manufacturer m inner join products p on p.manufacturer_id = m.id inner join categories c on c.id = p.category_id where c.id = ?1 group by m.id")
    @Query(nativeQuery = true, value =
            "select m.id, m.title from manufacturer m " +
            "inner join products p " +
            "on p.manufacturer_id = m.id " +
            "inner join categories c " +
            "on c.id = p.category_id " +
            "where c.id = ?1 " +
            "group by m.id")
    List<String> findManufacturer(long cat);

    @Modifying
    @Query(nativeQuery = true, value =
            "select m.id, m.title from manufacturer m")
    List<String> findAllManufacturer();

}
