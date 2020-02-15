package feignsvc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import feignsvc.bean.Product;
import feignsvc.service.ProductService;
 
  
@Controller
public class ProductController {
  
    @Autowired ProductService productService;
    @Value("${version}")
    String version;
    @RequestMapping("/products")
    public Object products(Model m) {
        List<Product> ps = productService.listProducts();
        m.addAttribute("ps", ps);
        m.addAttribute("version", version);  
        return "products";
    }
}