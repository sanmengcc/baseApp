import routerAPI from '@/api/router'
import { post, get } from '@/utils/request'

// 新增
export function add(params) {
  return post(routerAPI.role.ADD, params, true)
}

// 编辑
export function edit(params) {
  return post(routerAPI.role.EDIT, params, true)
}

// 详情
export function info(params) {
  return get(routerAPI.role.INFO, params)
}

// 分页
export function page(params) {
  return get(routerAPI.role.PAGE, params)
}

// 删除
export function del(params) {
  return post(routerAPI.role.DELETE, params, true);
}

export function list(params) {
  return get('/gateway/api/role/selectList', params)
}

export function getStaff(params) {
  return get('/gateway/api/role/selectStaffRole', params)
}

export function allotRole(params) {
  return post('/gateway/api/role/allotRole', params, true);
}

export function authModule(params) {
  return post('/gateway/api/role/authModule', params, true);
}

export function selectRoleModule(params) {
  return get('/gateway/api/role/selectRoleHasModuleId', params);
}
