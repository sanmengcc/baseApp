import routerAPI from '@/api/router'
import request, { post, get } from '@/utils/request'

// 新增系统
export function getDict(params) {
    return get(routerAPI.dict.DICT_TYPE, params, true)
}

export function getDictMap(dictTypes) {
    const params = {'dictTypes': dictTypes}
    return get('/gateway/api/dict/getDicMap', params);
}

export function getHostConfig() {
    const params = {}

    return new Promise((resolve, reject) => {
        get('/gateway/api/config/getHostConfig', params)
            .then(resp => {
                resolve(resp)
            }).catch((res) => {
            reject()
        })
    })
}

