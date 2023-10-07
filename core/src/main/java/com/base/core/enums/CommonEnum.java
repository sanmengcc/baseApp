package com.base.core.enums;

import lombok.AllArgsConstructor;

/**
 * 通用类型枚举
 */
public interface CommonEnum {


    /**
     * 是否还有子级
     */
    @AllArgsConstructor
    enum HasChild implements IDictEvent {

        N("否","1"),

        Y("是", "2"),
        ;
        private String name;
        private String code;

        @Override
        public String getName() {
            return this.name;
        }

        @Override
        public String getCode() {
            return this.code;
        }
    }
}
