<template>
  <div class="app-container">
    <!-- 头部搜索栏   -->
    <div class="filter-container" ref="header" style="min-width: 1000px; width: 100%;">
      <el-form ref="form" :inline="true" label-width="80px">
        <el-input
            v-model="queryParams.keyword"
            placeholder="请输入关键字"
            class="filter-item search-item"
        />
        <el-button class="filter-item" type="primary" plain @click="search">
          {{ $t('table.search') }}
        </el-button>
        <el-button class="filter-item" type="success" plain @click="reset">
          {{ $t('table.reset') }}
        </el-button>
      </el-form>
    </div>
    <!-- 分页组件   -->
    <CloudTb
        ref="cloudTb"
        :page="pageData"
        :pageOptions="pageOptions"
        @pagination="pagination"
    >
      <el-table-column
          prop="fileName"
          label="文件名称"
      />
      <el-table-column
          prop="bucketName"
          label="存储桶"
          width="100"
      />
      <el-table-column
          prop="protocol"
          width="100"
          label="存储协议"
      />
      <el-table-column
          prop="fileSize"
          label="文件大小"
          width="100"
      />
      <el-table-column
          prop="fileMd5"
          label="文件MD5"
      />
      <el-table-column
          prop="fileUrl"
          label="文件URL"
      />
      <el-table-column
          prop="gmtCreate"
          label="上传时间"
          sortable
      />
    </CloudTb>
  </div>
</template>

<script>
// 分页组件
import CloudTb from '@/components/My/CloudTable'

export default {
  name: 'OssIndex',
  // 定义组件
  components: {CloudTb},
  data() {
    return {
      dict:{
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
      pageOptions: {
      },
      // 子页面的显示控制参数
      dialog: {
        isVisible: false,
        title: ''
      },
      // loading参数
      loading: false,
      // 查询参数
      queryParams: {
        keyword: ''
      },
    }
  },
  computed: {},
  mounted() {
    // 初始化执行分页查询
    this.fetch(this.queryParams)
  },
  methods: {
    // 窗口关闭回调
    close(tab) {
      console.log('close', tab)
      this.dialog[tab] = false
      this.refreshRow()
    },
    // 分页查询
    fetch(params = {}) {
      params.currentPage = this.pageData.currentPage
      params.pageSize = this.pageData.pageSize

      this.loading = true
      this.$api.cloud.oss.page(params).then((r) => {
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
        keyword:''
      }
      this.extra.time = []
      this.search()
    }
  }
}
</script>
<style lang="scss" scoped>
.filter-container {
  .el-table--scrollable-x .el-table__body-wrapper {
    overflow-x: auto;
  }
}

.el-dropdown-link {
  cursor: pointer;
  color: #409EFF;
}

.el-icon-arrow-down {
  font-size: 12px;
}

.table-operation {
  font-size: 14px;
  color: #87d068;
}
</style>
