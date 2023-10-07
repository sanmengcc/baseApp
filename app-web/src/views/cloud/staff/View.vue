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
      <el-form-item label="用户名">
        <el-input v-model="module.account"/>
      </el-form-item>
      <el-form-item label="员工姓名">
        <el-input v-model="module.staffName"/>
      </el-form-item>
      <el-form-item label="手机号码">
        <el-input v-model="module.mobile"/>
      </el-form-item>
      <el-form-item label="电子邮箱">
        <el-input v-model="module.account"/>
      </el-form-item>
      <el-form-item label="入职日期">
        <el-input v-model="module.entryDate"/>
      </el-form-item>
      <el-form-item label="员工类型">
        <el-input v-model="module.adminTypeLabel"/>
      </el-form-item>
      <el-form-item label="在职状态">
        <el-input v-model="module.statusLabel"/>
      </el-form-item>
      <el-form-item label="头像图片">
        <img v-if="module.avatarUrl" :src="module.avatarUrl" class="menu-icon">
      </el-form-item>
      <el-form-item label="备注信息">
        <el-input v-model="module.remark"/>
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
      this.module = {...param}
      this.init()
    },
    // 初始化详情
    init() {
      const params = {
        staffId: this.module.staffId
      }
      this.$api.cloud.staff.info(params).then(res => {
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
