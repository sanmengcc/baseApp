package com.base.app.api.core;

import com.base.app.dto.core.staff.StaffDTO;
import com.base.app.ro.core.staff.AddRo;
import com.base.app.ro.core.staff.DeleteRo;
import com.base.app.ro.core.staff.EditRo;
import com.base.app.ro.core.staff.SearchRo;
import com.base.app.service.core.StaffService;
import com.base.core.annotation.Api;
import com.base.core.api.BaseAPI;
import com.base.core.entity.R;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Api(name = "员工模块")
@RestController
@RequestMapping(value = "/api/staff")
public class StaffAPI extends BaseAPI {

    @Resource
    private StaffService staffService;


    @Api(name = "分页查询员工列表")
    @GetMapping(value = "/page")
    public R page(SearchRo ro) {
        return R.ok(staffService.searchPage(ro));
    }

    @Api(name = "新增员工")
    @PostMapping(value = "/add")
    public R add(@RequestBody AddRo addRo) {
        this.staffService.addStaff(addRo);
        return R.ok();
    }

    @Api(name = "修改员工详情")
    @PostMapping(value = "/edit")
    public R edit(@RequestBody EditRo editRo) {
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
    @GetMapping(value = "/info")
    public R info(String staffId) {
        StaffDTO staffDTO = this.staffService.selectById(staffId);
        return R.ok(staffDTO);
    }

}
