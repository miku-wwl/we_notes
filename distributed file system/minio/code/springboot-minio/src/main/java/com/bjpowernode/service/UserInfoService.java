package com.bjpowernode.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bjpowernode.entity.UserInfo;

import java.util.List;

/**
* @author Imy
* @description 针对表【t_user_info】的数据库操作Service
* @createDate 2024-04-07 10:54:56
*/
public interface UserInfoService extends IService<UserInfo> {

    List<UserInfo> getUserList();

    UserInfo getUserById(Integer id);

    boolean delUserById(Integer id) throws Exception;
}
