package com.tiendasara.controllers;

import com.tiendasara.models.Product;
import com.tiendasara.models.ProductDto;
import com.tiendasara.models.Category;
import com.tiendasara.models.Mark;
import com.tiendasara.services.ProductRepository;
import com.tiendasara.services.CategoryRepository;
import com.tiendasara.services.MarkRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/products")
public class ProductController {
    
    @Autowired
    private ProductRepository productRepository;
    
    @Autowired
    private CategoryRepository categoryRepository;
    
    @Autowired
    private MarkRepository markRepository;
    
    // GET - Listar todos los productos
    @GetMapping("")
    public String index(Model model) {
        List<Product> products = productRepository.findAll();
        model.addAttribute("products", products);
        return "products/index";
    }
    
    // GET - Mostrar formulario para crear nuevo producto
    @GetMapping("/new")
    public String create(Model model) {
        model.addAttribute("productDto", new ProductDto());
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("marks", markRepository.findAll());
        return "products/create";
    }
    
    // POST - Guardar nuevo producto
    @PostMapping("")
    public String store(@Valid @ModelAttribute ProductDto productDto, 
                       BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("categories", categoryRepository.findAll());
            model.addAttribute("marks", markRepository.findAll());
            return "products/create";
        }
        
        try {
            Category categoria = categoryRepository.findById(productDto.getIdCategoria())
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
            Mark marca = markRepository.findById(productDto.getIdMarca())
                .orElseThrow(() -> new RuntimeException("Marca no encontrada"));
            
            Product product = new Product(
                productDto.getDescripcion(),
                productDto.getPrecio(),
                productDto.getCantidad(),
                categoria,
                marca,
                productDto.getImagen()
            );
            
            productRepository.save(product);
            return "redirect:/products";
        } catch (Exception e) {
            model.addAttribute("error", "Error al crear el producto: " + e.getMessage());
            model.addAttribute("categories", categoryRepository.findAll());
            model.addAttribute("marks", markRepository.findAll());
            return "products/create";
        }
    }
    
    // GET - Mostrar formulario para editar producto
    @GetMapping("/{id}/edit")
    public String edit(@PathVariable Integer id, Model model) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isEmpty()) {
            return "redirect:/products";
        }
        
        ProductDto productDto = new ProductDto(
            product.get().getId(),
            product.get().getDescripcion(),
            product.get().getPrecio(),
            product.get().getCantidad(),
            product.get().getCategoria().getId(),
            product.get().getMarca().getId(),
            product.get().getImagen()
        );
        
        model.addAttribute("productDto", productDto);
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("marks", markRepository.findAll());
        return "products/edit";
    }
    
    // PUT - Actualizar producto
    @PostMapping("/{id}")
    public String update(@PathVariable Integer id, 
                        @Valid @ModelAttribute ProductDto productDto,
                        BindingResult result, Model model) {
        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isEmpty()) {
            return "redirect:/products";
        }
        
        if (result.hasErrors()) {
            model.addAttribute("categories", categoryRepository.findAll());
            model.addAttribute("marks", markRepository.findAll());
            return "products/edit";
        }
        
        try {
            Product product = productOptional.get();
            Category categoria = categoryRepository.findById(productDto.getIdCategoria())
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
            Mark marca = markRepository.findById(productDto.getIdMarca())
                .orElseThrow(() -> new RuntimeException("Marca no encontrada"));
            
            product.setDescripcion(productDto.getDescripcion());
            product.setPrecio(productDto.getPrecio());
            product.setCantidad(productDto.getCantidad());
            product.setCategoria(categoria);
            product.setMarca(marca);
            product.setImagen(productDto.getImagen());
            product.setUpdatedAt(LocalDateTime.now());
            
            productRepository.save(product);
            return "redirect:/products";
        } catch (Exception e) {
            model.addAttribute("error", "Error al actualizar el producto: " + e.getMessage());
            model.addAttribute("categories", categoryRepository.findAll());
            model.addAttribute("marks", markRepository.findAll());
            return "products/edit";
        }
    }
    
    // DELETE - Eliminar producto
    @GetMapping("/{id}/delete")
    public String delete(@PathVariable Integer id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()) {
            productRepository.deleteById(id);
        }
        return "redirect:/products";
    }
    
    // API REST - GET todos los productos (JSON)
    @GetMapping("/api/all")
    @ResponseBody
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
    
    // API REST - GET producto por ID (JSON)
    @GetMapping("/api/{id}")
    @ResponseBody
    public Optional<Product> getProductById(@PathVariable Integer id) {
        return productRepository.findById(id);
    }
    
    // API REST - POST crear producto (JSON)
    @PostMapping("/api")
    @ResponseBody
    public Product createProductApi(@Valid @RequestBody ProductDto productDto) {
        Category categoria = categoryRepository.findById(productDto.getIdCategoria())
            .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
        Mark marca = markRepository.findById(productDto.getIdMarca())
            .orElseThrow(() -> new RuntimeException("Marca no encontrada"));
        
        Product product = new Product(
            productDto.getDescripcion(),
            productDto.getPrecio(),
            productDto.getCantidad(),
            categoria,
            marca,
            productDto.getImagen()
        );
        
        return productRepository.save(product);
    }
    
    // API REST - PUT actualizar producto (JSON)
    @PutMapping("/api/{id}")
    @ResponseBody
    public Product updateProductApi(@PathVariable Integer id, 
                                    @Valid @RequestBody ProductDto productDto) {
        Product product = productRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        
        Category categoria = categoryRepository.findById(productDto.getIdCategoria())
            .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
        Mark marca = markRepository.findById(productDto.getIdMarca())
            .orElseThrow(() -> new RuntimeException("Marca no encontrada"));
        
        product.setDescripcion(productDto.getDescripcion());
        product.setPrecio(productDto.getPrecio());
        product.setCantidad(productDto.getCantidad());
        product.setCategoria(categoria);
        product.setMarca(marca);
        product.setImagen(productDto.getImagen());
        product.setUpdatedAt(LocalDateTime.now());
        
        return productRepository.save(product);
    }
    
    // API REST - DELETE producto (JSON)
    @DeleteMapping("/api/{id}")
    @ResponseBody
    public String deleteProductApi(@PathVariable Integer id) {
        if (!productRepository.existsById(id)) {
            throw new RuntimeException("Producto no encontrado");
        }
        productRepository.deleteById(id);
        return "{\"message\": \"Producto eliminado correctamente\"}";
    }
}