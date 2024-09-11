import { post, get } from '@/utils/request'

export function page(params) {
  return get('/base-app/api/staff/page', params)
}

export function del(params) {
  return post('/base-app/api/staff/delete', params,true)
}

export function edit(params) {
  return post('/base-app/api/staff/edit', params,true)
}

export function info(params) {
  return get('/base-app/api/staff/info', params)
}

export function add(params) {
  return post('/base-app/api/staff/add', params, true);
}
