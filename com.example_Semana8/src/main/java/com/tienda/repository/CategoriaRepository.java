
package com.tienda.repository;

import com.tienda.domain.Categoria;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author Cesar Porras
 */
public interface CategoriaRepository extends JpaRepository<Categoria, Integer>{
    public List<Categoria> findByActivoTrue();
    
    //consulta derivada
    //NOTA: consulta derivada no soporta GROUP BY ni HAVING. 
    //Alternativamente intenté usar SELECT DISTINCT pero esto da problemas a la hora de 
    //hacer ORDER BY que no permite tener un COUNT en el ORDER, ni hacer el order por un valor(p.existencias) que no está en el select.
    public List<Categoria> findDistinctByActivoIsTrueAndProductosActivoIsTrueAndDescripcionContaining(String textoDescripcion/*,int cantidadMinProductos*/);
    
    //JPQL
    @Query(value = "SELECT c FROM Categoria c JOIN c.productos p WHERE c.activo=true AND p.activo=true AND c.descripcion LIKE %:textoDescripcion% GROUP BY c HAVING COUNT(p.activo) > :cantidadMinProductos ORDER BY count(p) DESC")
    //@Query(value = "SELECT DISTINCT c FROM Categoria c JOIN c.productos p WHERE c.activo=true AND p.activo=true AND c.descripcion LIKE %:textoDescripcion% AND COUNT(p.activo) > :cantidadMinProductos")
    public List<Categoria> consultaAdvCategoriaJPQL(@Param("textoDescripcion") String textoDescripcion,@Param("cantidadMinProductos") int cantidadMinProductos);
    
    //SQL
    @Query(nativeQuery = true, value = "SELECT c.* FROM categoria c INNER JOIN producto p ON c.id_categoria = p.id_categoria "
            + "WHERE c.activo=true AND p.activo=true AND c.descripcion LIKE %:textoDescripcion% "
            + "GROUP BY c.descripcion HAVING COUNT(p.activo) > :cantidadMinProductos ORDER BY count(p.id_producto) DESC")
    public List<Categoria> consultaAdvCategoriaSQL(@Param("textoDescripcion") String textoDescripcion,@Param("cantidadMinProductos") int cantidadMinProductos);
    
}
