package cc.mrbird.febs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableAspectJAutoProxy//开启AOP
@EnableTransactionManagement//开启spring事务管理
@EnableAutoConfiguration //这个不能注释掉，必须的，组合注解内嵌全局com注解，注释的话扫描不到配置类
@EnableCaching//开启spring缓存
public class SpringBootStartApplication extends SpringBootServletInitializer {
    /** --
     public static void main(String[] args) {
     new SpringApplicationBuilder(FebsApplication.class)
     .run(args);
     }
     */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(SpringBootStartApplication.class);
    }
    public static void main(String[] args) {
        SpringApplication.run(FebsApplication.class, args);
    }
}
