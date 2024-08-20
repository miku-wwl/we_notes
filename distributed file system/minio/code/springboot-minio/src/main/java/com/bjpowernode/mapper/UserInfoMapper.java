package com.bjpowernode.mapper;

import com.bjpowernode.entity.UserInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author Imy
* @description 针对表【t_user_info】的数据库操作Mapper
* @createDate 2024-04-07 10:54:56
* @Entity com.bjpowernode.entity.UserInfo
*/
public interface UserInfoMapper extends BaseMapper<UserInfo> {

    UserInfo selectUserById(Integer id);

    List<UserInfo> selectUserList();
}




