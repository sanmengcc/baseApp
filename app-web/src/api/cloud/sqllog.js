import routerAPI from '@/api/router'
import { post, get } from '@/utils/request'

// 分页查询
export function page(params) {
  return get('/gateway/api/sqlLog/searchPage', params)
}

// 删除
export function del(params) {
  return post('/gateway/api/sqlLog/delete', params,true)
}

export function info(params) {
  return get('/gateway/api/sqlLog/selectById', params)
}
