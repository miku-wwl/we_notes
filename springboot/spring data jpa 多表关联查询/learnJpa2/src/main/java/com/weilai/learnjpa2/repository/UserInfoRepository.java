package com.weilai.learnjpa2.repository;

import com.weilai.learnjpa2.domain.ViewInfo;
import com.weilai.learnjpa2.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {
    @Query(value = "SELECT new com.weilai.learnjpa2.domain.ViewInfo(u, a) FROM UserInfo u JOIN Address a ON u.addressId = a.addressId")
    public List<ViewInfo> findViewInfo();

    @Query(value = "SELECT u.addressId FROM UserInfo u")
    public List<Long> findAddressIds();

    public UserInfo findByAddressId(Long addressId);
}