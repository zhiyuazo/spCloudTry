package feignsvc;

import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.metrics.eventstream.HystrixMetricsStreamServlet;

import brave.sampler.Sampler;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.net.NetUtil;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.NumberUtil;
 
@SpringBootApplication
@EnableEurekaClient
@EnableDiscoveryClient  //表示用于发现eureka 注册中心的微服务。
@EnableFeignClients
@EnableCircuitBreaker  //将断路器信息共享给监控中心
public class FeignServiceApplication {
 
    public static void main(String[] args) {
        int port = 0;
        int defaultPort = 8012;
        Future<Integer> future = ThreadUtil.execAsync(() ->{
                int p = 0;
                System.out.println("请于5秒钟内输入端口号, 推荐  8010  超过5秒将默认使用 " + defaultPort);
                Scanner scanner = new Scanner(System.in);
                while(true) {
                    String strPort = scanner.nextLine();
                    if(!NumberUtil.isInteger(strPort)) {
                        System.err.println("只能是数字");
                        continue;
                    }
                    else {
                        p = Convert.toInt(strPort);
                        scanner.close();
                        break;
                    }
                }
                return p;
        });
        try{
            port=future.get(5,TimeUnit.SECONDS);
        }
        catch (InterruptedException | ExecutionException | TimeoutException e){
            port = defaultPort;
        }
        if(!NetUtil.isUsableLocalPort(port)) {
            System.err.printf("端口%d被占用了，无法启动%n", port );
            System.exit(1);
        }
        new SpringApplicationBuilder(FeignServiceApplication.class).properties("server.port=" + port).run(args);
 
    }
    @Bean
    public Sampler defaultSampler() {
        return Sampler.ALWAYS_SAMPLE;
    }   
    
    @Bean//遇到Unable to connect to Command Metric Stream.
    public ServletRegistrationBean getServlet() {
        HystrixMetricsStreamServlet streamServlet = new HystrixMetricsStreamServlet();
        ServletRegistrationBean registrationBean = new ServletRegistrationBean(streamServlet);
        registrationBean.setLoadOnStartup(1);
        registrationBean.addUrlMappings("/hystrix.stream");
        registrationBean.setName("HystrixMetricsStreamServlet");
        return registrationBean;
    }
    
}