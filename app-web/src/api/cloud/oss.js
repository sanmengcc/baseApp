import { post, get } from '@/utils/request'

// 分页查询
export function page(params) {
    return get('/gateway/api/common/oss/searchPage', params)
}
