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
      <el-form-item label="租户名称" prop="oemName">
        <el-input v-model="module.oemName" placeholder="请输入租户名称" show-word-limit maxlength="20"/>
      </el-form-item>
      <el-form-item label="租户编码" prop="oemCode">
        <el-input v-model="module.oemCode" placeholder="请输入租户编码" show-word-limit maxlength="20"/>
      </el-form-item>
      <el-form-item label="联系电话" prop="oemMobile">
        <el-input v-model="module.oemMobile" placeholder="请输入联系电话" show-word-limit maxlength="20"/>
      </el-form-item>
      <el-form-item label="联系地址" prop="oemAddr">
        <el-input v-model="module.oemAddr" placeholder="请输入联系地址" show-word-limit maxlength="20"/>
      </el-form-item>
      <el-form-item label="详细信息">
        <el-input v-model="module.oemDesc" placeholder="请输入详细信息" show-word-limit maxlength="20"/>
      </el-form-item>
      <el-form-item label="启用/禁用">
        <el-switch v-model="module.oemStatus" active-value="1" inactive-value="2"></el-switch>
      </el-form-item>
      <el-form-item label="授权时间">
        <el-date-picker
            v-model="form.time"
            value-format="yyyy-MM-dd"
            type="daterange"
            range-separator="至"
            start-placeholder="开始日期"
            end-placeholder="结束日期">
        </el-date-picker>
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
        time:[]
      },
      // 按钮loading
      buttonLoading: false,
      // 页面大小控制
      screenWidth: 0,
      width: this.pageApi.initTabWidth(),
      loading: false,
      // rule规则验证
      rules: {
        oemName: [
          { required: true, message: '租户名称不能为空', trigger: 'blur' },
          { min: 1, max: 20, message: '租户名称不能超过20个字符', trigger: 'blur' }
        ],
        oemCode: [
          { required: true, message: '租户编码不能为空', trigger: 'blur' },
          { min: 1, max: 20, message: '租户编码不能超过20个字符', trigger: 'blur' }
        ],
        oemMobile: [
          { required: true, message: '联系电话不能为空', trigger: 'blur' },
          { min: 1, max: 20, message: '联系电话不能超过20个字符', trigger: 'blur' }
        ],
        oemAddr: [
          { required: true, message: '联系地址不能为空', trigger: 'blur' },
          { min: 1, max: 20, message: '联系地址不能超过20个字符', trigger: 'blur' }
        ]
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
    window.onresize = () => {
      return (() => {
        this.width = this.pageApi.initTabWidth()
      })()
    }
  },
  methods: {
    // 初始化设置form表单数据
    setModule(param) {
      this.module.oemId = param.oemId
      // 加载详情
      this.init()
    },
    // 初始化详情
    init() {
      const params = {
        oemId: this.module.oemId
      }
      this.form.time = []

      this.$api.cloud.oem.info(params).then(res => {
        this.module = res.data
        const startDate = res.data.startDate;
        const endDate = res.data.endDate;

        if (startDate != null && endDate != null) {
          this.$set(this.form.time, 0, startDate)
          this.$set(this.form.time, 1, endDate)
        }
      })
    },
    // 关闭窗口并且透传
    close() {
      this.$emit('close', 'editTab')
      this.buttonLoading = false
      this.reset()
    },
    // 提交表单
    submitForm() {
      this.$refs.form.validate((valid) => {
        if (valid) {
          this.buttonLoading = true
          if(this.form.time != null && this.form.time.length == 2){
            this.module.startDate = this.form.time[0]
            this.module.endDate = this.form.time[1]
          }
          this.$api.cloud.oem.edit(this.module).then((res) => {
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
      this.module = {
        oemStatus:'1',
        oemName:'',
        oemCode:'',
        oemMobile:'',
        oemAddr: '',
        oemDesc:'',
        startDate:'',
        endDate:''
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
