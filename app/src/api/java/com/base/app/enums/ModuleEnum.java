package com.base.app.enums;

import com.base.core.enums.IEvent;
import lombok.AllArgsConstructor;

/**
 * 系统类型枚举
 */
public interface ModuleEnum {

    /**
     * 菜单类型
     */
    @AllArgsConstructor
    enum Type implements IEvent {
        DIR("目录","1"),

        LINK("功能链接", "2"),

        BUTTON("按钮", "3"),

        AUTH("访问权限", "4"),

        ROUTER("路由", "5"),
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

    /**
     * 启用状态
     */
    @AllArgsConstructor
    enum EnableStatus implements IEvent {

        ENABLE("启用","1"),

        DISABLE("禁用", "2"),
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

    /**
     * 隐藏状态
     */
    @AllArgsConstructor
    enum Hidden implements IEvent {

        DISPLAY("显示","1"),

        HIDDEN("隐藏", "2"),
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

    /**
     * 跳转方式
     */
    @AllArgsConstructor
    enum JumpType implements IEvent {
        NOW_PAGE("当前页打开","1"),

        NEW_PAGE("打开新页面", "2"),

        NEW_TAB("打开新Tab", "3"),
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
