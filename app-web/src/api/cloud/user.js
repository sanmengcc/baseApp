import routerAPI from '@/api/router'
import { post, get } from '@/utils/request'

export function page(params) {
    return get('/gateway/api/userCenter/searchPage', params)
}

export function changePassword(params) {
    return post('/gateway/api/userCenter/changePasswordByAdmin', params, true);
}
