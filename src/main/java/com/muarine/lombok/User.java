/*
 * RT MAP, Home of Professional MAP
 * Copyright 2017 Bit Main Inc. and/or its affiliates and other contributors
 * as indicated by the @author tags. All rights reserved.
 * See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 */
package com.muarine.lombok;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author maoyun@rtmap.com
 * @project hello
 * @package com.muarine.lombok
 * @date 5/28/18
 */
@Setter
@Getter
@EqualsAndHashCode(exclude = {"id"}, doNotUseGetters = true)
@Builder
public class User implements Serializable {

    private Long id;
    private String name;
    private Integer age;
    private String email;

    public static void main(String[] args) {
        User build = User.builder().id(1L).age(15).email("rtmap.com").build();
    }
}
