package com.tienda.controller;

import com.tienda.service.ProductoService;
import com.tienda.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/consultas")
public class ConsultaController {

    private final ProductoService productoService;
    @Autowired
    private CategoriaService categoriaService;

    public ConsultaController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @GetMapping("/listado")
    public String listado(Model model) {
        var productos = productoService.getProductos(false);
        model.addAttribute("productos", productos);
        return "/consultas/listado";
    }
    //pagina de listado para categorias
    @GetMapping("/listado_1")
    public String listado_1(Model model) {
        var categorias = categoriaService.getCategorias(false);
        model.addAttribute("categorias", categorias);
        return "/consultas/listado_1";
    }

    @PostMapping("/consultaDerivada")
    public String consultaDerivida(@RequestParam() double precioInf,
            @RequestParam() double precioSup,
            Model model) {
        var productos = productoService.consultaDerivada(precioInf, precioSup);
        model.addAttribute("productos", productos);
        model.addAttribute("precioInf", precioInf);
        model.addAttribute("precioSup", precioSup);
        return "/consultas/listado";
    }

    @PostMapping("/consultaJPQL")
    public String consultaJPQL(@RequestParam() double precioInf,
            @RequestParam() double precioSup,
            Model model) {
        var productos = productoService.consultaJPQL(precioInf, precioSup);
        model.addAttribute("productos", productos);
        model.addAttribute("precioInf", precioInf);
        model.addAttribute("precioSup", precioSup);
        return "/consultas/listado";
    }

    @PostMapping("/consultaSQL")
    public String consultaSQL(@RequestParam() double precioInf,
            @RequestParam() double precioSup,
            Model model) {
        var productos = productoService.consultaSQL(precioInf, precioSup);
        model.addAttribute("productos", productos);
        model.addAttribute("precioInf", precioInf);
        model.addAttribute("precioSup", precioSup);
        return "/consultas/listado";
    }
    
    /////////////practica 2 
    //consultas avanzadas de producto
    @PostMapping("/consultaDerivadaProducto")
    public String consultaDerividaProducto(@RequestParam() double precioInf,
            @RequestParam() double precioSup, int minExistencias, String descFiltro,
            Model model) {
        var productos = productoService.consultaDerividaProducto(precioInf, precioSup,  minExistencias, descFiltro);
        model.addAttribute("productos", productos);
        model.addAttribute("precioInf", precioInf);
        model.addAttribute("precioSup", precioSup);
        model.addAttribute("minExistencias", minExistencias);
        model.addAttribute("descFiltro", descFiltro);
        return "/consultas/listado";
    }

    @PostMapping("/consultaAdvProductoJPQL")
    public String consultaAdvProductoJPQL(@RequestParam() double precioInf,
            @RequestParam() double precioSup,  int minExistencias, String descFiltro,
            Model model) {
        var productos = productoService.consultaAdvProductoJPQL(precioInf, precioSup, minExistencias, descFiltro);
        model.addAttribute("productos", productos);
        model.addAttribute("precioInf", precioInf);
        model.addAttribute("precioSup", precioSup);
        model.addAttribute("minExistencias", minExistencias);
        model.addAttribute("descFiltro", descFiltro);
        return "/consultas/listado";
    }

    @PostMapping("/consultaAdvProductoSQL")
    public String consultaAdvProductoSQL(@RequestParam() double precioInf,
            @RequestParam() double precioSup, int minExistencias, String descFiltro,
            Model model) {
        var productos = productoService.consultaAdvProductoSQL(precioInf, precioSup, minExistencias, descFiltro);
        model.addAttribute("productos", productos);
        model.addAttribute("precioInf", precioInf);
        model.addAttribute("precioSup", precioSup);
        model.addAttribute("minExistencias", minExistencias);
        model.addAttribute("descFiltro", descFiltro);
        return "/consultas/listado";
    }
    
    //consultas avanzadas de categoria
    
    @PostMapping("/consultaDerivadaCategoria")
    public String consultaDerividaCategoria(@RequestParam() String textoDescripcion,
            /*@RequestParam() int cantidadMinProductos,*/
            Model model) {
        var categorias = categoriaService.consultaDerivadaCategoria(textoDescripcion/*, cantidadMinProductos*/);
        model.addAttribute("categorias", categorias);
        model.addAttribute("textoDescripcion", textoDescripcion);
        //model.addAttribute("cantidadMinProductos", cantidadMinProductos);
        return "/consultas/listado_1";
    }
    
    @PostMapping("/consultaAdvCategoriaJPQL")
    public String consultaAdvCategoriaJPQL(@RequestParam() String textoDescripcion,
            @RequestParam() int cantidadMinProductos,
            Model model) {
        var categorias = categoriaService.consultaAdvCategoriaJPQL(textoDescripcion, cantidadMinProductos);
        model.addAttribute("categorias", categorias);
        model.addAttribute("textoDescripcion", textoDescripcion);
        model.addAttribute("cantidadMinProductos", cantidadMinProductos);
        return "/consultas/listado_1";
    }

    @PostMapping("/consultaAdvCategoriaSQL")
    public String consultaAdvCategoriaSQL(@RequestParam() String textoDescripcion,
            @RequestParam() int cantidadMinProductos,
            Model model) {
        var categorias = categoriaService.consultaAdvCategoriaSQL(textoDescripcion, cantidadMinProductos);
        model.addAttribute("categorias", categorias);
        model.addAttribute("textoDescripcion", textoDescripcion);
        model.addAttribute("cantidadMinProductos", cantidadMinProductos);
        return "/consultas/listado_1";
    }
    
    
}
