<template>
  <CloudDialog :width="width" :title="title" id="CloudDialog" :formLoading="loading" top="100px" :view="readonly" :visible="visible" @close="close" @submitForm="submitForm">
    <template v-slot:contentarea>
      <el-form ref="form" id="el-form" :disabled="readonly" :model="module" :rules="rules" label-position="right" label-width="100px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="系统域名" prop="host">
              <el-input v-model="module.host" placeholder="请输入系统域名" show-word-limit maxlength="20"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="系统名称" prop="sysName" :rules="dynamicValidate.sysName">
              <el-input v-model="form.config.sysName" placeholder="请输入系统域名" show-word-limit maxlength="20"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="所属租户" prop="oemCode">
              <el-select v-model="module.oemCode" placeholder="请选择所属租户">
                <el-option v-for="item in form.oem" :key="item.oemCode" :label="item.oemName" :value="String(item.oemCode)"/>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="版权信息">
              <el-input v-model="form.config.copyright" placeholder="请输入版权信息" show-word-limit maxlength="50"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="网站图标">
              <el-upload class="icon-uploader" action="#" accept=".png, .jpg,.svg" :http-request="(params) => upload(params,'favicon')" :show-file-list="false">
                <img v-if="form.config.favicon" :src="form.config.favicon" class="menu-icon">
                <i v-else class="el-icon-plus avatar-uploader-icon"></i>
              </el-upload>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="登录页背景图">
              <el-upload class="icon-uploader" action="#" accept=".png, .jpg,.svg" :http-request="(params) => upload(params,'backgroundImage')" :show-file-list="false">
                <img v-if="form.config.backgroundImage" :src="form.config.backgroundImage" class="menu-icon">
                <i v-else class="el-icon-plus avatar-uploader-icon"></i>
              </el-upload>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="登录页特效图">
              <el-upload class="icon-uploader" action="#" accept=".png, .jpg,.svg" :http-request="(params) => upload(params,'loginLeftImage')" :show-file-list="false">
                <img v-if="form.config.loginLeftImage" :src="form.config.loginLeftImage" class="menu-icon">
                <i v-else class="el-icon-plus avatar-uploader-icon"></i>
              </el-upload>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="登录页LOGO">
              <el-upload class="icon-uploader" action="#" accept=".png, .jpg,.svg" :http-request="(params) => upload(params,'loginLogo')" :show-file-list="false">
                <img v-if="form.config.loginLogo" :src="form.config.loginLogo" class="menu-icon">
                <i v-else class="el-icon-plus avatar-uploader-icon"></i>
              </el-upload>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
    </template>
  </CloudDialog>
</template>
<script>

import CloudDialog from "@/components/My/CloudDialog.vue";

export default {
  name: 'View',
  components: {CloudDialog},
  data() {
    return {
      title: '',
      visible: false,
      loading: false,
      readonly: false,
      // 表单数据
      module: {
        host: '',
        oemCode: '',
      },
      dynamicValidate: {
        sysName: [{
          required: true,
          validator: (rule, value, callback) => {
            let sysName = this.form.config.sysName
            if (!sysName) {
              callback(new Error('请输入系统名称'));
            } else {
              callback();
            }
          },
          trigger: 'blur'
        }],
      },
      // 表单参数
      form: {
        oem: [],
        config: {
          sysName: '',
          loginTitle: '',
          copyright: '',
          loginLogo: '',
          backgroundImage: '',
          loginLeftImage: '',
          favicon: '',
        },
      },
      width: this.pageApi.initTabWidth(),
      // rule规则验证
      rules: {
        host: [
          {required: true, message: '系统域名不能为空', trigger: 'blur'},
          {min: 1, max: 20, message: '系统域名不能超过20个字符', trigger: 'blur'}
        ],
        oemCode: {required: true, message: '所属租户不能为空', trigger: 'blur'},
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
    upload(d, field) {
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
    add() {
      this.readonly = false
      this.title = '新增系统'
      this.visible = true
    },
    edit(param) {
      this.readonly = false
      this.title = '编辑系统'
      this.visible = true
      this.module.configId = param.configId
      this.getInfo()
    },
    view(param) {
      // 设置表单只读
      this.readonly = true
      // 设置dialog标题
      this.title = '查看系统'
      // 打开弹窗
      this.visible = true
      // 设置参数
      this.module.configId = param.configId
      this.getInfo()
    },
    // 关闭窗口并且透传
    close() {
      this.reset()
      this.visible = false
      this.$emit('close')
    },
    // 初始化详情
    getInfo() {
      const params = {
        configId: this.module.configId
      }
      this.loading = true
      this.$api.cloud.hostconfig.info(params).then(res => {
        this.module.host = res.data.host
        this.module.configId = res.data.configId
        this.form.config = JSON.parse(res.data.configJson)
        this.module.oemCode = this.form.config.oemCode
        this.loading = false
      })
    },
    // 提交表单
    submitForm() {
      this.$refs.form.validate((valid) => {
        if (valid) {
          if (this.module.configId) {
            this.editData()
          } else {
            this.addData()
          }
        } else {
          return false
        }
      })
    },
    editData() {
      this.loading = true
      this.form.config.oemCode = this.module.oemCode
      var params = {'configId': this.module.configId, 'host': this.module.host, 'config': this.form.config}
      this.$api.cloud.hostconfig.edit(params).then((res) => {
        this.$message({
          message: '操作成功',
          type: 'success'
        })
        this.loading = false
        this.close()
      }).catch((e) => {
        this.loading = false
      })
    },
    addData() {
      this.loading = true
      this.form.config.oemCode = this.module.oemCode
      var params = {'host': this.module.host, 'config': this.form.config}
      this.$api.cloud.hostconfig.add(params).then((res) => {
        this.loading = false
        this.$message({
          message: '操作成功',
          type: 'success'
        })
        this.close()
      }).catch((e) => {
        this.loading = false
      })
    },
    // 清空表单
    reset() {
      // 先清除校验，再清除表单，不然有奇怪的bug
      this.resetForm("form");
      this.module.configId = ''
      this.form.config = {
        sysName: '',
        loginTitle: '',
        copyright: '',
        loginLogo: '',
        backgroundImage: '',
        loginLeftImage: '',
        favicon: '',
      };

      console.log(this.module)
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
