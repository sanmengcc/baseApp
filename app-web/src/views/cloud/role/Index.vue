<template>
  <div class="app-container">
    <!-- 头部搜索栏   -->
    <div class="filter-container" ref="header" style="min-width: 1000px; width: 100%;">
      <el-form ref="form" :inline="true" label-width="80px">
        <el-input
            v-model="queryParams.roleName"
            placeholder="请输入角色名称"
            class="filter-item search-item"
        />
        <el-button class="filter-item" type="primary" plain @click="search">
          {{ $t('table.search') }}
        </el-button>
        <el-button class="filter-item" type="success" plain @click="reset">
          {{ $t('table.reset') }}
        </el-button>
        <el-button v-has-permission="['ROLE:ADD']" class="filter-item" type="success" plain @click="addTab">
          {{ $t('table.add') }}
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
          prop="roleName"
          label="角色名称"
      >
      </el-table-column>
      <el-table-column
          prop="roleCode"
          label="角色编码"
      >
      </el-table-column>
      <el-table-column
          prop="roleTypeLabel"
          label="角色类型"
      >
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
          <i class="el-icon-view table-operation" @click="viewTab(scope.row)" v-has-permission="['ROLE:VIEW']">查看</i>
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
    <!-- 新增页   -->
    <add
        ref="add"
        :visible="dialog.addTab"
        :title="dialog.title"
        @success="search"
        @close="close"
    />
    <!-- 编辑页   -->
    <edit
        ref="edit"
        :visible="dialog.editTab"
        :title="dialog.title"
        @success="search"
        @close="close"
    />
    <!-- 详情页   -->
    <Views
        ref="view"
        :visible="dialog.viewTab"
        :title="dialog.title"
        @close="close"
    />

    <Menu
        ref="menu"
        :visible="dialog.menuTab"
        :title="dialog.title"
        @close="close"
    />

  </div>
</template>

<script>
// 分页组件
import CloudTb from '@/components/My/CloudTable'
// 新增页面
import Add from './Add'
// 修改页面
import Edit from './Edit'
// 详情页面
import Views from './View'

import Menu from './Menu'
import {getPermissionCode, hasPermission} from "@/utils/permissionDirect";

export default {
  name: 'RoleIndex',
  // 定义组件
  components: {Add, Edit, Views, Menu, CloudTb},
  data() {
    return {
      // 冗余参数
      extra: {
        parentId: '',
        systemId: ''
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
        rowKey: 'roleId'
      },
      // 子页面的显示控制参数
      dialog: {
        isVisible: false,
        addTab: false,
        menuTab: false,
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
          permission: 'ROLE:EDIT',
          param: ''
        }, {
          value: 'delete',
          label: 'table.delete',
          permission: 'ROLE:DEL',
          param: ''
        }, {
          value: 'menu',
          permission: 'ROLE:MENU',
          label: '授权菜单',
          param: ''
        }]
    }
  },
  computed: {
  },
  mounted() {
    // 初始化执行分页查询
    this.fetch(this.queryParams)
  },
  methods: {
    getPermissionCode,
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
    // tab操作
    menuTab(row) {
      const param = {roleId: row.roleId}
      this.$refs.menu.setModule(param)
      this.dialog.title = '授权菜单'
      this.dialog.menuTab = true
    },
    // 窗口关闭回调
    close(tab) {
      console.log('close', tab)
      this.dialog[tab] = false
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
      this.$api.cloud.role.page(params).then((r) => {
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
        case 'add':
          this.addChild(c.row)
          break
        case 'edit':
          this.editTab(c.row)
          break
        case 'delete':
          this.delete(c.row)
          break
        case 'menu':
          this.menuTab(c.row)
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
