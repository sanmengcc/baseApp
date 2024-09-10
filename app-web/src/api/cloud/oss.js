import { post, get } from '@/utils/request'

// 分页查询
export function page(params) {
    return get('/base-app/api/common/oss/searchPage', params)
}
