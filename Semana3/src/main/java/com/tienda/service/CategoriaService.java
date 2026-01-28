
package com.tienda.service;

import com.tienda.domain.Categoria;
import com.tienda.repository.CategoriaRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
/**
 *
 * @author Cesar Porras
 */
@Service
public class CategoriaService {
    //permite crear una unica instancia de CategoriaRepository y la crea automaticamente
    @Autowired
    private CategoriaRepository categoriaRepository;
    
    @Transactional(readOnly = true)
    public List<Categoria> getCategorias(boolean activo){
        if (activo) { //solo activos
            return categoriaRepository.findByActivoTrue();
        }
        return categoriaRepository.findAll();
    }
}
