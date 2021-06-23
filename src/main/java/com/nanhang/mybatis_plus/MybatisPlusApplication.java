package com.nanhang.mybatis_plus;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;



//     http://localhost:8080/doc.html

@SpringBootApplication
@EnableTransactionManagement
@EnableScheduling  //开启springtask
@MapperScan("com.nanhang.mybatis_plus.dao")
public class MybatisPlusApplication implements InitializingBean {

    public static void main(String[] args) {
        SpringApplication.run(MybatisPlusApplication.class, args);
    }

    //分页插件
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

    //雪花算法
//    @Bean
//    public Snowflake snowflake() {
//        return new Snowflake(1, 1);
//    }

    /**
     * 设置 redisTemplate 的序列化设置
     *
     * @param redisConnectionFactory
     * @return
     */
    @Bean
    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        // 1.创建 redisTemplate 模版
        RedisTemplate<Object, Object> template = new RedisTemplate<>();
        // 2.关联 redisConnectionFactory
        template.setConnectionFactory(redisConnectionFactory);
        // 3.创建 序列化类
        GenericToStringSerializer genericToStringSerializer = new GenericToStringSerializer(Object.class);
        // 6.序列化类，对象映射设置
        // 7.设置 value 的转化格式和 key 的转化格式
        template.setValueSerializer(genericToStringSerializer);
        template.setKeySerializer(new StringRedisSerializer());
        template.afterPropertiesSet();
        return template;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
  //在spring装载完所有的bean的时候，会自动调用实现InitializingBean的afterPropertiesSet（）方法，在里面去调用初始化用的服务或者逻辑
    }
}
