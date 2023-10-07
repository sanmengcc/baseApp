import axios from 'axios'
import {Message} from 'element-ui'
import {getToken,getHostConfig} from '@/utils/auth'
import qs from 'qs'
import Vue from 'vue'
import MessageOnce from './messageOnce'
import router from '@/router'
import proxy from '@/api/proxy.js'
import md5 from "js-md5";
import {decrypt, encode} from './cryptoJs'

const messageOnce = new MessageOnce()
const service = axios.create({
    timeout: 5000
})

// request interceptor
service.interceptors.request.use(
    config => {
        // 对请求参数进行签名
        if (process.env.NODE_ENV !== 'dev') {
            const target = config.url.substring(0, config.url.indexOf('/', 1))
            if (target.indexOf('/') === 0 && target != null && target !== '') {
                let domain = proxy[target]
                domain = domain != null ? domain.target : null
                if (domain != null) config.url = domain + config.url.substring(config.url.indexOf('/', 1))
            }
        }

        let token = getToken();
        if (token) {
            config.headers['Authorization'] = token
        }
        let hostConfig = getHostConfig();
        if (hostConfig && hostConfig.oemCode != null && hostConfig.oemCode != undefined) {
            config.headers['oem-code'] = hostConfig.oemCode;
        }
        return config;
    },
    error => {
        console.log(error) // for debug
        return Promise.reject(error)
    }
)
// 提示信息显示时长
const messageDuration = 5 * 1000

service.interceptors.response.use(
    response => {
        const res = response.data
        const code = res.code
        const encryptHeader = response.headers['encrypt']
        if (encryptHeader == 'true') {
            res.data = JSON.parse(decrypt(res.data, "", ""));
        }
        console.log(res.data)

        if (code === '600' || code === 600) {
            router.push('/login')
            messageOnce.error({
                message: '登录已过期，请重新登录',
                type: 'info',
                duration: messageDuration
            })
        } else if (code === 602 || code === '602') {
            router.push('/login')
            messageOnce.error({
                message: '登录已过期，请重新登录',
                type: 'info',
                duration: messageDuration
            })
        } else if (code === '200') {
            return res
        } else {
            const desc = res.msg
            Message({
                message: desc,
                type: 'error',
                duration: 5 * 1000
            })
            return Promise.reject(desc)
        }
    },
    error => {
        Message({
            message: error.message,
            type: 'error',
            duration: 5 * 1000
        })
        return Promise.reject(error)
    }
)

export function get(url, params) {
    if (params == null || params == undefined) {
        params = {}
    }
    let newParams = JSON.parse(JSON.stringify(params));

    newParams['operateTime'] = new Date().getTime() + "";
    newParams['sign'] = getSign(newParams)

    return service.get(url, {params: newParams})
}

// 参数排序
function getSign(obj) {
    if (JSON.stringify(obj) == "{}" || obj == null) {
        return {}
    }
    let key = Object.keys(obj)?.sort()
    let newObj = {}
    for (let i = 0; i < key.length; i++) {
        newObj[key[i]] = obj[key[i]]
    }
    let str = JSON.stringify(newObj) + 'siosdosdadsasdasd';
    str = str.replaceAll(/"/g, "");
    // JSON数据存在特殊符号[和]
    str = str.replaceAll("\[", "");
    str = str.replaceAll("]", "");
    str = str.replaceAll("]", "");
    let sign = md5(str);
    return sign;
}

// 参数序列化
function dataSerialize(sortObj) {
    let strJoin = ''
    for (let key in sortObj) {
        let element = sortObj[key];
        strJoin += key + "=" + element + "&"
    }
    return strJoin
}

export function post(url, params = {}, json = false) {
    const newParams = JSON.parse(JSON.stringify(params));

    if (params == null || params == undefined) {
        params = {}
    }
    params['operateTime'] = new Date().getTime() + "";
    newParams['sign'] =  getSign(newParams)

    // json格式请求头
    const headerJSON = {
        'Content-Type': 'application/json'
    }
    // FormData格式请求头
    const headerFormData = {
        'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'
    }
    return new Promise((resolve, reject) => {
        service
            .post(url, json ? JSON.stringify(newParams) : qs.stringify(newParams), {
                headers: json ? headerJSON : headerFormData
            })
            .then(res => {
                resolve(res)
            })
            .catch(err => {
                reject(err)
            })
    })
}

Vue.prototype.$post = post
Vue.prototype.$get = get
export default service
