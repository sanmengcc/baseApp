import Vue from 'vue'

import Cookies from 'js-cookie'

// api请求
import '@/api'
import pageApi from './utils/page'
Vue.prototype.pageApi = pageApi

import 'normalize.css/normalize.css' // a modern alternative to CSS resets
import Element from 'element-ui'
import './styles/element-variables.scss'
import enLang from 'element-ui/lib/locale/lang/zh-CN'// 如果使用中文语言包请默认支持，无需额外引入，请删除该依赖

import '@/styles/index.scss' // global css

import App from './App'
import store from './store'
import router from './router'

import './icons' // icon
import './permission' // permission control
import './utils/error-log' // error log

import * as filters from './filters' // global filters

import i18n from './lang'

import {hasPermission, hasNoPermission, hasAnyPermission, getPermissionCode,showPermission} from './utils/permissionDirect'

/**
 * If you don't want to use mock-server
 * you want to use MockJs for mock api
 * you can execute: mockXHR()
 *
 * Currently MockJs will be used in the production environment,
 * please remove it before going online ! ! !
 */
const Plugins = [
  hasPermission,
  hasNoPermission,
  hasAnyPermission
]
Vue.prototype.getPermissionCode = getPermissionCode
Vue.prototype.showPermission = showPermission

Plugins.map((plugin) => {
  Vue.use(plugin)
})

if (process.env.NODE_ENV === 'production') {
  const { mockXHR } = require('../mock')
  mockXHR()
}

Vue.use(Element, {
  size: Cookies.get('size') || 'medium', // set element-ui default size
  locale: enLang // 如果使用中文，无需设置，请删除
})

Vue.use(Element, {
  i18n: (key, value) => i18n.t(key, value)
})

// register global utility filters
Object.keys(filters).forEach(key => {
  Vue.filter(key, filters[key])
})

// exports.install = function(Vue, options) {
//
//   Vue.prototype.havePermission = function (item) { //全局函数1
//     const permissions = store.state.account.permissions
//     if (permissions.includes(item)) {
//       return true;
//     } else {
//       return false;
//     }
//
//   };
// }

Vue.config.productionTip = false

new Vue({
  el: '#app',
  router,
  store,
  i18n,
  render: h => h(App)
})

