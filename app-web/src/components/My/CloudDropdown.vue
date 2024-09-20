<template>
  <el-dropdown
    :scope="scope"
    @command="executeParentOperate($event)" size="small"
    v-has-any-permission="getPermissionCode(options)">
    <span class="el-dropdown-link">操作<i class="el-icon-arrow-down el-icon--right"></i></span>
    <el-dropdown-menu slot="dropdown">
      <el-dropdown-item v-for="(item, index) in options"
                        :key="index" :icon="item.icon"
                        v-has-permission="[(item.permission)]"
                        :command="handleCommand(scope.row, item.value, item.param)">{{ $t(item.label) }}
      </el-dropdown-item>
    </el-dropdown-menu>
  </el-dropdown>
</template>
<script>

import {executeParentOperate} from "@/utils/core";

export default {
  name: 'CloudDropdown',
  components: { },
  props: {
    options: {
      type:Array,
      default:[]
    },
    scope: {
      type:Object,
      default:{}
    }
  },
  data() {
    return {
    }
  },
  activated() {

  },
  mounted() {

  },
  methods: {
    executeParentOperate
  }
}
</script>
