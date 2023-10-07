import Vue from 'vue'
import * as core from './baseapi'
import * as cloud from './cloud/index'
const api = {
  core, cloud
}

Vue.prototype.$api = api
export default {
  api
}

