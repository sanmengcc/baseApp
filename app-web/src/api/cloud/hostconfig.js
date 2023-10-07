import {post, get} from '@/utils/request'


export function page(params) {
    return get('/gateway/api/config/searchPage', params)
}

export function info(params) {
    return get('/gateway/api/config/selectById', params)
}

export function add(params) {
    return post('/gateway/api/config/addConfig', params, true);
}

export function edit(params) {
    return post('/gateway/api/config/updateById', params, true);
}

export function del(params) {
    return post('/gateway/api/config/delete', params, true);
}

