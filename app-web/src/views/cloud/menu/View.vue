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
      <!--  表单部分  -->
      <el-form ref="form" id="el-form" :disabled="readonly" :model="module" :rules="rules" label-position="right"
               label-width="100px">
        <el-row>
          <el-col :span="12">
            <el-form-item prop="name">
            <span slot="label" style="display:inline-block;">
              菜单名称
              <el-tooltip effect="dark" content="菜单名称" placement="bottom">
                <i class='el-icon-question'/>
              </el-tooltip>
             </span>
              <el-input v-model="module.name" placeholder="请输入菜单名称" show-word-limit maxlength="20"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item prop="authCode">
        <span slot="label" style="display:inline-block;">
        权限编码
        <el-tooltip effect="dark" content="用于控制页面按钮权限" placement="bottom">
          <i class='el-icon-question'/>
        </el-tooltip>
        </span>
              <el-input v-model="module.authCode" placeholder="请输入权限编码" show-word-limit maxlength="20"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
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
          </el-col>
          <el-col :span="12">
            <el-form-item label="菜单排序" prop="seq">
              <el-input-number v-model="module.seq" :min="0"></el-input-number>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="12">
            <el-form-item label="显示/隐藏" prop="hidden">
              <el-switch v-model="module.hidden" active-value="1" inactive-value="2"></el-switch>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="启用/禁用" prop="enableStatus">
              <el-switch v-model="module.enableStatus" active-value="1" inactive-value="2"></el-switch>
            </el-form-item>
          </el-col>
        </el-row>

        <el-form-item label="菜单图标" prop="icon">
          <el-upload
            class="icon-uploader"
            action="#"
            accept=".png, .jpg"
            :http-request="uploadIcon"
            :show-file-list="false">
            <img v-if="module.icon" :src="module.icon" class="menu-icon">
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
        </el-form-item>

        <el-form-item prop="jumpAction">
         <span slot="label" style="display:inline-block;">
        跳转地址
        <el-tooltip effect="dark" content="用于Vue菜单路径跳转" placement="bottom">
          <i class='el-icon-question'/>
        </el-tooltip>
        </span>
          <el-input v-model="module.jumpAction" placeholder="请输入跳转地址" show-word-limit maxlength="300"/>
        </el-form-item>
        <el-form-item label="路由地址" prop="routerName">
          <el-input v-model="module.routerName" placeholder="请输入路由地址" show-word-limit maxlength="300"/>
        </el-form-item>
        <el-form-item prop="serverApi">
        <span slot="label" style="display:inline-block;">
        接口地址
        <el-tooltip effect="dark" content="用于控制接口级权限" placement="bottom">
          <i class='el-icon-question'/>
        </el-tooltip>
        </span>
          <el-input v-model="module.serverApi" placeholder="请输入接口地址" show-word-limit maxlength="300"/>
        </el-form-item>

        <el-form-item label="配置参数" prop="configJson">
          <el-input
            type="textarea"
            v-model="module.configJson"
            placeholder="请输入配置参数"
            show-word-limit
            maxlength="500"></el-input>
        </el-form-item>
      </el-form>
    </template>
  </CloudDialog>
</template>
<script>
import CloudDialog from '@/components/My/CloudDialog'

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
      screenWidth: 0,
      width: this.pageApi.initTabWidth(),
      // 表单数据
      module: {
        seq: 0,
        parentId: 0,
        name: '',
        routerName: '',
        code: '',
        type: '',
        authCode: '',
        serverApi: '',
        configJson: '',
        hidden: '1',
        icon: '',
        enableStatus: '1',
        jumpAction: ''
      },
      // 表单参数
      form: {
        dict: {}
      },
      rules: {
        name: [
          {required: true, message: '菜单名称不能为空', trigger: 'blur'},
          {min: 1, max: 20, message: '菜单名称不能超过20个字符', trigger: 'blur'}
        ],
        type: {required: true, message: '功能类型不能为空', trigger: 'change'}
      }
    }
  },
  computed: {},
  mounted() {
    // 初始化数据字典
    this.$api.cloud.sys.getDictMap('MODULE_TYPE').then(res => {
      this.form.dict = res.data
    })
    window.onresize = () => {
      return (() => {
        this.width = this.pageApi.initTabWidth()
      })()
    }
  },
  methods: {
    add(param) {
      this.readonly = false
      this.title = '新增菜单'
      this.visible = true
      this.module.systemId = param.systemId
      if (param.parentId) {
        this.module.parentId = param.parentId
      }
    },
    edit(param) {
      this.readonly = false
      this.title = '编辑菜单'
      this.visible = true
      this.module.moduleId = param.moduleId
      this.getInfo()
    },
    view(param) {
      // 设置表单只读
      this.readonly = true
      // 设置dialog标题
      this.title = '查看菜单'
      // 打开弹窗
      this.visible = true
      // 设置参数
      this.module.moduleId = param.moduleId
      this.getInfo()
    },
    // 初始化详情
    getInfo() {
      const params = {
        moduleId: this.module.moduleId
      }
      this.loading = true
      this.$api.cloud.module.info(params).then(res => {
        this.loading = false
        this.module = res.data
      })
    },
    // 提交表单
    submitForm() {
      this.$refs.form.validate((valid) => {
        if (valid) {
          if (this.module.moduleId) {
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
      this.$api.cloud.module.edit(this.module).then((res) => {
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
      this.$api.cloud.module.add(this.module).then((res) => {
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
    // 清空表单
    reset() {
      this.module.moduleId = ''
      this.resetForm("form");
    },
    // 关闭窗口并且透传
    close() {
      this.reset()
      this.visible = false
      this.$emit('close')
    }
  }
}
</script>
<style lang="scss" scoped>
.user-view {
  .img-wrapper {
    text-align: center;
    margin-top: -1.5rem;
    margin-bottom: 10px;

    img {
      width: 4rem;
      border-radius: 50%;
    }
  }

  .view-item {
    margin: 7px;

    i {
      font-size: .97rem;
    }

    span {
      margin-left: 5px;
    }
  }
}

.menu-icon {
  width: 78px;
  height: 78px;
  display: block;
}
</style>


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

