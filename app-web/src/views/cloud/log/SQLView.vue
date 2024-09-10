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
  >
    <template v-slot:contentarea>
      <el-form ref="form" id="el-form" :disabled="readonly" :model="module" :rules="rules" label-position="right"
               label-width="100px">
        <el-form-item label="SQLID" prop="mapper">
          <el-input v-model="module.mapper"/>
        </el-form-item>
        <el-form-item label="执行耗时" prop="useTime">
          <el-input v-model="module.useTime"/>
        </el-form-item>
        <el-form-item label="执行时间" prop="gmtCreate">
          <el-input v-model="module.gmtCreate"/>
        </el-form-item>
        <el-form-item label="SQL详情" prop="sql">
          <el-input type="textarea" :rows="5" v-model="module.sql"></el-input>
        </el-form-item>
      </el-form>
    </template>
  </CloudDialog>
</template>
<script>

import CloudDialog from "@/components/My/CloudDialog.vue";

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
      module: {},
      rules: {}
    }
  },
  computed: {},
  mounted() {
    window.onresize = () => {
      return (() => {
        this.width = this.pageApi.initTabWidth()
      })()
    }
  },
  methods: {
    view(param) {
      // 设置表单只读
      this.readonly = true
      // 设置dialog标题
      this.title = '查看SQL'
      // 打开弹窗
      this.visible = true
      // 设置参数
      this.module.logId = param.logId
      this.getInfo()
    },
    // 初始化详情
    getInfo() {
      const params = {
        logId: this.module.logId
      }
      this.loading = true
      this.$api.cloud.sqllog.info(params).then(res => {
        this.module = res.data
        this.loading = false
      })
    },
    // 关闭窗口并且透传
    close() {
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
