<template>
  <CloudDialog
    :width="width"
    :title="title"
    id="CloudDialog"
    :formLoading="loading"
    top="100px"
    :view="readonly"
    :close-on-click-modal="true"
    :close-on-press-escape="false"
    style="height: 90vh;overflow: auto;margin: 5vh auto"
    :visible="visible"
    @close="close"
    @submitForm="submitForm"
  >
    <!--  表单部分  -->
    <template v-slot:contentarea>
      <el-form ref="form" id="el-form" :disabled="readonly" :model="module" :rules="rules" label-position="right"
               label-width="100px">
        <el-form-item label="用户名" prop="account">
          <el-input v-model="module.account" placeholder="请输入用户名" show-word-limit maxlength="20"/>
        </el-form-item>
        <el-form-item label="员工姓名" prop="staffName">
          <el-input v-model="module.staffName" placeholder="请输入员工姓名" show-word-limit maxlength="20"/>
        </el-form-item>
        <el-form-item label="手机号码" prop="mobile">
          <el-input v-model="module.mobile" placeholder="请输入手机号码" show-word-limit maxlength="11"/>
        </el-form-item>
        <el-form-item label="电子邮箱" prop="email">
          <el-input v-model="module.email" placeholder="请输入电子邮箱" show-word-limit maxlength="30"/>
        </el-form-item>
        <el-form-item label="入职日期" prop="entryDate">
          <el-date-picker
            v-model="module.entryDate"
            value-format="yyyy-MM-dd"
            type="date"
            placeholder="请选择入职日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="员工类型" prop="adminType">
          <el-select v-model="module.adminType" value="" placeholder="请选择员工类型">
            <el-option
              v-for="item in form.dict.ADMIN_TYPE"
              :key="item.dictKey"
              :label="item.dictValue"
              :value="String(item.dictKey)"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="在职状态" prop="status">
          <el-select v-model="module.status" value="" placeholder="请选择在职状态">
            <el-option
              v-for="item in form.dict.WORK_STATUS"
              :key="item.dictKey"
              :label="item.dictValue"
              :value="String(item.dictKey)"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="头像图片" prop="avatarUrl">
          <el-upload
            class="icon-uploader"
            action="#"
            accept=".png, .jpg"
            :http-request="uploadIcon"
            :show-file-list="false" >
            <img v-if="module.avatarUrl" :src="module.avatarUrl" class="menu-icon">
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
        </el-form-item>
        <el-form-item label="备注信息" prop="remark">
          <el-input v-model="module.remark" placeholder="请输入备注信息" show-word-limit maxlength="50"/>
        </el-form-item>
    </el-form>
    </template>
  </CloudDialog>
</template>
<script>

import CloudDialog from "@/components/My/CloudDialog.vue";

export default {
  name: 'Views',
  components: {CloudDialog},
  data() {
    return {
      title: '',
      visible: false,
      loading: false,
      readonly: false,
      screenWidth: 0,
      width: this.pageApi.initTabWidth(),
      module: {},
      // 表单参数
      form: {
        dict: {}
      },
      rules: {
        account: [
          { required: true, message: '用户名不能为空', trigger: 'blur' },
          { min: 1, max: 20, message: '用户名不能超过20个字符', trigger: 'blur' }
        ],
        staffName: [
          { required: true, message: '员工姓名不能为空', trigger: 'blur' },
          { min: 1, max: 20, message: '员工姓名不能超过20个字符', trigger: 'blur' }
        ],
        mobile: [
          { required: true, message: '手机号码不能为空', trigger: 'blur' },
          { min: 1, max: 11, message: '手机号码不能超过11个字符', trigger: 'blur' },
          { pattern: /^1[3456789]\d{9}$/, message: '请输入正确的手机号码', trigger: ['blur', 'change'] }
        ],
        email: [
          { required: true, message: '电子邮箱不能为空', trigger: 'blur' },
          { min: 1, max: 30, message: '电子邮箱不能超过30个字符', trigger: 'blur' },
          { pattern: /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/, message: '请输入正确的电子邮箱', trigger: ['blur', 'change'] }
        ],
        status: { required: true, message: '在职状态不能为空', trigger: 'change' },
        adminType: { required: true, message: '员工类型不能为空', trigger: 'change' },
        entryDate: { required: true, message: '入职日期不能为空', trigger: 'change' },
      }
    }
  },
  mounted() {
    window.onresize = () => {
      return (() => {
        this.width = this.pageApi.initTabWidth()
      })()
    }
    this.getDict()
  },
  methods: {
    add() {
      this.readonly = false
      this.title = '新增员工'
      this.visible = true
    },
    edit(param) {
      this.readonly = false
      this.title = '编辑员工'
      this.visible = true
      this.module.staffId = param.staffId
      this.getInfo()
    },
    view(param) {
      // 设置表单只读
      this.readonly = true
      // 设置dialog标题
      this.title = '查看员工'
      // 打开弹窗
      this.visible = true
      // 设置参数
      this.module.staffId = param.staffId
      this.getInfo()
    },
    getDict() {
      // 初始化数据字典
      this.$api.cloud.sys.getDictMap('WORK_STATUS,ADMIN_TYPE').then(res => {
        this.form.dict = res.data
      })
    },
    // 初始化详情
    getInfo() {
      const params = {
        staffId: this.module.staffId
      }
      this.loading = true
      this.$api.cloud.staff.info(params).then(res => {
        this.module = res.data
        this.loading = false
      })
    },
    // 提交表单
    submitForm() {
      this.$refs.form.validate((valid) => {
        if (valid) {
          if (this.module.staffId) {
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
      this.$api.cloud.staff.edit(this.module).then((res) => {
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
      this.$api.cloud.staff.add(this.module).then((res) => {
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
            this.module.avatarUrl = res.url
          } else {
            this.$message({
              message: '图标上传失败',
              type: 'error'
            })
          }
          this.loading = false
        })
    },
    // 关闭窗口并且透传
    close() {
      this.module.staffId = ''
      this.resetForm("form");
      this.visible = false;
      this.$emit('close')
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
