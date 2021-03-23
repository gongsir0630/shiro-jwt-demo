package com.github.gongsir0630.shirodemo.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author 码之泪殇 GitHub: https://github.com/gongsir0630
 * @date 2021/3/19 17:15
 * 你的指尖,拥有改变世界的力量
 * 描述: MyBatis-Plus插件配置
 */

@Configuration
@MapperScan("com.github.gongsir0630.shirodemo.mapper")
public class MybatisPlusConfig {
}
