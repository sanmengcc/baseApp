import routerAPI from '@/api/router'
import { post, get } from '@/utils/request'

// 新增菜单
export function add(params) {
  return post(routerAPI.module.ADD, params, true)
}

// 编辑菜单
export function edit(params) {
  return post(routerAPI.module.EDIT, params, true)
}

// 菜单详情
export function info(params) {
  return get(routerAPI.module.INFO, params)
}

// 菜单分页
export function page(params) {
  return get(routerAPI.module.PAGE, params)
}

// 菜单列表
export function list(params) {
  return get(routerAPI.module.LIST, params)
}

// 菜单删除
export function del(params) {
  return post(routerAPI.module.DELETE, params, true);
}

// 菜单列表
export function change(params) {
  return post(routerAPI.module.CHANGE, params, true)
}

export function selectList(params) {
  return get('/gateway/api/module/selectTree', params)
}

