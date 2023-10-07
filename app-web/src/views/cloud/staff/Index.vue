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
                   placeholder="在职状态">
          <el-option
              v-for="item in dict.WORK_STATUS"
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
        <el-button v-has-permission="['STAFF:ADD']" class="filter-item" type="success" plain @click="addTab">
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
          prop="avatarUrl"
          label="员工头像"
          sortable
      >
        <template v-slot="{row}">
          <el-avatar :size="50" :src="row.avatarUrl" v-if="row.avatarUrl"/>
        </template>
      </el-table-column>
      <el-table-column
          prop="account"
          label="员工账号"
      />
      <el-table-column
          prop="staffName"
          label="员工姓名"
      />
      <el-table-column
          prop="mobile"
          label="手机号码"
      />
      <el-table-column
          prop="email"
          label="电子邮箱"
      />
      <el-table-column
          prop="entryDate"
          label="入职日期"
      />
      <el-table-column
          prop="adminTypeLabel"
          label="员工类型"
      />
      <el-table-column
          prop="status"
          label="工作状态"
          sortable
      >
        <template v-slot="{row}">
          <el-tag type="success" v-if="row.status === '1'">
            {{ row.statusLabel }}
          </el-tag>
          <el-tag type="danger" v-if="row.status === '2'">
            {{ row.statusLabel }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column
          prop="lastLoginTime"
          label="上次登陆时间"
      />
      <el-table-column
          :label="$t('table.operation')"
          align="center"
          min-width="150px"
          fixed="right"
          class-name="small-padding fixed-width"
      >
        <template v-slot="scope">
          <i class="el-icon-view table-operation" @click="viewTab(scope.row)" v-has-permission="['STAFF:VIEW']">查看</i>
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

    <!-- 详情页   -->
    <Role
        ref="role"
        :visible="dialog.roleTab"
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

import Role from './Role'
import md5 from "js-md5";
export default {
  name: 'StaffIndex',
  // 定义组件
  components: {Add, Edit, Views,Role, CloudTb},
  data() {
    return {
      dict:{
      },
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
        roleTab: false,
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
          permission: 'STAFF:EDIT',
          param: ''
        }, {
          value: 'delete',
          label: 'table.delete',
          permission: 'STAFF:DEL',
          param: ''
        }, {
          value: 'changePassword',
          permission: 'STAFF:CHANGE_PWD',
          label: '修改密码',
          param: ''
        },{
          value: 'allotRole',
          permission: 'STAFF:ALLOT_ROLE',
          label: '分配角色',
          param: ''
        }]
    }
  },
  computed: {},
  mounted() {
    // 初始化执行分页查询
    this.$api.cloud.sys.getDict({dictType: 'WORK_STATUS'}).then(res => {
      this.dict.WORK_STATUS = res.data
    })
    // 初始化执行分页查询
    this.fetch(this.queryParams)
  },
  methods: {
    // 详情操作tab
    viewTab(row) {
      const param = {staffId: row.staffId}
      this.$refs.view.setModule(param)
      this.dialog.title = '详情'
      this.dialog.viewTab = true
    },
    // 编辑操作tab
    editTab(row) {
      const param = {staffId: row.staffId}
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
      this.dialog[tab] = false
    },
    allotRole(row){
      const param = {staffId: row.staffId}
      this.$refs.role.setModule(param)
      this.dialog.title = '分配角色'
      this.dialog.roleTab = true
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
        this.$api.cloud.staff.del({staffId: row.staffId,userGlobalId: row.userGlobalId}).then(() => {
          this.$message({
            message: this.$t('tips.deleteSuccess'),
            type: 'success'
          })
          this.search()
        }).catch(()=>{
          this.loading = false
        })
      }).catch(() => {
        this.loading = false
        this.search()
      })
    },
    // 分页查询
    fetch(params = {}) {
      params.currentPage = this.pageData.currentPage
      params.pageSize = this.pageData.pageSize
      this.loading = true
      this.$api.cloud.staff.page(params).then((r) => {
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
        case 'changePassword':
          this.changePassword(c.row)
          break
        case 'allotRole':
          this.allotRole(c.row)
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
