package feignsvc.client;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import feignsvc.bean.Product;
 
@FeignClient(value = "PRODUCT-DATA-SERVICE")
public interface ProductClientFeign {
    @GetMapping("/products")
    public List<Product> listProdcuts();
}