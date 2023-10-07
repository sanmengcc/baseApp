package com.base.app.api.sys;

import com.base.app.dto.staff.StaffDTO;
import com.base.app.ro.staff.*;
import com.base.app.service.StaffService;
import com.base.core.annotation.Api;
import com.base.core.api.BaseAPI;
import com.base.core.entity.R;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.A;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Api(name = "员工模块")
@RestController
@RequestMapping(value = "/api/staff")
public class StaffAPI extends BaseAPI {

    @Resource
    private StaffService staffService;


    @Api(name = "分页查询员工列表")
    @GetMapping(value = "/searchPage")
    public R searchPage(SearchRo ro) {
        return R.ok(staffService.searchPage(ro));
    }

    @Api(name = "新增员工")
    @PostMapping(value = "/addStaff")
    public R addStaff(@RequestBody AddRo addRo) {
        this.staffService.addStaff(addRo);
        return R.ok();
    }

    @Api(name = "修改员工详情")
    @PostMapping(value = "/updateById")
    public R updateById(@RequestBody EditRo editRo) {
        this.staffService.update(editRo);
        return R.ok();
    }

    @Api(name = "删除员工")
    @PostMapping(value = "/delete")
    public R delete(@RequestBody DeleteRo ro) {
        this.staffService.delete(ro.getStaffId(), ro.getUserGlobalId());
        return R.ok();
    }

    @Api(name = "根据ID查询员工信息")
    @GetMapping(value = "/selectById")
    public R selectById(String staffId) {
        StaffDTO staffDTO = this.staffService.selectById(staffId);
        return R.ok(staffDTO);
    }

}
