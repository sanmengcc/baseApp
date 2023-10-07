import routerAPI from '@/api/router'
import { post, get } from '@/utils/request'

// 菜单详情
export function page(params) {
  return get(routerAPI.loginlog.PAGE, params)
}
