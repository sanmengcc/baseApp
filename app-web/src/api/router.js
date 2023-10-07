// 所有的api定义

// 路由前缀
const ROUTER = '/gateway'

const login = {
  // 图片验证码
  CAPTCHA_IMAGE: ROUTER + '/api/userCenter/captcha/captchaImage',
  // 用户详细信息
  USER_INFO: ROUTER + '/api/auth/getUserInfo',
  // 用户登录
  LOGIN: ROUTER + '/api/oauth/Login',
  // 退出登录
  LOGOUT: ROUTER + '/api/oauth/logout',
  // 获取权限
  PERMISSIONS: ROUTER + '/api/oauth/getPermissions',

}

// 菜单相关
const module = {
  // 新增菜单
  ADD: ROUTER + '/api/module/addModule',
  // 修改菜单
  EDIT: ROUTER + '/api/module/updateById',
  // 查询菜单详情
  INFO: ROUTER + '/api/module/selectById',
  // 查询菜单分页
  PAGE: ROUTER + '/api/module/searchPage',
  // 查询菜单列表
  LIST: ROUTER + '/api/module/selectByParentId',
  // 删除菜单
  DELETE: ROUTER + '/api/module/delete',
  // 修改菜单部分字段
  CHANGE: ROUTER + '/api/module/change'
}

// 角色相关
const role = {
  // 新增
  ADD: ROUTER + '/api/role/addRole',
  // 修改
  EDIT: ROUTER + '/api/role/updateById',
  // 查询详情
  INFO: ROUTER + '/api/role/selectById',
  // 查询分页
  PAGE: ROUTER + '/api/role/searchPage',
  // 删除
  DELETE: ROUTER + '/api/role/delete',
}

const system = {
  // 新增系统
  ADD: ROUTER + '/api/auth/system/systemAdd',
  // 修改系统
  EDIT: ROUTER + '/api/auth/system/systemEdit',
  // 查询系统详情
  INFO: ROUTER + '/api/auth/system/selectById',
  // 查询系统列表
  LIST: ROUTER + '/api/auth/system/systemList',
  // 删除系统
  DELETE: ROUTER + '/api/auth/system/delete',
  // 分页查询系统
  PAGE: ROUTER + '/api/auth/system/systemPage',
}

const loginlog = {
  // 查询列表
  PAGE: ROUTER + '/api/loginLog/getLogPage',
}


const dict = {
  // 获取数据字典组
  DICT_TYPE: ROUTER + '/api/dict/getDict',
}
export default {
  login,
  module,
  loginlog,
  role,
  dict,
  system
}

