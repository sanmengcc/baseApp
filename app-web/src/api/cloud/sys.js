import request, { post, get } from '@/utils/request'

// 新增系统
export function getDict(params) {
    return get("/base-app/api/dict/getDict", params, true)
}

export function getDictMap(dictTypes) {
    const params = {'dictTypes': dictTypes}
    return get('/base-app/api/dict/getDicMap', params);
}

export function getHostConfig() {
    const params = {}

    return new Promise((resolve, reject) => {
        get('/base-app/api/config/getHostConfig', params)
            .then(resp => {
                resolve(resp)
            }).catch((res) => {
            reject()
        })
    })
}

