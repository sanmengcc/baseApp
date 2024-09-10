import { post, get } from '@/utils/request'

export function page(params) {
    return get('/base-app/api/userCenter/searchPage', params)
}

export function changePassword(params) {
    return post('/base-app/api/userCenter/changePasswordByAdmin', params, true);
}
