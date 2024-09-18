package com.weilai.learnjpa2;

import com.weilai.learnjpa2.domain.ViewInfo;
import com.weilai.learnjpa2.entity.Address;
import com.weilai.learnjpa2.entity.UserInfo;
import com.weilai.learnjpa2.repository.AddressRepository;
import com.weilai.learnjpa2.repository.UserInfoRepository;

import com.weilai.learnjpa2.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class LearnJpa2ApplicationTests {

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private UserService userService;

    @BeforeEach
    public void init() {
        Address addr1 = new Address("027", "CN", "HuBei", "WuHan", "WuChang", "123 street");
        Address addr2 = new Address("023", "CN", "ChongQing", "ChongQing", "YuBei", "123 road");
        addressRepository.save(addr1);
        addressRepository.save(addr2);

        UserInfo user1 = new UserInfo("ZS", 21, "Male", "123@xx.com", addr1.getAddressId());
        UserInfo user2 = new UserInfo("Ww", 25, "Male", "234@xx.com", addr2.getAddressId());
        userInfoRepository.save(user1);
        userInfoRepository.save(user2);
    }

    @AfterEach
    public void deleteAll() {
        userInfoRepository.deleteAll();

        addressRepository.deleteAll();
    }

    /**
     * 测试userInfoRepository.findViewInfo(), 使用Query方法，来实现关联查询
     */
    @Test
    public void testRelationQuery() {

        List<ViewInfo> viewInfos = userInfoRepository.findViewInfo();
        for (ViewInfo viewInfo : viewInfos) {
            System.out.println(viewInfo.getUserInfo());
            System.out.println(viewInfo.getAddress());
        }
    }

    /**
     * userService.findViewInfo(), 使用Java Service,多次单表查询，来实现关联查询
     */
    @Test
    public void testServiceQuery() {
        List<ViewInfo> viewInfos = userService.findViewInfo();
        for (ViewInfo viewInfo : viewInfos) {
            System.out.println(viewInfo.getUserInfo());
            System.out.println(viewInfo.getAddress());
        }

    }

}
