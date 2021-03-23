package com.github.gongsir0630.shirodemo;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;

/**
 * @author gongsir <a href="https://github.com/gongsir0630">码之泪殇</a>
 * 描述: Spring Boot 工程启动类,可以直接点击下面的main方法运行程序
 */

@SpringBootApplication
public class ShiroJwtDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShiroJwtDemoApplication.class, args);
    }

    /**
     * fastjson 配置注入: 使用阿里巴巴的 fastjson 处理 json 信息
     * @return HttpMessageConverters
     */
    @Bean
    public HttpMessageConverters fastJsonHttpMessageConverters() {
        // 消息转换对象
        FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
        // fastjson 配置
        FastJsonConfig config = new FastJsonConfig();
        config.setSerializerFeatures(SerializerFeature.PrettyFormat);
        config.setDateFormat("yyyy-MM-dd");
        // 配置注入消息转换器
        converter.setFastJsonConfig(config);
        // 让 spring 使用自定义的消息转换器
        return new HttpMessageConverters(converter);
    }
}
