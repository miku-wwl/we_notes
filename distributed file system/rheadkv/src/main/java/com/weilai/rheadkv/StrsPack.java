package com.weilai.rheadkv;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class StrsPack implements Serializable {
    private long packNo;
    private List<String> strs;
}