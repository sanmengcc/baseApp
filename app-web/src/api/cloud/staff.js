import { post, get } from '@/utils/request'

export function page(params) {
  return get('/base-app/api/staff/searchPage', params)
}

export function del(params) {
  return post('/base-app/api/staff/delete', params,true)
}

export function edit(params) {
  return post('/base-app/api/staff/updateById', params,true)
}

export function info(params) {
  return get('/base-app/api/staff/selectById', params)
}

export function add(params) {
  return post('/base-app/api/staff/addStaff', params, true);
}
