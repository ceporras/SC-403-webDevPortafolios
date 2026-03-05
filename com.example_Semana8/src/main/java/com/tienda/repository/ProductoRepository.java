
package com.tienda.repository;

import com.tienda.domain.Producto;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;



public interface ProductoRepository extends JpaRepository<Producto, Integer>{
    public List<Producto> findByActivoTrue();
    
   
    
    //ejemplo utilizando consultas derivadas
    public List<Producto> findByPrecioBetweenOrderByPrecioAsc(double precioInf, double precioSup);
    
    //ejemplo utilizando JPQL
    @Query(value = "SELECT p FROM Producto p WHERE p.precio BETWEEN :precioInf AND :precioSup ORDER BY p.precio ASC")
    public List<Producto> consultaJPQL(@Param("precioInf") double precioInf,@Param("precioSup") double precioSup);
    
    //ejemplo utilizando SQL nativo
    @Query(nativeQuery = true, value = "SELECT * FROM producto p WHERE p.precio BETWEEN :precioInf AND :precioSup ORDER BY p.precio ASC")
    public List<Producto> consultaSQL(@Param("precioInf") double precioInf,@Param("precioSup") double precioSup);
    
    //consultas practica programada
    //
    //consultas derivadas
    public List<Producto> findByActivoTrueAndPrecioBetweenAndExistenciasGreaterThanAndCategoriaActivoTrueAndCategoriaDescripcionContainingOrderByPrecioAsc(double precioInf, double precioSup, int minExistencias, String descFiltro);
    
    //JPQL
    @Query(value = "SELECT p FROM Producto p WHERE p.activo=true AND p.precio BETWEEN :precioInf AND :precioSup AND p.existencias > :minExistencias AND p.categoria.activo=true AND p.categoria.descripcion LIKE %:descFiltro% ORDER BY p.precio ASC")
    public List<Producto> consultaAvanzadaJPQL(@Param("precioInf") double precioInf,@Param("precioSup") double precioSup,@Param("minExistencias") int minExistencias,@Param("descFiltro") String descFiltro);
    
    //SQL
    @Query(nativeQuery = true, value = "SELECT p.* FROM producto p INNER JOIN categoria c ON p.id_categoria = c.id_categoria "
            + "WHERE p.activo=true AND p.precio BETWEEN :precioInf AND :precioSup AND p.existencias > :minExistencias "
            + "AND c.activo=true AND c.descripcion LIKE %:descFiltro% ORDER BY p.precio ASC")
    public List<Producto> consultaAvanzadaSQL(@Param("precioInf") double precioInf,@Param("precioSup") double precioSup,@Param("minExistencias") int minExistencias,@Param("descFiltro") String descFiltro);
    
}
