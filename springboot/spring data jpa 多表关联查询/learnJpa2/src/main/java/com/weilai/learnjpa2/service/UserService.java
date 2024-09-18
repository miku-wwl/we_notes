package com.weilai.learnjpa2.service;

import com.weilai.learnjpa2.domain.ViewInfo;
import com.weilai.learnjpa2.entity.Address;
import com.weilai.learnjpa2.entity.UserInfo;
import com.weilai.learnjpa2.repository.AddressRepository;
import com.weilai.learnjpa2.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Transactional
    public List<ViewInfo> findViewInfo() {
        List<ViewInfo> viewInfos = new ArrayList<>();

        // 查询所有 userInfo表中的addressId 记录
        List<Long> addressIdList = userInfoRepository.findAddressIds();
        addressIdList.forEach(it -> {
            UserInfo userInfo = userInfoRepository.findByAddressId(it);
            Address address = addressRepository.findByAddressId(it);
            if (address != null) {
                ViewInfo viewInfo = new ViewInfo(userInfo, address);
                viewInfos.add(viewInfo);
            }
        });

        return viewInfos;
    }
}