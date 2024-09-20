<template>
  <div class="app-container">
    <!-- 头部搜索栏   -->
    <div class="filter-container" ref="header" style="width: 100%;">
      <el-form ref="form" :inline="true" label-width="80px">
        <el-input v-model="queryParams.host" placeholder="请输入域名"class="filter-item search-item"/>
        <el-button class="filter-item" type="primary" plain @click="search">查询</el-button>
        <el-button class="filter-item" type="success" plain @click="reset">重置</el-button>
        <el-button v-has-permission="['HOST_CONFIG:ADD']" class="filter-item" type="success" plain @click="addTab">新增</el-button>
      </el-form>
    </div>
    <!-- 分页组件   -->
    <CloudTb ref="cloudTb" :page="pageData" v-loading="loading"@pagination="pagination">
      <el-table-column prop="configId" label="配置ID"/>
      <el-table-column prop="sysName" label="系统名称"/>
      <el-table-column prop="oemCode" label="所属租户"/>
      <el-table-column prop="host" label="配置域名"/>
      <el-table-column prop="gmtCreate" label="创建时间" min-width="150px" sortable/>
      <el-table-column label="操作" align="center" min-width="150px" fixed="right" class-name="small-padding fixed-width">
        <template v-slot="scope">
          <i class="el-icon-view table-operation" @click="viewTab(scope.row)" v-has-permission="['HOST_CONFIG:VIEW']">查看</i>
          <CloudDropdown :options="operateOptions" :scope="scope"></CloudDropdown>
        </template>
      </el-table-column>
    </CloudTb>
    <Views ref="view" @close="close"/>
  </div>
</template>
<script>
// 分页组件
import CloudTb from '@/components/My/CloudTable'
import CloudDropdown from '@/components/My/CloudDropdown'
// 详情页面
import Views from './View'

export default {
  name: 'HostIndex',
  // 定义组件
  components: {Views,CloudDropdown, CloudTb},
  data() {
    return {
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
      queryParams: {},
      // 分页操作栏参数
      operateOptions: [
        {
          value: 'editTab',
          label: 'table.edit',
          permission: 'HOST_CONFIG:EDIT',
          param: ''
        }, {
          value: 'delete',
          label: 'table.delete',
          permission: 'HOST_CONFIG:DEL',
          param: ''
        }]
    }
  },
  computed: {},
  mounted() {
    // 初始化执行分页查询
    this.fetch(this.queryParams)
  },
  methods: {
    // 详情操作tab
    viewTab(row) {
      const param = {configId: row.configId}
      this.$refs.view.view(param)
    },
    // 编辑操作tab
    editTab(row) {
      const param = {configId: row.configId}
      this.$refs.view.edit(param)
    },
    // tab操作
    addTab() {
      this.$refs.view.add()
    },
    // 窗口关闭回调
    close() {
      this.fetch()
    },
    // 删除数据
    delete(row) {
      this.$confirm('选中数据将被永久删除, 是否继续？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.loading = true
        this.$api.cloud.hostconfig.del({configId: row.configId}).then(() => {
          this.$message({
            message: '删除成功!',
            type: 'success'
          })
          this.search()
        })
      }).catch(() => {
        this.search()
      })
    },
    // 分页查询
    fetch(params = {}) {
      params.currentPage = this.pageData.currentPage
      params.pageSize = this.pageData.pageSize
      this.loading = true
      this.$api.cloud.hostconfig.page(params).then((r) => {
        const data = r.data
        this.pageData = data
        this.loading = false
      }).catch(e => {
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
      this.queryParams = {}
      this.search()
    },
  }
}
</script>
