import { post, get } from '@/utils/request'

export function page(params) {
  return get('/gateway/api/staff/searchPage', params)
}

export function del(params) {
  return post('/gateway/api/staff/delete', params,true)
}

export function edit(params) {
  return post('/gateway/api/staff/updateById', params,true)
}

export function info(params) {
  return get('/gateway/api/staff/selectById', params)
}

export function add(params) {
  return post('/gateway/api/staff/addStaff', params, true);
}
