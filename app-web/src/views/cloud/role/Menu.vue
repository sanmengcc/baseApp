<template>
  <el-dialog
      :title="title"
      :width="width"
      top="100px"
      v-loading="loading"
      :close-on-click-modal="false"
      :close-on-press-escape="false"
      :visible.sync="menuTab"
  >
    <!--  表单部分  -->
    <el-form ref="form" :model="module" label-position="right" label-width="100px">
      <el-input
          placeholder="输入关键字进行过滤"
          v-model="filterText">
      </el-input>

      <el-tree
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
    <!--  按钮部分  -->
    <div slot="footer" class="dialog-footer">
      <el-button type="warning" plain :loading="buttonLoading" @click="menuTab = false">
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
  name: 'Menu',
  watch: {
    filterText(val) {
      this.$refs.tree.filter(val);
    }
  },
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
        data:[],
        moduleIdList: []
      },
      // 按钮loading
      buttonLoading: false,
      // 页面大小控制
      screenWidth: 0,
      width: this.pageApi.initTabWidth(),
      loading: false
    }
  },
  computed: {
    menuTab: {
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
    filterNode(value, data) {
      if (!value) return true;
      return data.name.indexOf(value) !== -1;
    },
    // 初始化设置form表单数据
    setModule(param) {
      this.module.roleId = param.roleId
      // 加载数据字典
      // 加载详情
      this.init()
    },
    // 初始化详情
    init() {
      const params = {
        roleId: this.module.roleId
      }
      this.$api.cloud.module.selectList().then(res => {
        this.form.data = res.data
      })

      this.$api.cloud.role.selectRoleModule(params).then(res => {
        this.form.moduleIdList = res.data
      })
    },
    // 关闭窗口并且透传
    close() {
      this.$emit('close', 'menuTab')
      this.buttonLoading = false
      this.reset()
    },
    // 提交表单
    submitForm() {
      this.$refs.form.validate((valid) => {
        if (valid) {
          this.buttonLoading = true

          const moduleIdList = []
          let checkedNodes = this.$refs.tree.getCheckedNodes();
          checkedNodes.forEach(item => {
            if (item.isLeaf) {
              moduleIdList.push(item.moduleId)
            }
          })

          const params = {'moduleIdList': moduleIdList, 'roleId': this.module.roleId}

          this.$api.cloud.role.authModule(params).then((res) => {
            this.buttonLoading = false
            this.isVisible = false
            this.$message({
              message: '授权成功',
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
      this.$refs.tree.setCheckedKeys([]);
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
