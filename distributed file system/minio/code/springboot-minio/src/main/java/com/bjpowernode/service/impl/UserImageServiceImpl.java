package com.bjpowernode.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bjpowernode.entity.UserImage;
import com.bjpowernode.mapper.UserImageMapper;
import com.bjpowernode.service.UserImageService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
* @author Imy
* @description 针对表【t_user_image】的数据库操作Service实现
* @createDate 2024-04-07 10:55:48
*/
@Service
public class UserImageServiceImpl extends ServiceImpl<UserImageMapper, UserImage>
    implements UserImageService{

    @Resource
    private UserImageMapper userImageMapper;

    @Override
    public boolean saveOrUpdateUserImage(Integer uid, String bucket, String object) {
        boolean flag = false;

        UserImage userImage = new UserImage();
        userImage.setBucket(bucket);
        userImage.setObject(object);
        userImage.setUid(uid);

        LambdaQueryWrapper<UserImage> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserImage::getUid, uid);
        Long count = userImageMapper.selectCount(wrapper);
        if (count <= 0) {
            userImage.setCreateTime(new Date());
            flag =  userImageMapper.insert(userImage) >= 1;
        } else {
            userImage.setUpdateTime(new Date());
            flag = userImageMapper.update(userImage, wrapper) >= 1;
        }
        return flag;
    }
}




