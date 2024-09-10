import { post, get } from '@/utils/request'

// 分页查询
export function page(params) {
  return get('/base-app/api/sqlLog/searchPage', params)
}

// 删除
export function del(params) {
  return post('/base-app/api/sqlLog/delete', params,true)
}

export function info(params) {
  return get('/base-app/api/sqlLog/selectById', params)
}
