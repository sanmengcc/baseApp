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
    <template v-slot:contentarea>
      <el-form ref="form" id="el-form" v-loading="loading" :disabled="readonly" :model="module" label-position="right"
               label-width="100px">
        <el-input
          style="top:20px"
          placeholder="输入关键字进行过滤"
          v-model="filterText">
        </el-input>
        <el-tree
          style="top:20px"
          class="filter-tree"
          :data="form.data"
          :props="defaultProps"
          show-checkbox
          node-key="moduleId"
          default-expand-all
          :default-checked-keys="form.moduleIdList"
          :filter-node-method="filterNode"
          ref="tree">
        </el-tree>
      </el-form>
    </template>
  </CloudDialog>
</template>
<script>
import CloudDialog from "@/components/My/CloudDialog"

export default {
  name: 'Menu',
  components: {CloudDialog},
  watch: {
    filterText(val) {
      this.$refs.tree.filter(val);
    }
  },
  data() {
    return {
      title: '',
      visible: false,
      loading: false,
      readonly: false,
      screenWidth: 0,
      filterText: '',
      defaultProps: {
        children: 'children',
        label: 'name',
        isLeaf: "isLeaf"
      },
      // 表单数据
      module: {},
      // 表单参数
      form: {
        data: [],
        moduleIdList: []
      },
      width: this.pageApi.initTabWidth(),
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
    filterNode(value, data) {
      if (!value) return true;
      return data.name.indexOf(value) !== -1;
    },
    // 初始化设置form表单数据
    edit(param) {
      this.visible = true
      this.module.roleId = param.roleId
      // 加载详情
      this.getInfo()
    },
    // 初始化详情
    getInfo() {
      const params = {
        roleId: this.module.roleId
      }
      this.loading = true
      const selectList = this.$api.cloud.module.selectList();
      const selectRoleModule = this.$api.cloud.role.selectRoleModule(params);
      Promise.all([selectList, selectRoleModule])
        .then(results => {
          if (results.length != 2) {
            this.$message({
              message: '数据加载失败',
              type: 'error'
            })
            return
          }
          // 对两个接口返回的数据进行操作
          this.form.data = results[0].data;
          this.form.moduleIdList = results[1].data;
          this.loading = false;
        })
        .catch(error => {
          this.loading = false;
        });
    },
    // 关闭窗口并且透传
    close() {
      this.$emit('close')
      this.visible = false
      this.reset()
    },
    // 提交表单
    submitForm() {
      this.$refs.form.validate((valid) => {
        if (valid) {
          this.loading = true

          const moduleIdList = []
          let checkedNodes = this.$refs.tree.getCheckedNodes();
          checkedNodes.forEach(item => {
            if (item.isLeaf) {
              moduleIdList.push(item.moduleId)
            }
          })

          const params = {'moduleIdList': moduleIdList, 'roleId': this.module.roleId}

          this.$api.cloud.role.authModule(params).then((res) => {
            this.loading = false
            this.$message({
              message: '授权成功',
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
      this.$refs.tree.setCheckedKeys([]);
      this.module = {}
    }
  }
}
</script>
