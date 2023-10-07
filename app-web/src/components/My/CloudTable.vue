<template>
  <!-- 自定义Table分页组件 -->
  <div ref="cloudTbBox">
    <el-table
      ref="cloudTb"
      :load='tbTreeLoad'
      :lazy="pageOptions.lazy || false"
      :row-key="pageOptions.rowKey"
      :tree-props="pageOptions.tree_props"
      :data="page.dataList"
      :height="screenHeight"
      :header-cell-style="{background:'#eef1f6',color:'#606266'}"
      style="width:100%;"
      @selection-change='selectChange'
    >
      <slot></slot>
    </el-table>
    <!-- 分页 -->
    <pagination
      ref="pagination"
      v-show="page.total>0"
      class="filter-footer"
      :current-page.sync="page.currentPage"
      :page-size.sync="page.pageSize"
      :page-sizes="page.pageSizes"
      :total="page.total"
      @pagination="search"></pagination>
  </div>
</template>

<script>
import Pagination from '@/components/Pagination'

export default {
  name: 'CloudTb',
  components: { Pagination },
  directives: {
    focus: {
      inserted: function(e) {
        e.querySelector('input').focus()
      }
    }
  },
  props: {
    selection: [],
    page: {
      type: Object,
      required: true
    },
    pageOptions: {
      type: Object,
      required: true
    },
    rowKey: {
      type: String,
      default: ''
    },
    options: {
      type: Object,
      default() {
        return {
          lazy: false,
          load: null
        }
      }
    }
  },
  data() {
    return {
      screenHeight: 0
    }
  },
  // keep-alive 解决错位问题
  activated() {
    if (this.$refs.cloudTb && this.$refs.cloudTb.doLayout) {
      this.$refs.cloudTb.doLayout()
    }
  },
  mounted() {
    // table高度自适应
    this.$nextTick(() => {
      this.screenHeight = window.innerHeight - 100 - 160
      window.onresize = () => {
        this.screenHeight = window.innerHeight - 100 - 160
      }
    })
  },
  methods: {
    // 触发搜索
    search(e) {
      var page = {}
      page.currentPage = e.page
      page.pageSize = e.limit
      this.$emit('pagination', page)
    },
    refreshRow(index, dataList) {
      this.$set(this.$refs.cloudTb.store.states.lazyTreeNodeMap, index, dataList)
    },
    // 选中事件
    selectChange(selection) {
      this.$emit('selectChange', selection)
    },
    // load
    tbTreeLoad(tree, treeNode, resolve) {
      this.$emit('tbTreeLoad', tree, treeNode, resolve)
    }
  }
}
</script>
<style lang="scss" scoped>

.el-table {
  /* 表格高度设置 */
  width: 100% !important;
}

::-webkit-scrollbar-track {
  border-radius: 1px;
}
.el-table__fixed-right-patch{
  width: 0px !important;
}

</style>
