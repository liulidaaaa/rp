package com.rp.largegarbage;

import com.rp.largegarbage.socket.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.util.unit.DataSize;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import javax.servlet.MultipartConfigElement;


@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.rp.largegarbage.dao")
public class LargegarbageApplication implements CommandLineRunner {

    /*@Autowired
    private NettyServer nettyServer;*/

    /*@Autowired
    private NettyClient nettyClient;*/

    /** logger */
    private static final Logger LOGGER = LoggerFactory.getLogger(LargegarbageApplication.class);

    /*@Override
    public void run(String...strings) throws Exception {
        InetSocketAddress address = new InetSocketAddress(DefaultConstants.SOCKET_IP, DefaultConstants.SOCKET_PORT);
        LOGGER.info("netty服务器启动地址： "+ DefaultConstants.SOCKET_IP);
        nettyServer.start(address);
    }*/
    /*@Override
    public void run(String...strings) throws Exception {
        new Thread(new NettyClient()).start();
        LOGGER.info("netty客户端启动。。。");
    }*/
    @Override
    public void run(String...strings) throws Exception {
        //new Client("119.57.53.254",1502).start();
        LOGGER.info("socket客户端启动。。。");
    }

    /**
     * SpringBoot添加跨域设置
     */
    @Configuration
    @EnableWebMvc
    public class WebMvcConfg implements WebMvcConfigurer {
        @Override
        public void addCorsMappings(CorsRegistry registry) {
            WebMvcConfigurer.super.addCorsMappings(registry);
            registry.addMapping("/**")//需要跨域访问的Map路径
                    .allowedOrigins("http://localhost:4200")//允许跨域访问的ip及端口
                    .allowedHeaders("*")//允许跨域访问的Headers内容
                    .allowedMethods("POST", "GET", "PUT", "OPTIONS", "DELETE")//允许跨域访问的方法，OPTIONS必须设置Shiro中用到
                    .allowCredentials(true);
        }
    }


    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        //单个文件最大   KB,MB
        factory.setMaxFileSize(DataSize.parse("100KB"));
        /// 设置总上传数据总大小
        factory.setMaxRequestSize(DataSize.parse("1024000KB"));
        return factory.createMultipartConfig();
    }

    /**
     * SpringBoot主方法
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(LargegarbageApplication.class, args);
    }

}
