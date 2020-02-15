package feignsvc.service;

 
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import feignsvc.bean.Product;
import feignsvc.client.ProductClientFeign;
 
@Service
public class ProductService {
    @Autowired ProductClientFeign productClientFeign;
    public List<Product> listProducts(){
        return productClientFeign.listProdcuts();
    }
}