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
    <el-form ref="form" :model="module" :rules="rules" label-position="right" label-width="100px">
      <el-form-item label="角色名称" prop="roleName">
        <el-input v-model="module.roleName" placeholder="请输入角色名称" show-word-limit maxlength="20"/>
      </el-form-item>
      <el-form-item label="角色编码" prop="roleCode">
        <el-input v-model="module.roleCode" placeholder="请输入角色编码" show-word-limit maxlength="20"/>
      </el-form-item>
      <el-form-item label="角色类型" prop="roleType">
        <el-select v-model="module.roleType" value="" placeholder="请选择角色类型">
          <el-option
            v-for="item in form.dict.ROLE_TYPE"
            :key="item.dictKey"
            :label="item.dictValue"
            :value="String(item.dictKey)"
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
        roleName: '',
        roleCode: '',
        roleType: ''
      },
      // 表单参数
      form: {
        dict: {}
      },
      // 按钮loading
      buttonLoading: false,
      // 页面大小控制
      screenWidth: 0,
      loading: false,
      width: this.pageApi.initTabWidth(),
      // rule规则验证
      rules: {
        roleName: [
          { required: true, message: '角色名称不能为空', trigger: 'blur' },
          { min: 1, max: 20, message: '角色名称不能超过20个字符', trigger: 'blur' }
        ],
        roleCode: [
          { required: true, message: '角色编码不能为空', trigger: 'blur' },
          { min: 1, max: 20, message: '角色编码不能超过20个字符', trigger: 'blur' }
        ],
        roleType: { required: true, message: '角色类型不能为空', trigger: 'change' }
      }
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
    // 初始化数据字典
    this.$api.cloud.sys.getDictMap('ROLE_TYPE').then(res => { this.form.dict = res.data })
    window.onresize = () => {
      return (() => {
        this.width = this.pageApi.initTabWidth()
      })()
    }
  },
  methods: {

    // 初始化设置form表单数据
    setModule(param) {

    },
    // 关闭窗口并且透传
    close() {
      this.$emit('close', 'addTab')
      this.reset()
      this.buttonLoading = false
    },
    // 提交表单
    submitForm() {
      this.$refs.form.validate((valid) => {
        if (valid) {
          this.buttonLoading = true
          this.$api.cloud.role.add(this.module).then((res) => {
            this.buttonLoading = false
            this.isVisible = false
            this.$message({
              message: '新增成功',
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
      this.module = { seq: 0,
        roleName: '',
        roleCode: '',
        roleType: ''
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
