import router from './router'
import store from './store'
import NProgress from 'nprogress' // progress bar
import 'nprogress/nprogress.css' // progress bar style
import Layout from '@/layout'
import db from '@/utils/localstorage'
import {getPermissions} from '@/api/cloud/login'

NProgress.configure({showSpinner: false}) // NProgress Configuration

const whiteList = ['/login', '/auth-redirect'] // no redirect whitelist


let asyncRouter = []
router.beforeEach((to, from, next) => {
    NProgress.start()
    if (whiteList.indexOf(to.path) !== -1) {
        next()
    } else {
        const token = db.get('ACCESS_TOKEN')
        const user = db.get('USER')

        if (token.length && user) {
            if (asyncRouter.length == 0) {
                getPermissions().then((res) => {
                    const permissions = res.data.buttonCodes
                    store.commit('account/setPermissions', permissions)
                    asyncRouter = res.data.moduleList
                    store.commit('account/setRoutes', asyncRouter)
                    save('USER_ROUTER', asyncRouter)
                    go(to, next)
                })
            } else {
                next()
            }
        } else {
            if (to.path === '/login') {
                next()
            } else {
                next('/login')
                NProgress.done()
            }
        }
    }
})

function go(to, next) {
    asyncRouter = filterAsyncRouter(asyncRouter)
    router.addRoutes(asyncRouter)
    next({...to, replace: true})
}

function save(name, data) {
    localStorage.setItem(name, JSON.stringify(data))
}

function get(name) {
    return JSON.parse(localStorage.getItem(name))
}

function filterAsyncRouter(routes) {
    return routes.filter((route) => {
        const component = route.component
        if (component) {
            if (route.component === 'Layout') {
                route.component = Layout
            } else {
                route.component = view(component)
            }
            if (route.children && route.children.length) {
                route.children = filterAsyncRouter(route.children)
            }
            return true
        }
    })
}

function view(path) {
    return (resolve) => require([`@/views/${path}.vue`], resolve)
}

router.afterEach(() => {
    // finish progress bar
    NProgress.done()
})
