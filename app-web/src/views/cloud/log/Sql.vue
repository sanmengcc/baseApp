<template>
  <div class="app-container">
    <!-- 头部搜索栏   -->
    <div class="filter-container" ref="header" style="width: 100%;">
      <el-form ref="form" :inline="true" label-width="80px">
        <el-input v-model="queryParams.keyword"  placeholder="请输入关键字" class="filter-item search-item" />
        <el-form-item label="耗时最小值" label-width="120px">
          <el-input-number class="filter-item search-item" v-model="queryParams.startTime" :max="99999999" label="耗时最小值"/>
        </el-form-item>
        <el-button class="filter-item" type="primary" plain @click="search">查询</el-button>
        <el-button class="filter-item" type="success" plain @click="reset">重置</el-button>
        <el-button v-has-permission="['SQL_LOG:DEL']" class="filter-item" type="danger" plain @click="deleteTime('week')">删除近一周</el-button>
        <el-button v-has-permission="['SQL_LOG:DEL']" class="filter-item" type="danger" plain @click="deleteTime('month')">删除近一月</el-button>
        <el-button v-has-permission="['SQL_LOG:DEL']" class="filter-item" type="danger" plain @click="deleteTime('year')">删除近一年</el-button>
      </el-form>
    </div>
    <!-- 分页组件 -->
    <CloudTb ref="cloudTb" :page="pageData" @pagination="pagination">
      <el-table-column prop="mapper" min-width="300px" label="SQLID" />
      <el-table-column  prop="useTime" label="执行耗时" sortable />
      <el-table-column prop="gmtCreate" label="执行时间"/>
      <el-table-column label="操作" align="center" min-width="150px" fixed="right" class-name="small-padding fixed-width">
        <template v-slot="scope">
          <i class="el-icon-view table-operation" @click="viewTab(scope.row)" v-has-permission="['SQL_LOG:VIEW']">查看</i>
          <CloudDropdown :options="operateOptions" :scope="scope"></CloudDropdown>
        </template>
      </el-table-column>
    </CloudTb>
    <!-- 详情页   -->
    <Views ref="view" @close="close"/>
  </div>
</template>

<script>
// 分页组件
import CloudTb from '@/components/My/CloudTable'
// 详情页面
import Views from './SQLView'
import CloudDropdown from "@/components/My/CloudDropdown.vue";
export default {
  name: 'SQLLogIndex',
  // 定义组件
  components: {CloudDropdown, Views, CloudTb},
  data() {
    return {
      dict: {
        COMMON_FLAG: []
      },
      // 分页参数
      pageData: {
        position: 'left',
        total: 20,
        dataList: [],
        currentPage: 1,
        maxPage: 1,
        pageSize: 20
      },
      // loading参数
      loading: false,
      // 查询参数
      queryParams: {
        keyword: '',
        startTime: ''
      },
    }
  },
  computed: {},
  mounted() {
    // 初始化执行分页查询
    this.$api.cloud.sys.getDict({dictType: 'COMMON_FLAG'}).then(res => {
      this.dict.COMMON_FLAG = res.data
    })
    this.fetch(this.queryParams)
  },
  methods: {
    deleteTime: function (k) {
      this.$confirm(this.$t('tips.confirmDelete'), this.$t('common.tips'), {
        confirmButtonText: this.$t('common.confirm'),
        cancelButtonText: this.$t('common.cancel'),
        type: 'warning'
      }).then(() => {
        this.loading = true
        const params = {'key': k}
        this.$api.cloud.sqllog.del(params).then(() => {
          this.$message({
            message: this.$t('tips.deleteSuccess'),
            type: 'success'
          })
          this.search()
        })
      }).catch(() => {
        this.search()
      })
    },
    // 详情操作tab
    viewTab(row) {
      const param = { logId: row.logId }
      this.$refs.view.view(param)
    },
    // 窗口关闭回调
    close() {
      this.fetch()
    },
    // 分页查询
    fetch(params = {}) {
      params.currentPage = this.pageData.currentPage
      params.pageSize = this.pageData.pageSize
      this.loading = true
      this.$api.cloud.sqllog.page(params).then((r) => {
        const data = r.data
        this.pageData = data
        this.loading = false
      })
    },
    // 分页参数变化
    pagination(e) {
      this.pageData.currentPage = e.currentPage
      this.pageData.pageSize = e.pageSize
      this.search()
    },
    // 搜索
    search() {
      this.fetch({
        ...this.queryParams
      })
    },
    // 重置
    reset() {
      this.queryParams = {
        keyword: '',
        startTime: ''
      }
      this.extra.time = []
      this.search()
    },
  }
}
</script>
