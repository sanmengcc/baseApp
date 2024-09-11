import {post, get} from '@/utils/request'


export function page(params) {
    return get('/base-app/api/oemInfo/page', params)
}

export function info(params) {
    return get('/base-app/api/oemInfo/info', params)
}

export function list(params) {
    return get('/base-app/api/oemInfo/list', params)
}
export function add(params) {
    return post('/base-app/api/oemInfo/add', params, true);
}

export function edit(params) {
    return post('/base-app/api/oemInfo/edit', params, true);
}

export function del(params) {
    return post('/base-app/api/oemInfo/delete', params, true);
}

