package productsvc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import productsvc.bean.Product;
import productsvc.service.ProductService;


@RestController
public class ProductController {
  
    @Autowired ProductService productService;
     
    @RequestMapping("/products")
    public Object products() {
        List<Product> ps = productService.listProducts();
        return ps;
    }
}
