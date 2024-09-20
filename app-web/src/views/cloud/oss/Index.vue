<template>
  <div class="app-container">
    <!-- 头部搜索栏   -->
    <div class="filter-container" ref="header" style="width: 100%;">
      <el-form ref="form" :inline="true" label-width="80px">
        <el-input v-model="queryParams.keyword" placeholder="请输入关键字" class="filter-item search-item"/>
        <el-button class="filter-item" type="primary" plain @click="search">查询</el-button>
        <el-button class="filter-item" type="success" plain @click="reset">重置</el-button>
      </el-form>
    </div>
    <!-- 分页组件   -->
    <CloudTb ref="cloudTb" :page="pageData" @pagination="pagination">
      <el-table-column prop="fileName" min-width="200px" label="文件名称"/>
      <el-table-column prop="bucketName" label="存储桶" width="100"/>
      <el-table-column prop="protocol" width="100" label="存储协议"/>
      <el-table-column prop="fileSize" label="文件大小" width="100"/>
      <el-table-column prop="fileMd5" label="文件MD5" min-width="200px"/>
      <el-table-column prop="gmtCreate" label="上传时间" min-width="150px" sortable/>
      <el-table-column prop="fileUrl" width="100px" align="left" label="文件URL">
        <template v-slot="{row}">
          <a style="color: #87d068;" type="primary" plain @click="copy(row.fileUrl)">复制地址</a>
        </template>
      </el-table-column>
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
    copy(fileurl) {
      let input = document.createElement("input");
      document.body.appendChild(input);
      input.value = fileurl;
      input.focus();
      input.select();
      try {
        let result = document.execCommand("copy");
        document.body.removeChild(input);
        if (!result) {
          console.error("复制失败");
        } else {
          this.$message.success("复制成功");
        }
      } catch (e) {
        document.body.removeChild(input);
        alert("当前浏览器不支持复制功能，请检查更新或更换其他浏览器操作");
      }
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
