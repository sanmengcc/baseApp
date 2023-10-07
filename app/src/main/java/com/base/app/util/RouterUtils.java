package com.base.app.util;

import com.base.app.dto.module.SystemModuleDTO;
import com.base.app.dto.user.VueRouterModuleVo;
import com.base.app.enums.ModuleEnum;
import com.base.core.constant.CommonConstants;
import com.base.util.TreeUtils;
import com.base.util.ValidateHelper;

import java.util.*;
import java.util.stream.Collectors;

public class RouterUtils {

    /**
     * 构建Vue菜单
     * @param moduleVos
     * @return
     */
    public static List<VueRouterModuleVo> buildVueRouter(List<SystemModuleDTO> moduleVos) {
        moduleVos = moduleVos.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(
                () -> new TreeSet<>(Comparator.comparing(SystemModuleDTO::getModuleId))), ArrayList::new));

        List<SystemModuleDTO> dataList = moduleVos
                .stream()
                .filter(m -> m.getType().equals(ModuleEnum.Type.ROUTER.getCode()) || m.getType().equals(ModuleEnum.Type.DIR.getCode()) || m.equals(ModuleEnum.Type.LINK.getCode()))
                .collect(Collectors.toList());
        // 获取顶级节点
        Map<String, String> moduleIdMap = dataList.stream()
                .collect(Collectors.toMap(d -> d.getModuleId(), d -> d.getParentId()));
        // 变更hidden节点
        dataList.stream()
                .filter(d -> ModuleEnum.Hidden.HIDDEN.getCode().equals(d.getHidden()))
                .forEach(d->{
                    String topModuleId = matchHiddenNodeFormTop(moduleIdMap, d.getParentId());
                    if (ValidateHelper.isNotEmptyString(topModuleId)) {
                        d.setParentId(topModuleId);
                    }
                });

        List<VueRouterModuleVo> dataTree = dataList
                .stream()
                .map(module -> {
                    VueRouterModuleVo router = new VueRouterModuleVo();
                    router.setId(module.getModuleId());
                    router.setParentId(module.getParentId());
                    router.setSeq(module.getSeq());
                    router.setName(module.getRouterName());
                    router.setPath(module.getRouterName());
                    router.setComponent(module.getJumpAction());
                    if (module.getType().equals(ModuleEnum.Type.DIR.getCode()) && ValidateHelper.isEmptyString(module.getJumpAction())) {
                        router.setComponent("Layout");
                    }
                    if (CommonConstants.Flag.YES.equals(module.getHasChild())) {
                        router.setAlwaysShow(true);
                    }else {
                        router.setAlwaysShow(false);
                    }
                    Map<String, String> meta = new HashMap<>();
                    meta.put("title", module.getName());
                    meta.put("icon", module.getIcon());
                    router.setMeta(meta);
                    if (ModuleEnum.Hidden.HIDDEN.getCode().equals(module.getHidden())) {
                        router.setHidden(true);
                    }
                    return router;
                }).collect(Collectors.toList());
        TreeUtils<VueRouterModuleVo> treeUtils = new TreeUtils<>() {
            @Override
            protected Object getKey(VueRouterModuleVo node) {
                return node.getId();
            }

            @Override
            protected Object getParentId(VueRouterModuleVo node) {
                return node.getParentId();
            }

            @Override
            protected List<VueRouterModuleVo> getChildren(VueRouterModuleVo node) {
                return node.getChildren();
            }

            @Override
            protected void setChildren(List<VueRouterModuleVo> nodes, VueRouterModuleVo node) {
                if (ValidateHelper.isNotEmptyCollection(nodes)) {
                    nodes.sort(Comparator.comparing(VueRouterModuleVo::getSeq, Comparator.nullsLast(Integer::compareTo)));
                    node.setChildren(nodes);
                }
            }
        };
        return treeUtils.getTree(dataTree);
    }

    /**
     * 寻找顶级节点
     * @param moduleIdMap
     * @param hiddenModuleId
     * @return
     */
    public static String matchHiddenNodeFormTop(Map<String, String> moduleIdMap, String hiddenModuleId) {
        String moduleId = moduleIdMap.get(hiddenModuleId);
        if (ValidateHelper.isNotEmptyString(moduleId) && moduleId.equals(CommonConstants.PARENT_ID.toString())) {
            return matchHiddenNodeFormTop(moduleIdMap, moduleId);
        }else {
            return moduleId;
        }
    }
}
