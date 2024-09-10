import { post, get } from '@/utils/request'

// 菜单详情
export function page(params) {
  return get("/base-app/api/loginLog/getLogPage", params)
}
