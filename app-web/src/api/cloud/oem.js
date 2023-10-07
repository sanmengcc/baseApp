import {post, get} from '@/utils/request'


export function page(params) {
    return get('/gateway/api/oemInfo/searchPage', params)
}

export function info(params) {
    return get('/gateway/api/oemInfo/selectById', params)
}

export function list(params) {
    return get('/gateway/api/oemInfo/selectList', params)
}
export function add(params) {
    return post('/gateway/api/oemInfo/addOem', params, true);
}

export function edit(params) {
    return post('/gateway/api/oemInfo/updateById', params, true);
}

export function del(params) {
    return post('/gateway/api/oemInfo/delete', params, true);
}

