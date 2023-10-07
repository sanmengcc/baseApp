<template>
  <el-dialog
    :title="$t('common.view')"
    :width="width"
    top="100px"
    :visible.sync="viewTab"
    class="user-view"
  >
    <!--  表单部分  -->
    <el-form ref="form" :model="module" label-position="right" label-width="100px" :disabled="true">
      <el-form-item label="菜单名称">
        <el-input v-model="module.name"/>
      </el-form-item>
      <el-form-item label="权限编码">
        <el-input v-model="module.authCode"/>
      </el-form-item>
      <el-form-item label="功能类型" >
        <el-input v-model="module.typeLabel"/>
      </el-form-item>
      <el-form-item label="跳转地址">
        <el-input v-model="module.jumpAction"/>
      </el-form-item>
      <el-form-item label="路由地址">
        <el-input v-model="module.routerName"/>
      </el-form-item>
      <el-form-item label="接口地址">
        <el-input v-model="module.serverApi"/>
      </el-form-item>
      <el-form-item label="菜单排序">
        <el-input-number v-model="module.seq" :min="0"></el-input-number>
      </el-form-item>
      <el-form-item label="菜单图标">
        <img v-if="module.icon" :src="module.icon" class="menu-icon">
        <i v-else class="el-icon-plus avatar-uploader-icon"></i>
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
      <el-button type="warning" @click="viewTab = false">
        {{ $t('common.cancel') }}
      </el-button>
    </div>
  </el-dialog>
</template>
<script>

export default {
  name: 'Views',
  props: {
    // 窗口控制
    visible: {
      type: Boolean,
      default: false
    }
  },
  data() {
    return {
      screenWidth: 0,
      width: this.pageApi.initTabWidth(),
      module: {}
    }
  },
  computed: {
    viewTab: {
      get() {
        return this.visible
      },
      set() {
        this.close()
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
      this.module = { ...param }
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
      this.$emit('close', 'viewTab')
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
