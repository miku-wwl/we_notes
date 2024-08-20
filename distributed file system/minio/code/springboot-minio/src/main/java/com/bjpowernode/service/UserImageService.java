package com.bjpowernode.service;

import com.bjpowernode.entity.UserImage;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author Imy
* @description 针对表【t_user_image】的数据库操作Service
* @createDate 2024-04-07 10:55:48
*/
public interface UserImageService extends IService<UserImage> {

    boolean saveOrUpdateUserImage(Integer uid, String bucket, String object);
}
