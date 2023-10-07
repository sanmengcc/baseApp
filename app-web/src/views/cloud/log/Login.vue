<template>
  <div class="app-container">
    <!-- 头部搜索栏   -->
    <div class="filter-container" ref="header" style="min-width: 1000px; width: 100%;">
      <el-form ref="form" :inline="true" label-width="80px">
        <el-input
            v-model="queryParams.account"
            placeholder="请输入登录账号"
            class="filter-item search-item"
        />
        <el-input
            v-model="queryParams.loginCity"
            placeholder="请输入登录城市"
            class="filter-item search-item"
        />
        <el-select class="filter-item search-item"
                   v-model="queryParams.loginStatus"
                   placeholder="登陆状态">
          <el-option
              v-for="item in dict.loginStatus"
              :key="item.dictKey"
              :label="item.dictValue"
              :value="item.dictKey">
          </el-option>
        </el-select>
        <el-date-picker
            class="filter-item search-item"
            v-model="extra.time"
            type="datetimerange"
            value-format="yyyy-MM-dd HH:mm:ss"
            :picker-options="extra.pickerOptions"
            range-separator="至"
            start-placeholder="开始时间"
            end-placeholder="结束时间"
            align="right">
        </el-date-picker>
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
          prop="loginIp"
          label="登陆IP"
      >
      </el-table-column>
      <el-table-column
          prop="loginCity"
          label="登陆城市"
      >
      </el-table-column>
      <el-table-column
          prop="loginTime"
          label="登陆时间"
          sortable
      >
      </el-table-column>
      <el-table-column
          prop="loginAccount"
          label="登陆账号"
      >
      </el-table-column>

      <el-table-column
          label="登陆状态"
      >
        <template v-slot="{row}">
          <el-tag type="success" v-if="row.loginStatus === '1'">
            {{ row.loginStatusLabel }}
          </el-tag>
          <el-tag type="warn" v-if="row.loginStatus === '2'">
            {{ row.loginStatusLabel }}
          </el-tag>
        </template>
      </el-table-column>
    </CloudTb>
  </div>
</template>

<script>
// 分页组件
import CloudTb from '@/components/My/CloudTable'

export default {
  name: 'LoginLogIndex',
  // 定义组件
  components: {CloudTb},
  data() {
    return {
      dict:{
        loginStatus:[]
      },
      // 冗余参数
      extra: {
        time: [],
        pickerOptions: {
          shortcuts: [{
            text: '最近一周',
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
              picker.$emit('pick', [start, end]);
            }
          }, {
            text: '最近一个月',
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 30);
              picker.$emit('pick', [start, end]);
            }
          }, {
            text: '最近三个月',
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 90);
              picker.$emit('pick', [start, end]);
            }
          }]
        },
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
        loginCity: '',
        account: '',
        startTime: '',
        endTime: ''
      },
    }
  },
  computed: {},
  mounted() {
    // 初始化执行分页查询
    this.$api.cloud.sys.getDict({dictType: 'LOGIN_STATUS'}).then(res => {
      this.dict.loginStatus = res.data
    })
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
      if (this.extra.time.length > 0) {
        params.startTime = this.extra.time[0]
        params.endTime = this.extra.time[1]
      }
      this.loading = true
      this.$api.cloud.loginlog.page(params).then((r) => {
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
        loginCity: '',
        account: '',
        startTime: '',
        endTime: ''
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
