package com.bjpowernode.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bjpowernode.entity.UserContract;
import com.bjpowernode.entity.UserImage;
import com.bjpowernode.entity.UserInfo;
import com.bjpowernode.mapper.UserContractMapper;
import com.bjpowernode.mapper.UserImageMapper;
import com.bjpowernode.mapper.UserInfoMapper;
import com.bjpowernode.service.UserInfoService;
import io.minio.MinioClient;
import io.minio.RemoveObjectArgs;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
* @author Imy
* @description 针对表【t_user_info】的数据库操作Service实现
* @createDate 2024-04-07 10:54:56
*/
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo>
    implements UserInfoService {

    @Resource
    private MinioClient minioClient;

    @Resource
    private UserInfoMapper userInfoMapper;

    @Resource
    private UserImageMapper userImageMapper;

    @Resource
    private UserContractMapper userContractMapper;

    @Override
    public List<UserInfo> getUserList() {
        return userInfoMapper.selectUserList();
    }

    @Override
    public UserInfo getUserById(Integer id) {
        return userInfoMapper.selectUserById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean delUserById(Integer id) throws Exception {

        UserInfo userInfo = userInfoMapper.selectUserById(id);

        //删除用户
        int delUser = userInfoMapper.deleteById(id);

        //删除头像
        LambdaQueryWrapper<UserImage> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserImage::getUid, id);
        int delImage = userImageMapper.delete(wrapper);

        //删除合同
        LambdaQueryWrapper<UserContract> contractWrapper = new LambdaQueryWrapper<>();
        contractWrapper.eq(UserContract::getUid, id);
        int delContract = userContractMapper.delete(contractWrapper);

        if (null != userInfo.getUserImageDO()) {
            //删除MinIO服务器上的文件 (删除头像)
            minioClient.removeObject(RemoveObjectArgs.builder()
                    .bucket(userInfo.getUserImageDO().getBucket())
                    .object(userInfo.getUserImageDO().getObject())
                    .build());
        }

        if (null != userInfo.getUserContractDO()) {
            //删除MinIO服务器上的文件 (删除合同)
            minioClient.removeObject(RemoveObjectArgs.builder()
                    .bucket(userInfo.getUserContractDO().getBucket())
                    .object(userInfo.getUserContractDO().getObject())
                    .build());
        }

        return delUser >= 1;
    }
}




