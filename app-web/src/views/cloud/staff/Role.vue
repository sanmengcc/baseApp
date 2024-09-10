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
      <el-form ref="form" id="el-form" :disabled="readonly" :model="module" label-position="right"
               label-width="100px">
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
    </template>
  </CloudDialog>
</template>
<script>
import CloudDialog from "@/components/My/CloudDialog.vue";


export default {
  name: 'Role',
  components:{CloudDialog},
  data() {
    return {
      title: '',
      visible: false,
      loading: false,
      readonly: false,
      screenWidth: 0,
      // 表单数据
      module: {
        roleIdList: [],
        staffId: ''
      },
      // 表单参数
      form: {
        roleList: []
      },
      // 页面大小控制
      width: this.pageApi.initTabWidth(),
    }
  },
  mounted() {
    // 初始化数据字典
    window.onresize = () => {
      return (() => {
        this.width = this.pageApi.initTabWidth()
      })()
    }
  },
  methods: {
    add(param) {
      this.module.staffId = param.staffId
      this.readonly = false
      this.title = '授权角色'
      this.visible = true
      this.getInfo()
    },
    getInfo() {
      this.$api.cloud.role.list().then(res => {
        this.form.roleList = res.data
      })

      // 加载数据字典
      const arr = []
      this.loading = true
      this.$api.cloud.role.getStaff({staffId: this.module.staffId}).then(res => {
        this.loading = false
        res.data.forEach((item) => {
          arr.push(item.roleId);
        });
        this.module.roleIdList = arr
      })
    },
    // 关闭窗口并且透传
    close() {
      this.$emit('close')
      this.reset()
      this.visible = false
    },
    // 提交表单
    submitForm() {
      this.$refs.form.validate((valid) => {
        if (valid) {
          this.loading = true
          this.$api.cloud.role.allotRole(this.module).then((res) => {
            this.loading = false
            this.$message({
              message: '分配成功',
              type: 'success'
            })
            this.$emit('success')
            this.close()
          }).catch((e) => {
            this.loading = false
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
