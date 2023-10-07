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
      <el-form-item label="系统域名" prop="host">
        <el-input v-model="module.host" placeholder="请输入系统域名" show-word-limit maxlength="20"/>
      </el-form-item>
      <el-form-item label="系统名称">
        <el-input v-model="form.config.sysName" placeholder="请输入系统域名" show-word-limit maxlength="20"/>
      </el-form-item>
      <el-form-item label="所属租户" prop="oemCode">
        <el-select v-model="module.oemCode" placeholder="请选择所属租户">
          <el-option
              v-for="item in form.oem"
              :key="item.oemCode"
              :label="item.oemName"
              :value="String(item.oemCode)"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="网站图标">
        <el-upload
            class="icon-uploader"
            action="#"
            accept=".png, .jpg,.svg"
            :http-request="(params) => upload(params,'favicon')"
            :show-file-list="false" >
          <img v-if="form.config.favicon" :src="form.config.favicon" class="menu-icon">
          <i v-else class="el-icon-plus avatar-uploader-icon"></i>
        </el-upload>
      </el-form-item>
      <el-form-item label="登录页背景图">
        <el-upload
            class="icon-uploader"
            action="#"
            accept=".png, .jpg,.svg"
            :http-request="(params) => upload(params,'backgroundImage')"
            :show-file-list="false" >
          <img v-if="form.config.backgroundImage" :src="form.config.backgroundImage" class="menu-icon">
          <i v-else class="el-icon-plus avatar-uploader-icon"></i>
        </el-upload>
      </el-form-item>
      <el-form-item label="登录页特效图">
        <el-upload
            class="icon-uploader"
            action="#"
            accept=".png, .jpg,.svg"
            :http-request="(params) => upload(params,'loginLeftImage')"
            :show-file-list="false" >
          <img v-if="form.config.loginLeftImage" :src="form.config.loginLeftImage" class="menu-icon">
          <i v-else class="el-icon-plus avatar-uploader-icon"></i>
        </el-upload>
      </el-form-item>
      <el-form-item label="登录页LOGO">
        <el-upload
            class="icon-uploader"
            action="#"
            accept=".png, .jpg,.svg"
            :http-request="(params) => upload(params,'loginLogo')"
            :show-file-list="false" >
          <img v-if="form.config.loginLogo" :src="form.config.loginLogo" class="menu-icon">
          <i v-else class="el-icon-plus avatar-uploader-icon"></i>
        </el-upload>
      </el-form-item>
      <el-form-item label="登陆表单文案">
        <el-input v-model="form.config.loginTitle" placeholder="请输入登陆表单文案" show-word-limit maxlength="10"/>
      </el-form-item>
      <el-form-item label="版权信息">
        <el-input v-model="form.config.copyright" placeholder="请输入版权信息" show-word-limit maxlength="50"/>
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
      },
      // 表单参数
      form: {
        oem:[],
        config:{},
      },
      // 按钮loading
      buttonLoading: false,
      // 页面大小控制
      screenWidth: 0,
      loading: false,
      width: this.pageApi.initTabWidth(),
      // rule规则验证
      rules: {
        host: [
          { required: true, message: '系统域名不能为空', trigger: 'blur' },
          { min: 1, max: 20, message: '系统域名不能超过20个字符', trigger: 'blur' }
        ],
        oemCode: { required: true, message: '所属租户不能为空', trigger: 'change' }
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
    this.$api.cloud.oem.list().then(res => {
      this.form.oem = res.data
    })
    window.onresize = () => {
      return (() => {
        this.width = this.pageApi.initTabWidth()
      })()
    }
  },
  methods: {
    upload(d,field) {
      if (d.file.size / 1024 < 2) {
        this.$message({
          message: '图标文件大小不能超过2MB',
          type: 'error'
        })
        return
      }
      this.loading = true
      this.$api.core.uploadFile(d)
          .then(res => {
            if (res.code === '200') {
              this.$message({
                message: '图标上传成功',
                type: 'success'
              })
              this.form.config[field] = res.url
            } else {
              this.$message({
                message: '图标上传失败',
                type: 'error'
              })
            }
            this.loading = false
          })
    },
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
          this.form.config.oemCode = this.module.oemCode
          var params = {'host':this.module.host,'config':this.form.config}
          this.$api.cloud.hostconfig.add(params).then((res) => {
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
      this.module = {
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
