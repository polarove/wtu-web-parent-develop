package cn.neorae.wtu.module.account.mapper;

import cn.neorae.wtu.module.account.domain.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* @author Neorae
* @description 针对表【user(用户)】的数据库操作Mapper
* @createDate 2023-09-20 22:37:41
* @Entity cn.neorae.wtu.module.account.domain.User
*/
public interface UserMapper extends BaseMapper<User> {
    void register (User user);

    User getUserByUUID(String uuid, Integer notDeleted);
}




