<template>
  <CloudDialog :width="width" :title="title" id="CloudDialog" :formLoading="loading" top="100px" :view="readonly" :visible="visible" @close="close" @submitForm="submitForm">
    <template v-slot:contentarea>
      <el-form ref="form" id="el-form" :disabled="readonly" :model="module" :rules="rules" label-position="right" label-width="100px">
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
        <el-form-item label="详细信息" prop="oemDesc">
          <el-input v-model="module.oemDesc" placeholder="请输入详细信息" show-word-limit maxlength="20"/>
        </el-form-item>
        <el-form-item label="启用/禁用" prop="oemStatus">
          <el-switch v-model="module.oemStatus" active-value="1" inactive-value="2"></el-switch>
        </el-form-item>
        <el-form-item label="授权时间" prop="time">
          <el-date-picker v-model="module.time" value-format="yyyy-MM-dd" type="daterange" range-separator="至" start-placeholder="开始日期" end-placeholder="结束日期"></el-date-picker>
        </el-form-item>
      </el-form>
    </template>
  </CloudDialog>
</template>
<script>

import CloudDialog from "@/components/My/CloudDialog";

export default {
  name: 'Views',
  components: {CloudDialog},
  props: {},
  data() {
    return {
      title: '',
      visible: false,
      loading: false,
      readonly: false,
      width: this.pageApi.initTabWidth(),
      form: {
        config: {}
      },
      rules: {
        oemName: [
          {required: true, message: '租户名称不能为空', trigger: 'blur'},
        ],
        oemCode: {required: true, message: '租户编码不能为空', trigger: 'blur'},
        oemMobile: {required: true, message: '联系电话不能为空', trigger: 'blur'},
        oemAddr: {required: true, message: '联系地址不能为空', trigger: 'blur'},
        time: {
          required: true,
          validator: (rule, value, callback) => {
            let time = this.module.time;
            if (!time || time.length === 0) {
              callback(new Error('请选择授权时间'));
            } else {
              callback();
            }
          },
          trigger: 'blur'
        }
      },
      module: {
        oemStatus: '1',
        oemName: '',
        oemCode: '',
        oemMobile: '',
        oemAddr: '',
        oemDesc: '',
        startDate: '',
        endDate: '',
        time: []
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
    add() {
      this.readonly = false
      this.title = '新增租户'
      this.visible = true
    },
    edit(param) {
      this.readonly = false
      this.title = '编辑租户'
      this.visible = true
      this.module.oemId = param.oemId
      this.getInfo()
    },
    view(param) {
      // 设置表单只读
      this.readonly = true
      // 设置dialog标题
      this.title = '查看租户'
      // 打开弹窗
      this.visible = true
      // 设置参数
      this.module.oemId = param.oemId
      this.getInfo()
    },
    // 初始化详情
    getInfo() {
      const params = {
        oemId: this.module.oemId
      }
      this.loading = true
      this.$api.cloud.oem.info(params).then(res => {
        this.module = res.data
        if (this.module.startDate != null && this.module.endDate != null) {
          const formTime = []
          formTime[0] = this.module.startDate
          formTime[1] = this.module.endDate
          this.module.time = formTime
        }
        this.loading = false
      }).catch(e=>{
        this.loading = false
      })
    },
    // 提交表单
    submitForm() {
      this.$refs.form.validate((valid) => {
        if (valid) {
          if (this.module.oemId) {
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
      if (this.module.time != null && this.module.time.length == 2) {
        this.module.startDate = this.module.time[0]
        this.module.endDate = this.module.time[1]
      }
      this.$api.cloud.oem.edit(this.module).then((res) => {
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
      if (this.module.time != null && this.module.time.length == 2) {
        this.module.startDate = this.module.time[0]
        this.module.endDate = this.module.time[1]
      }
      this.$api.cloud.oem.add(this.module).then((res) => {
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
    // 关闭窗口并且透传
    close() {
      this.module.oemId = ''
      this.module.time = []
      this.resetForm("form");
      this.visible = false;
      this.$emit('close')
    }
  }
}
</script>

