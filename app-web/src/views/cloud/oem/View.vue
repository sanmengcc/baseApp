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
      <el-form-item label="租户名称">
        <el-input v-model="module.oemName"/>
      </el-form-item>
      <el-form-item label="租户编码">
        <el-input v-model="module.oemCode"/>
      </el-form-item>
      <el-form-item label="联系电话">
        <el-input v-model="module.oemMobile"/>
      </el-form-item>
      <el-form-item label="联系地址">
        <el-input v-model="module.oemAddr"/>
      </el-form-item>
      <el-form-item label="详细信息">
        <el-input v-model="module.oemDesc"/>
      </el-form-item>
      <el-form-item label="详细信息">
        <el-input v-model="module.oemDesc"/>
      </el-form-item>
      <el-form-item label="启用/禁用">
        <el-input v-model="module.oemStatusLabel"/>
      </el-form-item>
      <el-form-item label="授权时间">
        <el-input v-model="module.time"/>
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
        oemId: this.module.oemId
      }

      this.$api.cloud.oem.info(params).then(res => {
        this.module = res.data
        if (this.module.startDate != null && this.module.endDate != null) {
          this.module.time = this.module.startDate+'~'+this.module.endDate
        }
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
