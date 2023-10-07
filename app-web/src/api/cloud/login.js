import { post, get } from '@/utils/request'

// 获取图片验证码
export function captchaImage(param) {
  return get('/gateway/api/userCenter/captcha/captchaImage', param)
}

// 登陆
export function userLogin(param) {
  return post('/gateway/api/oauth/Login', param, true)
}

// 获取用户上下文信息
export function getUserInfo() {
  return get('/gateway/api/auth/getUserInfo')
}

// 获取用户上下文信息
export function logout() {
  return get('/gateway/api/oauth/logout')
}

// 获取权限菜单列表
export function getPermissions() {
  return get('/gateway/api/userCenter/getPermissions')
}

