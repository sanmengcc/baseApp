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
      <el-form-item label="配置ID">
        <el-input v-model="module.configId"/>
      </el-form-item>
      <el-form-item label="系统域名">
        <el-input v-model="module.host"/>
      </el-form-item>
      <el-form-item label="所属租户">
        <el-input v-model="form.config.oemCode"/>
      </el-form-item>
      <el-form-item label="系统名称">
        <el-input v-model="form.config.sysName"/>
      </el-form-item>
      <el-form-item label="网站图标">
        <img v-if="form.config.favicon" :src="form.config.favicon" class="menu-icon">
        <i v-if="form.config.favicon" v-else class="el-icon-plus avatar-uploader-icon"></i>
      </el-form-item>
      <el-form-item label="登录页背景图">
        <img v-if="form.config.backgroundImage" :src="form.config.backgroundImage" class="menu-icon">
        <i v-if="form.config.backgroundImage" v-else class="el-icon-plus avatar-uploader-icon"></i>
      </el-form-item>
      <el-form-item label="登录页特效图">
        <img v-if="form.config.loginLeftImage" :src="form.config.loginLeftImage" class="menu-icon">
        <i v-if="form.config.loginLeftImage" v-else class="el-icon-plus avatar-uploader-icon"></i>
      </el-form-item>
      <el-form-item label="登录页LOGO">
        <img v-if="form.config.loginLogo" :src="form.config.loginLogo" class="menu-icon">
        <i v-if="form.config.loginLogo" v-else class="el-icon-plus avatar-uploader-icon"></i>
      </el-form-item>
      <el-form-item label="登陆表单文案">
        <el-input v-model="form.config.loginTitle"/>
      </el-form-item>
      <el-form-item label="版权信息">
        <el-input v-model="form.config.copyright"/>
      </el-form-item>
      <el-form-item label="创建时间">
        <el-input v-model="module.gmtCreate"/>
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

import {info} from "@/api/cloud/hostconfig";

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
      form: {
        config: {}
      },
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
      this.module = {...param}
      this.init()
    },
    // 初始化详情
    init() {
      const params = {
        configId: this.module.configId
      }
      this.$api.cloud.hostconfig.info(params).then(res => {
        this.module = res.data
        this.form.config = JSON.parse(res.data.configJson)
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
