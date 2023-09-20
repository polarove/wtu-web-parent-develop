package cn.neorae.wtu.module.account.mapper;

import cn.neorae.wtu.module.account.domain.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* @author Neorae
* @description 针对表【user(用户)】的数据库操作Mapper
* @createDate 2023-09-18 09:10:08
* @Entity cn.neorae.wtu.module.account.domain.User
*/
public interface UserMapper extends BaseMapper<User> {

    void register(User user);
}




