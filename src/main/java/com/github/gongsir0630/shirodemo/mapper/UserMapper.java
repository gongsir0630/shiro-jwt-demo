package com.github.gongsir0630.shirodemo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.gongsir0630.shirodemo.model.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 码之泪殇 GitHub: https://github.com/gongsir0630
 * @date 2021/3/22 19:44
 * 你的指尖,拥有改变世界的力量
 * 描述: User类mapper接口,继承自BaseMapper(已经实现User的CRUD)
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
