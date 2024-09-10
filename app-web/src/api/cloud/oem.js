import {post, get} from '@/utils/request'


export function page(params) {
    return get('/base-app/api/oemInfo/searchPage', params)
}

export function info(params) {
    return get('/base-app/api/oemInfo/selectById', params)
}

export function list(params) {
    return get('/base-app/api/oemInfo/selectList', params)
}
export function add(params) {
    return post('/base-app/api/oemInfo/addOem', params, true);
}

export function edit(params) {
    return post('/base-app/api/oemInfo/updateById', params, true);
}

export function del(params) {
    return post('/base-app/api/oemInfo/delete', params, true);
}

