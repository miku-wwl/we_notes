package com.bjpowernode.service;

import com.bjpowernode.entity.UserContract;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author Imy
* @description 针对表【t_user_contract】的数据库操作Service
* @createDate 2024-04-07 10:55:56
*/
public interface UserContractService extends IService<UserContract> {

    boolean saveOrUpdateUserContract(Integer uid, String bucket, String object);

}
