import { post, get } from '@/utils/request'

// 分页查询
export function page(params) {
  return get('/base-app/api/requestLog/page', params)
}

// 删除
export function del(params) {
  return post('/base-app/api/requestLog/delete', params,true)
}
