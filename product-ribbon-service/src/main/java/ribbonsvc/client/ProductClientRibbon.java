package ribbonsvc.client;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import ribbonsvc.bean.Product;
 
@Component
public class ProductClientRibbon {
    @Autowired RestTemplate restTemplate; //用这个来去Eruaka获得服务地址
    public List<Product> listProdcuts() {
    	//只需服务名字...ip 和 端口都不需要指定
        return restTemplate.getForObject("http://PRODUCT-DATA-SERVICE/products",List.class);
    }
 
}