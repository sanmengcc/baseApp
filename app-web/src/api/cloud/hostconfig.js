import {post, get} from '@/utils/request'


export function page(params) {
    return get('/base-app/api/config/page', params)
}

export function info(params) {
    return get('/base-app/api/config/info', params)
}

export function add(params) {
    return post('/base-app/api/config/add', params, true);
}

export function edit(params) {
    return post('/base-app/api/config/edit', params, true);
}

export function del(params) {
    return post('/base-app/api/config/delete', params, true);
}

