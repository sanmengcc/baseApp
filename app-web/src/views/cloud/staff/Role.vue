<template>
  <el-dialog
      :title="title"
      :width="width"
      top="100px"
      v-loading="loading"
      :close-on-click-modal="false"
      :close-on-press-escape="false"
      :visible.sync="addTab"
  >
    <!--  表单部分  -->
    <el-form ref="form" :model="module" label-position="right" label-width="100px">
      <el-form-item label="选择角色" prop="adminType">
        <el-select v-model="module.roleIdList" multiple placeholder="请选择角色">
          <el-option
              v-for="item in form.roleList"
              :key="item.roleId"
              :label="item.roleName"
              :value="item.roleId"
          />
        </el-select>
      </el-form-item>
    </el-form>
    <!--  按钮部分  -->
    <div slot="footer" class="dialog-footer">
      <el-button type="warning" plain :loading="buttonLoading" @click="addTab = false">
        {{ $t('common.cancel') }}
      </el-button>
      <el-button type="primary" plain :loading="buttonLoading" @click="submitForm">
        {{ $t('common.confirm') }}
      </el-button>
    </div>
  </el-dialog>
</template>
<script>

import {getDictMap} from "@/api/cloud/sys";
import {getStaff} from "@/api/cloud/role";

export default {
  name: 'Add',
  props: {
    // 窗口控制
    visible: {
      type: Boolean,
      default: false
    },
    // 窗口标题
    title: {
      type: String,
      default: ''
    }
  },
  data() {
    return {
      // 表单数据
      module: {
        roleIdList: [],
        staffId: ''
      },
      // 表单参数
      form: {
        roleList: []
      },
      // 按钮loading
      buttonLoading: false,
      // 页面大小控制
      screenWidth: 0,
      loading: false,
      width: this.pageApi.initTabWidth(),
    }
  },
  computed: {
    addTab: {
      get() {
        return this.visible
      },
      set() {
        this.close()
        this.reset()
      }
    }
  },
  mounted() {

    // 加载详情
    this.init()
    // 初始化数据字典
    window.onresize = () => {
      return (() => {
        this.width = this.pageApi.initTabWidth()
      })()
    }
  },
  methods: {
    init() {
      this.$api.cloud.role.list().then(res => {
        this.form.roleList = res.data
      })

    },
    // 初始化设置form表单数据
    setModule(param) {
      this.module.staffId = param.staffId
      // 加载数据字典
      const arr = []
      this.$api.cloud.role.getStaff({staffId: this.module.staffId}).then(res => {
        res.data.forEach((item) => {
          arr.push(item.roleId);
        });
        this.module.roleIdList = arr
        console.log(this.module.roleIdList)

      })
    },
    // 关闭窗口并且透传
    close() {
      this.$emit('close', 'roleTab')
      this.reset()
      this.buttonLoading = false
    },
    // 提交表单
    submitForm() {
      this.$refs.form.validate((valid) => {
        if (valid) {
          this.buttonLoading = true
          this.$api.cloud.role.allotRole(this.module).then((res) => {
            this.buttonLoading = false
            this.isVisible = false
            this.$message({
              message: '分配成功',
              type: 'success'
            })
            this.$emit('success')
            this.close()
          }).catch((e) => {
            this.buttonLoading = false
          })
        } else {
          return false
        }
      })
    },
    // 清空表单
    reset() {
      // 先清除校验，再清除表单，不然有奇怪的bug
      this.$refs.form.clearValidate()
      this.$refs.form.resetFields()
      this.module = {
        roleIdList: [],
        staffId: ''
      }
    }
  }
}
</script>
<style lang="scss" scoped>
.el-uploader {
  border: 1px dashed #d9d9d9 !important;
  border-radius: 6px;
  width: 78px;
  overflow: hidden;
}

.icon-uploader {
  border: 1px dashed #d9d9d9 !important;
  border-radius: 6px;
  cursor: pointer;
  width: 78px;
  position: relative;
  overflow: hidden;
}

.avatar-uploader .el-upload:hover {
  border-color: #409EFF;
}

.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 78px;
  height: 78px;
  line-height: 78px;
  text-align: center;
}

.menu-icon {
  width: 78px;
  height: 78px;
  display: block;
}
</style>
