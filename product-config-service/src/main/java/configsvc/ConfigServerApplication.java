package configsvc;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

import cn.hutool.core.net.NetUtil;
 
@SpringBootApplication
@EnableDiscoveryClient
@EnableEurekaClient
@EnableConfigServer
public class ConfigServerApplication {
    public static void main(String[] args) {
        int port = 8030;
        if(!NetUtil.isUsableLocalPort(port)) {
            System.err.printf("端口%d被占用了，无法启动%n", port );
            System.exit(1);
        }
        new SpringApplicationBuilder(ConfigServerApplication.class).properties("server.port=" + port).run(args);
//        配置文件http: product-config-service-dev.properties
//        访问方式http://localhost:8030/product-config-service/dev
//        The HTTP service has resources in the following form:
//        	http://localhost:8030/{application}/{profile}[/{label}]
//        	http://localhost:8030/{application}-{profile}.yml
//        	http://localhost:8030/{label}/{application}-{profile}.yml
//        	http://localhost:8030/{application}-{profile}.properties
//        	http://localhost:8030/{label}/{application}-{profile}.properties
        
    }
}