package com.bjpowernode.controller;

import com.bjpowernode.config.MinIOInfo;
import com.bjpowernode.entity.UserInfo;
import com.bjpowernode.result.R;
import com.bjpowernode.service.UserContractService;
import com.bjpowernode.service.UserImageService;
import com.bjpowernode.service.UserInfoService;
import io.minio.*;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@CrossOrigin //支持跨域访问
@RestController
public class UserInfoController {

    @Resource
    private UserInfoService userInfoService;

    @Resource
    private UserImageService userImageService;

    @Resource
    private UserContractService userContractService;

    @Resource
    private MinioClient minioClient;

    @Resource
    private MinIOInfo minIOInfo;

    @GetMapping(value = "/api/users")
    public R users() {
        List<UserInfo> userInfoList = userInfoService.getUserList();
        return R.OK(userInfoList);
    }

    @PostMapping(value = "/api/user/image")
    public R image(MultipartFile file, @RequestParam(value = "id") Integer id) throws Exception {
        //1234.jpg
        String subfix = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));
        String object = id+subfix;
        ObjectWriteResponse objectWriteResponse = minioClient.putObject(PutObjectArgs.builder()
                .bucket(minIOInfo.getBucket())
                .object(object) //.png  .jpg
                .stream(file.getInputStream(), file.getSize(), -1)
                .build()
        );
        System.out.println(objectWriteResponse);

        userImageService.saveOrUpdateUserImage(id, minIOInfo.getBucket(), object);

        return R.OK();
    }

    @PostMapping(value = "/api/user/contract")
    public R contract(MultipartFile file, @RequestParam(value = "id") Integer id) throws Exception {
        //1234.jpg
        String subfix = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf("."));
        String object = id+subfix;
        ObjectWriteResponse objectWriteResponse = minioClient.putObject(PutObjectArgs.builder()
                .bucket(minIOInfo.getBucket())
                .object(object)
                .stream(file.getInputStream(), file.getSize(), -1)
                .build()
        );
        System.out.println(objectWriteResponse);

        userContractService.saveOrUpdateUserContract(id, minIOInfo.getBucket(), object);
        return R.OK();
    }

    @GetMapping(value = "/api/user/{id}")
    public R user(@PathVariable(value = "id") Integer id) {
        UserInfo userInfo = userInfoService.getUserById(id);
        return R.OK(userInfo);
    }

    @PutMapping(value = "/api/user")
    public R updateUser(UserInfo userInfo) throws Exception {
        boolean update = userInfoService.updateById(userInfo);
        return update ? R.OK() : R.FAIL();
    }

    @GetMapping(value = "/api/download/{id}")
    public void download(@PathVariable(value = "id") Integer id, HttpServletResponse response) throws Exception {

        UserInfo userInfo = userInfoService.getUserById(id);

        String bucket = userInfo.getUserContractDO().getBucket();
        String object = userInfo.getUserContractDO().getObject();

        //要想让浏览器弹出下载框，你后端要设置一下响应头信息
        response.setContentType("application/octet-stream");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(object, StandardCharsets.UTF_8));

        GetObjectResponse getObjectResponse = minioClient.getObject(GetObjectArgs.builder()
                .bucket(bucket)
                .object(object)
                .build());

        getObjectResponse.transferTo(response.getOutputStream());
    }

    @DeleteMapping(value = "/api/user/{id}")
    public R delUser(@PathVariable(value = "id") Integer id) {
        try {
            boolean del = userInfoService.delUserById(id);
            return del ? R.OK() : R.FAIL();
        } catch (Exception e) {
            e.printStackTrace();
            return R.FAIL();
        }
    }
}
