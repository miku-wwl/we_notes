package com.weilai.learnjpa2.domain;

import com.weilai.learnjpa2.entity.Address;
import com.weilai.learnjpa2.entity.UserInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ViewInfo {
    private UserInfo userInfo;
    private Address address;
}