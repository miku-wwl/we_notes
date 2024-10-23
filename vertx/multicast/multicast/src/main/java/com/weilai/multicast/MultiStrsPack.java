package com.weilai.multicast;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MultiStrsPack implements Serializable {
    private long packNo;
    private List<String> strs;
}