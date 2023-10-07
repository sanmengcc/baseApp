import routerAPI from '@/api/router'
import { post, get } from '@/utils/request'

// 新增系统
export function add(params) {
  return post(routerAPI.system.ADD, params, true)
}

// 修改系统
export function edit(params) {
  return post(routerAPI.system.EDIT, params, true)
}

// 查询系统详情
export function info(params) {
  return get(routerAPI.system.INFO, params, true)
}

// 查询系统列表
export function list(params) {
  return get(routerAPI.system.LIST, params, true)
}

// 删除系统
export function del(params) {
  return post(routerAPI.system.DELETE, params)
}

// 分页查询系统
export function page(params) {
  return get(routerAPI.system.PAGE, params, true)
}
