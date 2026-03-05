
package com.tienda.service;

import com.tienda.domain.Producto;
import com.tienda.repository.ProductoRepository;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
/**
 *
 * @author Cesar Porras
 */
@Service
public class ProductoService {
    
    private final ProductoRepository productoRepository;
    private final FirebaseStorageService firebaseStorageService;
    
    public ProductoService(ProductoRepository productoRepository, FirebaseStorageService firebaseStorageService){
        this.productoRepository = productoRepository;
        this.firebaseStorageService = firebaseStorageService;
    }
    
    @Transactional(readOnly = true)
    public List<Producto> getProductos(boolean activo){
        if (activo) { //solo activos
            return productoRepository.findByActivoTrue();
        }
        return productoRepository.findAll();
    }
    
    @Transactional(readOnly = true)
    public Optional<Producto> getProducto(Integer idProducto){
        return productoRepository.findById(idProducto);
    }
      
    
    @Transactional
    public void save(Producto producto, MultipartFile imagenFile){
        producto = productoRepository.save(producto);
        if (!imagenFile.isEmpty()) { //si no esta vacio, pasaron una imagen
            try{
                String rutaImagen = firebaseStorageService.uploadImage(imagenFile, "producto", producto.getIdProducto());
                producto.setRutaImagen(rutaImagen);
                productoRepository.save(producto);
            }catch(IOException e){
                
            }
        }
    }
    
    @Transactional
    public void delete(Integer idProducto){
        //verifica si la producto existe antes de intentar eliminarlo
        if (!productoRepository.existsById(idProducto)){
            //lanza exception para indicar que el usuario no fue encontrado
            throw new IllegalArgumentException("La producto con ID " + idProducto + " no existe.");
        }
        try {
            productoRepository.deleteById(idProducto);
        }catch(DataIntegrityViolationException e){
            throw new IllegalStateException("No se puede eliminar la producto. Tiene datos asociados.", e);
        }
    }
    //consulta derivada
    @Transactional(readOnly = true)
    public List<Producto> consultaDerivada(double precioInf, double precioSup){
        return productoRepository.findByPrecioBetweenOrderByPrecioAsc(precioInf, precioSup);
    }
    //consulta JPQL
    @Transactional(readOnly = true)
    public List<Producto> consultaJPQL(double precioInf, double precioSup){
        return productoRepository.consultaJPQL(precioInf, precioSup);
    }
    //consulta SQL nativo
    @Transactional(readOnly = true)
    public List<Producto> consultaSQL(double precioInf, double precioSup){
        return productoRepository.consultaSQL(precioInf, precioSup);
    }
    
    //consultas de practica 2
    @Transactional(readOnly = true)
    public List<Producto> consultaDerividaProducto(double precioInf, double precioSup, int minExistencias, String descFiltro){
        return productoRepository.findByActivoTrueAndPrecioBetweenAndExistenciasGreaterThanAndCategoriaActivoTrueAndCategoriaDescripcionContainingOrderByPrecioAsc(precioInf, precioSup, minExistencias, descFiltro);
    }
    //consulta JPQL
    @Transactional(readOnly = true)
    public List<Producto> consultaAdvProductoJPQL(double precioInf, double precioSup,int minExistencias, String descFiltro){
        return productoRepository.consultaAvanzadaJPQL(precioInf, precioSup, minExistencias, descFiltro);
    }
    //consulta SQL nativo
    @Transactional(readOnly = true)
    public List<Producto> consultaAdvProductoSQL(double precioInf, double precioSup, int minExistencias, String descFiltro){
        return productoRepository.consultaAvanzadaSQL(precioInf, precioSup, minExistencias, descFiltro);
    }
    
}
