package com.base.app.dto.user;

import com.base.core.entity.BaseValue;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.List;

@Data
public class UserPermissionsDTO extends BaseValue {

    /**
     * 菜单权限集
     */
    private List moduleList;

    /**
     * 按钮权限集
     */
    private List<String> buttonCodes;

    /**
     * 请求URL
     */
    @JsonIgnore
    private List<String> requestURL;
}
