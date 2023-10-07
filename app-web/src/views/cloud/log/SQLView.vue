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
      <el-form-item label="SQLID">
        <el-input v-model="module.mapper"/>
      </el-form-item>
      <el-form-item label="执行耗时">
        <el-input v-model="module.useTime"/>
      </el-form-item>
      <el-form-item label="执行时间" >
        <el-input v-model="module.gmtCreate"/>
      </el-form-item>
      <el-form-item label="SQL详情" >
        <el-input type="textarea" :rows="5" v-model="module.sql"></el-input>
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
        logId: this.module.logId
      }
      this.$api.cloud.sqllog.info(params).then(res => {
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
