<template>
  <el-dialog
    :title="title"
    :width="width"
    top="100px"
    v-loading="loading"
    :close-on-click-modal="false"
    :close-on-press-escape="false"
    :visible.sync="editTab"
  >
    <!--  表单部分  -->
    <el-form ref="form" :model="module" :rules="rules" label-position="right" label-width="100px">
      <el-form-item label="菜单名称" prop="name">
        <el-input v-model="module.name" placeholder="请输入菜单名称" show-word-limit maxlength="20"/>
      </el-form-item>
      <el-form-item label="权限编码">
        <el-input v-model="module.authCode" placeholder="请输入权限编码" show-word-limit maxlength="20"/>
      </el-form-item>
      <el-form-item label="功能类型" prop="type">
        <el-select v-model="module.type" value="" placeholder="请选择功能类型">
          <el-option
            v-for="item in form.dict.MODULE_TYPE"
            :key="item.dictKey"
            :label="item.dictValue"
            :value="String(item.dictKey)"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="跳转地址">
        <el-input v-model="module.jumpAction" placeholder="请输入跳转地址" show-word-limit maxlength="300"/>
      </el-form-item>
      <el-form-item label="路由地址">
        <el-input v-model="module.routerName" placeholder="请输入路由地址" show-word-limit maxlength="300"/>
      </el-form-item>
      <el-form-item label="接口地址">
        <el-input v-model="module.serverApi" placeholder="请输入接口地址" show-word-limit maxlength="300"/>
      </el-form-item>
      <el-form-item label="菜单排序" prop="seq">
        <el-input-number v-model="module.seq" :min="0"></el-input-number>
      </el-form-item>
      <el-form-item label="菜单图标">
        <el-upload
          class="icon-uploader"
          action="#"
          accept=".png, .jpg"
          :http-request="uploadIcon"
          :show-file-list="false" >
          <img v-if="module.icon" :src="module.icon" class="menu-icon">
          <i v-else class="el-icon-plus avatar-uploader-icon"></i>
        </el-upload>
      </el-form-item>
      <el-form-item label="显示/隐藏">
        <el-switch v-model="module.hidden" active-value="1" inactive-value="2"></el-switch>
      </el-form-item>
      <el-form-item label="启用/禁用">
        <el-switch v-model="module.enableStatus" active-value="1" inactive-value="2"></el-switch>
      </el-form-item>
      <el-form-item label="配置参数">
        <el-input
          type="textarea"
          v-model="module.configJson"
          placeholder="请输入配置参数"
          show-word-limit
          maxlength="500"></el-input>
      </el-form-item>
    </el-form>
    <!--  按钮部分  -->
    <div slot="footer" class="dialog-footer">
      <el-button type="warning" plain :loading="buttonLoading" @click="editTab = false">
        {{ $t('common.cancel') }}
      </el-button>
      <el-button type="primary" plain :loading="buttonLoading" @click="submitForm">
        {{ $t('common.confirm') }}
      </el-button>
    </div>
  </el-dialog>
</template>
<script>
export default {
  name: 'Edit',
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
        dict: {}
      },
      // 按钮loading
      buttonLoading: false,
      // 页面大小控制
      screenWidth: 0,
      width: this.pageApi.initTabWidth(),
      loading: false,
      // rule规则验证
      rules: {
        name: [
          { required: true, message: '菜单名称不能为空', trigger: 'blur' },
          { min: 1, max: 20, message: '菜单名称不能超过20个字符', trigger: 'blur' }
        ],
        type: { required: true, message: '功能类型不能为空', trigger: 'change' }
      }
    }
  },
  computed: {
    editTab: {
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
    // 加载数据字典
    this.$api.cloud.sys.getDictMap('MODULE_TYPE').then(res => { this.form.dict = res.data })
    window.onresize = () => {
      return (() => {
        this.width = this.pageApi.initTabWidth()
      })()
    }
  },
  methods: {
    // 初始化设置form表单数据
    setModule(param) {
      this.module.moduleId = param.moduleId
      // 加载数据字典
      // 加载详情
      this.init()
    },
    // 初始化详情
    init() {
      const params = {
        moduleId: this.module.moduleId
      }
      this.$api.cloud.module.info(params).then(res => {
        this.module = res.data
      })
    },
    // 关闭窗口并且透传
    close() {
      this.$emit('close', 'editTab')
      this.buttonLoading = false
      this.reset()
    },
    // 图标上传
    uploadIcon(d) {
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
            this.module.icon = res.url
          } else {
            this.$message({
              message: '图标上传失败',
              type: 'error'
            })
          }
          this.loading = false
        })
    },
    // 提交表单
    submitForm() {
      this.$refs.form.validate((valid) => {
        if (valid) {
          this.buttonLoading = true
          this.$api.cloud.module.edit(this.module).then((res) => {
            this.buttonLoading = false
            this.isVisible = false
            this.$message({
              message: '修改成功',
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
      this.module = {}
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
