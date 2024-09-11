import { post, get } from '@/utils/request'

// 新增菜单
export function add(params) {
  return post("/base-app/api/module/add", params, true);
}

// 编辑菜单
export function edit(params) {
  return post("/base-app/api/module/edit", params, true)
}

// 菜单详情
export function info(params) {
  return get("/base-app/api/module/info", params)
}

// 菜单分页
export function page(params) {
  return get("/base-app/api/module/page", params)
}

// 菜单列表
export function list(params) {
  return get("/base-app/api/module/list", params)
}

// 菜单删除
export function del(params) {
  return post("/base-app/api/module/delete", params, true);
}

// 菜单列表
export function change(params) {
  return post("/base-app/api/module/change", params, true)
}

export function selectList(params) {
  return get('/base-app/api/module/tree', params)
}

