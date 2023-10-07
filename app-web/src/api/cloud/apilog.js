import routerAPI from '@/api/router'
import { post, get } from '@/utils/request'

// 分页查询
export function page(params) {
  return get('/gateway/api/requestLog/searchPage', params)
}

// 删除
export function del(params) {
  return post('/gateway/api/requestLog/delete', params,true)
}
