package com.base.app.api.sys;

import com.base.app.ro.oss.OssFileAddRo;
import com.base.app.ro.oss.OssSignatureRo;
import com.base.app.ro.oss.SearchRo;
import com.base.app.service.OssFileService;
import com.base.core.annotation.Api;
import com.base.core.annotation.ApiPermission;
import com.base.core.context.CloudManager;
import com.base.core.entity.R;
import com.base.core.entity.UserInfo;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/api/common/oss")
public class OssAPI {


    @Resource
    private OssFileService ossFileService;

    @Api(name = "获取文件上传签名")
    @ApiPermission(auth = false)
    @PostMapping("/getUploadSignature")
    public R getUploadSignature(@RequestBody @Validated OssSignatureRo ro) {
        return R.ok(ossFileService.getUploadSignature(ro));
    }

    @Api(name = "上传文件URL")
    @ApiPermission(auth = false)
    @PostMapping("/callbackFile")
    public R callbackFile(@RequestBody @Validated OssFileAddRo ro) {
        UserInfo userInfo = CloudManager.getInstance().getUserInfo();
        ro.setCreatorId(userInfo.getUserGlobalId());
        ro.setCreatorName(userInfo.getRealName());
        ossFileService.addFile(ro);
        return R.ok();
    }

    @Api(name = "获取文件存储列表")
    @GetMapping(value = "/searchPage")
    public R searchPage(SearchRo ro) {
        return R.ok(ossFileService.searchPage(ro));
    }
}
