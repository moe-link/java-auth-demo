package com.moesama.giteedemo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
@NoArgsConstructor
public class Model implements Serializable {

    private static final long serialVersionUID  = 1L;

    private String userName;
    private String userImage;

}
