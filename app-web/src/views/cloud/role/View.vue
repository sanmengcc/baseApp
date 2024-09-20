<template>
  <CloudDialog :width="width" :title="title" id="CloudDialog" :formLoading="loading" top="100px" :view="readonly" :visible="visible" @close="close" @submitForm="submitForm">
    <template v-slot:contentarea>
      <el-form ref="form" id="el-form" :disabled="readonly" :model="module" :rules="rules" label-position="right" label-width="100px">
      <el-form-item label="角色名称" prop="roleName">
        <el-input v-model="module.roleName" placeholder="请输入角色名称" show-word-limit maxlength="20"/>
      </el-form-item>
      <el-form-item label="角色编码" prop="roleCode">
        <el-input v-model="module.roleCode" placeholder="请输入角色编码" show-word-limit maxlength="20"/>
      </el-form-item>
      <el-form-item label="角色类型" prop="roleType">
        <el-select v-model="module.roleType" value="" placeholder="请选择角色类型">
          <el-option v-for="item in form.dict.ROLE_TYPE" :key="item.dictKey" :label="item.dictValue" :value="String(item.dictKey)"/>
        </el-select>
      </el-form-item>
      </el-form>
    </template>
  </CloudDialog>
</template>
<script>
import CloudDialog from "@/components/My/CloudDialog"
export default {
  name: 'Views',
  components:{CloudDialog},
  data() {
    return {
      title: '',
      visible: false,
      loading: false,
      readonly: false,
      width: this.pageApi.initTabWidth(),
      form: {
        dict: {}
      },
      module: {
        roleName: '',
        roleCode: '',
        roleType: ''
      },
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
  mounted() {
    this.$api.cloud.sys.getDictMap('ROLE_TYPE').then(res => { this.form.dict = res.data })
    window.onresize = () => {
      return (() => {
        this.width = this.pageApi.initTabWidth()
      })()
    }
  },
  methods: {
    add() {
      this.readonly = false
      this.title = '新增角色'
      this.visible = true
    },
    edit(param) {
      this.readonly = false
      this.title = '编辑角色'
      this.visible = true
      this.module.roleId = param.roleId
      this.getInfo()
    },
    view(param) {
      // 设置表单只读
      this.readonly = true
      // 设置dialog标题
      this.title = '查看角色'
      // 打开弹窗
      this.visible = true
      // 设置参数
      this.module.roleId = param.roleId
      this.getInfo()
    },
    // 初始化详情
    getInfo() {
      const params = {
        roleId: this.module.roleId
      }
      this.loading = true
      this.$api.cloud.role.info(params).then(res => {
        this.module = res.data
        this.loading = false
      }).catch(e=>{
        this.loading = false
      })
    },
    // 提交表单
    submitForm() {
      this.$refs.form.validate((valid) => {
        if (valid) {
          if (this.module.roleId) {
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
      this.$api.cloud.role.edit(this.module).then((res) => {
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
      this.$api.cloud.role.add(this.module).then((res) => {
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
      this.module.roleId = ''
      this.resetForm("form");
      this.visible = false;
      this.$emit('close')
    }
  }
}
</script>
