import { post, get } from '@/utils/request'

// 新增
export function add(params) {
  return post("/base-app/api/role/add", params, true)
}

// 编辑
export function edit(params) {
  return post("/base-app/api/role/edit", params, true)
}

// 详情
export function info(params) {
  return get("/base-app/api/role/info", params)
}

// 分页
export function page(params) {
  return get("/base-app/api/role/page", params)
}

// 删除
export function del(params) {
  return post("/base-app/api/role/delete", params, true);
}

export function list(params) {
  return get('/base-app/api/role/list', params)
}

export function getStaff(params) {
  return get('/base-app/api/role/selectStaffRole', params)
}

export function allotRole(params) {
  return post('/base-app/api/role/allotRole', params, true);
}

export function authModule(params) {
  return post('/base-app/api/role/authModule', params, true);
}

export function selectRoleModule(params) {
  return get('/base-app/api/role/selectRoleHasModuleId', params);
}
