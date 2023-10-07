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
        <el-select class="filter-item search-item"
                   v-model="queryParams.status"
                   placeholder="用户状态">
          <el-option
              v-for="item in dict.userStatus"
              :key="item.dictKey"
              :label="item.dictValue"
              :value="item.dictKey">
          </el-option>
        </el-select>
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
        v-loading="loading"
        :pageOptions="pageOptions"
        @pagination="pagination"
    >
      <el-table-column
          prop="avatarUrl"
          label="用户头像"
          width="80"
      >
        <template v-slot="{row}">
          <el-avatar :size="50" :src="row.avatarUrl" v-if="row.avatarUrl"/>
        </template>
      </el-table-column>
      <el-table-column
          prop="userGlobalId"
          label="用户ID"
          width="250"
      />
      <el-table-column
          prop="userAccount"
          label="用户名"
      />
      <el-table-column
          prop="nickName"
          label="用户昵称"
      />
      <el-table-column
          prop="mobile"
          label="手机号码"
      />
      <el-table-column
          label="用户状态"
      >
        <template v-slot="{row}">
          <el-tag type="success" v-if="row.status === 'U01'">
            {{ row.statusLabel }}
          </el-tag>
          <el-tag type="warn" v-if="row.status === 'U02'">
            {{ row.statusLabel }}
          </el-tag>
          <el-tag type="danger" v-if="row.status === 'U03'">
            {{ row.statusLabel }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column
          prop="gmtCreate"
          label="创建时间"
          sortable
      >
      </el-table-column>
      <el-table-column
          :label="$t('table.operation')"
          align="center"
          min-width="150px"
          fixed="right"
          class-name="small-padding fixed-width"
      >
        <template v-slot="scope">
          <i class="el-icon-view table-operation" @click="viewTab(scope.row)" v-has-permission="['USER_INFO:VIEW']">查看</i>
          <el-dropdown @command="executeOperate" size="small" v-has-any-permission="getPermissionCode(operateOptions)">
            <span class="el-dropdown-link">{{ $t('table.operation') }}<i class="el-icon-arrow-down el-icon--right"></i></span>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item
                  v-for="(item, index) in operateOptions"
                  :key="index"
                  :icon="item.icon"
                  v-has-permission="[(item.permission)]"
                  :command="handleCommand(scope.row, item.value, item.param)"
              >{{ $t(item.label) }}
              </el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
        </template>
      </el-table-column>
    </CloudTb>
  </div>
</template>

<script>
// 分页组件
import CloudTb from '@/components/My/CloudTable'

import md5 from "js-md5";

export default {
  name: 'UserIndex',
  // 定义组件
  components: {CloudTb},
  data() {
    return {
      dict: {
        userStatus: []
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
      // 分页配置
      pageOptions: {
        rowKey: 'userId'
      },
      // 子页面的显示控制参数
      dialog: {
        isVisible: false,
        addTab: false,
        viewTab: false,
        editTab: false,
        title: ''
      },
      // loading参数
      loading: false,
      // 查询参数
      queryParams: {},
      // 分页操作栏参数
      operateOptions: [
        {
          value: 'edit',
          label: 'table.edit',
          permission: 'USER_INFO:EDIT',
          param: ''
        }, {
          value: 'delete',
          label: 'table.delete',
          permission: 'USER_INFO:DEL',
          param: ''
        }, {
          value: 'changePassword',
          permission: 'USER_INFO:CHANGE_PWD',
          label: '修改密码',
          param: ''
        }]
    }
  },
  computed: {},
  mounted() {
    this.$api.cloud.sys.getDict({dictType: 'USER_STATUS'}).then(res => {
      this.dict.userStatus = res.data
    })
    // 初始化执行分页查询
    this.fetch(this.queryParams)
  },
  methods: {
    // 详情操作tab
    viewTab(row) {
      const param = {roleId: row.roleId}
      this.$refs.view.setModule(param)
      this.dialog.title = '详情'
      this.dialog.viewTab = true
    },
    // 编辑操作tab
    editTab(row) {
      const param = {roleId: row.roleId}
      this.$refs.edit.setModule(param)
      this.dialog.title = '修改'
      this.dialog.editTab = true
    },
    // tab操作
    addTab() {
      this.dialog.title = '新增'
      this.dialog.addTab = true
    },
    // 窗口关闭回调
    close(tab) {
      console.log('close', tab)
      this.dialog[tab] = false
      this.refreshRow()
    },
    changePassword(row){
      this.$prompt('请输入新密码', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
      }).then(({ value }) => {
        if (value.length < 6) {
          this.$message({
            type: 'info',
            message: '密码长度不得低于6位'
          });
          return false
        }
        const param = {'userGlobalId':row.userGlobalId,'password':md5(value)}
        this.$api.cloud.user.changePassword(param).then(() => {
          this.$message({
            message: '密码修改成功',
            type: 'success'
          })
          this.search()
        })
      }).catch(e => {
        console.log(e);
      });
    },
    // 删除数据
    delete(row) {
      this.$confirm(this.$t('tips.confirmDelete'), this.$t('common.tips'), {
        confirmButtonText: this.$t('common.confirm'),
        cancelButtonText: this.$t('common.cancel'),
        type: 'warning'
      }).then(() => {
        this.loading = true
        this.$api.cloud.role.del({roleId: row.roleId}).then(() => {
          this.$message({
            message: this.$t('tips.deleteSuccess'),
            type: 'success'
          })
          this.search()
          this.refreshRow()
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
      this.$api.cloud.user.page(params).then((r) => {
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
    // 操作栏注册
    executeOperate(c) {
      switch (c.command) {
        case 'changePassword':
          this.changePassword(c.row)
          break
        default :
          return
      }
    },
    // 绑定操作栏指令
    handleCommand(row, command, param) {
      return {
        row: row,
        command: command,
        param: param
      }
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
