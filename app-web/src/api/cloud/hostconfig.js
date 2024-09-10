import {post, get} from '@/utils/request'


export function page(params) {
    return get('/base-app/api/config/searchPage', params)
}

export function info(params) {
    return get('/base-app/api/config/selectById', params)
}

export function add(params) {
    return post('/base-app/api/config/addConfig', params, true);
}

export function edit(params) {
    return post('/base-app/api/config/updateById', params, true);
}

export function del(params) {
    return post('/base-app/api/config/delete', params, true);
}

