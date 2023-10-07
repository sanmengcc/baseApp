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
        <el-button v-has-permission="['MODULE:ADD']" class="filter-item" type="success" plain @click="addTab">
          {{ $t('table.add') }}
        </el-button>
      </el-form>
    </div>
    <!-- 分页组件   -->
    <CloudTb
      ref="cloudTb"
      :page="pageData"
      v-loading="loading"
      @tbTreeLoad="tbTreeLoad"
      :pageOptions="pageOptions"
      :tree_props="{hasChildren: 'hasChildren'}"
      @pagination="pagination"
    >
      <el-table-column
        prop="name"
        label="菜单名称"
      >
      </el-table-column>
      <el-table-column
        prop="jumpAction"
        label="菜单路径"
      >
      </el-table-column>
      <el-table-column
        prop="typeLabel"
        label="功能类型"
      >
      </el-table-column>
      <el-table-column
        prop="seq"
        label="排序"
      >
      </el-table-column>
      <el-table-column
        label="显示/隐藏"
      >
        <template v-slot="{row}">
          <el-switch
            v-model="row.hidden"
            active-value="1"
            inactive-value="2"
            :disabled="showPermission('MODULE:EDIT')"
            @change="changeHidden($event,row)"
          ></el-switch>
        </template>
      </el-table-column>
      <el-table-column
        label="启用"
      >
        <template v-slot="{row}">
          <el-switch
            v-model="row.enableStatus"
            active-value="1"
            inactive-value="2"
            :disabled="showPermission('MODULE:EDIT')"
            @change="changeEnable($event,row)"
          ></el-switch>
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
          <i class="el-icon-view table-operation" @click="viewTab(scope.row)" v-has-permission="['MODULE:VIEW']">查看</i>
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
import {showPermission} from "@/utils/permissionDirect";

export default {
  name: 'ModuleIndex',
  // 定义组件
  components: { Add, Edit, Views, CloudTb },
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
        rowKey: 'moduleId',
        tree_props: { children: 'children', hasChildren: 'hasChildren' },
        lazy: true
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
      queryParams: {
        parentId: 0
      },
      // 分页操作栏参数
      operateOptions: [
        {
          value: 'add',
          label: 'table.add',
          icon: 'el-icon-folder-add',
          permission: 'MODULE:ADD',
          param: ''
        }, {
          value: 'edit',
          label: 'table.edit',
          icon: 'el-icon-edit',
          permission: 'MODULE:EDIT',
          param: ''
        }, {
          value: 'delete',
          label: 'table.delete',
          icon: 'el-icon-document-delete',
          permission: 'MODULE:DEL',
          param: ''
        }]
    }
  },
  computed: {},
  mounted() {
    this.queryParams.systemId = this.$router.currentRoute.params.systemId
    this.extra.systemId = this.$router.currentRoute.params.systemId
    // 初始化执行分页查询
    this.fetch(this.queryParams)
  },
  methods: {
    addChild(row) {
      this.dialog.title = '新增'
      this.dialog.addTab = true
      this.extra.parentId = row.moduleId
      const param = { parentId: row.moduleId, systemId: row.systemId }
      this.$refs.add.setModule(param)
    },
    // 详情操作tab
    viewTab(row) {
      const param = { moduleId: row.moduleId }
      this.$refs.view.setModule(param)
      this.dialog.title = '查看详情'
      this.dialog.viewTab = true
    },
    // 编辑操作tab
    editTab(row) {
      const param = { moduleId: row.moduleId }
      this.extra.parentId = row.parentId
      this.$refs.edit.setModule(param)
      this.dialog.title = '修改'
      this.dialog.editTab = true
    },
    // tab操作
    addTab() {
      this.dialog.title = '新增系统'
      this.dialog.addTab = true
      const param = { systemId: this.extra.systemId }
      this.$refs.add.setModule(param)
    },
    // 窗口关闭回调
    close(tab) {
      this.dialog[tab] = false
      this.refreshRow()
    },
    changeHidden(event, row) {
      const params = {
        moduleId: row.moduleId,
        hidden: event
      }
      this.changeField(params)
    },
    changeEnable(event, row) {
      const params = {
        moduleId: row.moduleId,
        enableStatus: event
      }
      this.changeField(params)
    },
    changeField(params) {
      this.$api.cloud.module.change(params).then(res => {
        if (res.code === '200') {
          this.search()
          this.$message({
            message: '修改成功',
            type: 'success'
          })
        } else {
          this.$message({
            message: res.desc,
            type: 'error'
          })
        }
      })
    },
    // 刷新tree的子节点
    refreshRow() {
      const parentId = this.extra.parentId
      const params = { parentId: this.extra.parentId, systemId: this.extra.systemId }
      if (parentId) {
        this.$api.cloud.module.list(params)
          .then(res => {
            this.$refs.cloudTb.refreshRow(parentId, res.data)
          })
      }
    },
    // 删除数据
    delete(row) {
      this.extra.parentId = row.parentId
      this.$confirm(this.$t('tips.confirmDelete'), this.$t('common.tips'), {
        confirmButtonText: this.$t('common.confirm'),
        cancelButtonText: this.$t('common.cancel'),
        type: 'warning'
      }).then(() => {
        this.loading = true
        this.$api.cloud.module.del({ moduleId: row.moduleId }).then(() => {
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
    // 子节点异步加载
    tbTreeLoad(tree, treeNode, resolve) {
      const params = { parentId: tree.moduleId}
      this.$api.cloud.module.list(params).then((r) => {
        const data = r.data
        resolve(data)
      })
    },
    // 分页查询
    fetch(params = {}) {
      params.currentPage = this.pageData.currentPage
      params.pageSize = this.pageData.pageSize
      if (this.queryParams.timeRange) {
        params.createTimeFrom = this.queryParams.timeRange[0]
        params.createTimeTo = this.queryParams.timeRange[1]
      }
      this.loading = true
      this.$api.cloud.module.page(params).then((r) => {
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
      this.queryParams = {
        parentId: 0,
        systemId: this.extra.systemId
      }
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
