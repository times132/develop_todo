import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home'
import Login from '../views/Login'
import Me from '../views/Profile'
import Write from '../views/Goods/Write'
import Modify from '../views/Goods/Modify'
import Detail from '../views/Goods/Detail'
import List from '../views/Goods/List'
import store from '../vuex'
import Signup from '../views/Signup'
import Free from '../views/Free'

Vue.use(VueRouter)

const requireAuth = (from, to, next) => {
    if (store.state.login.isAuthenticated){ // 로그인 되있을 때
        return next()
    } else { // 로그인 안 돼있을 때
        next({
            path: '/login',
            query: { redirect: from.fullPath }
        })
    }
}

const routes = [
    {
        path: '/',
        name: 'home',
        component: Home
    },
    {
        path: '/login',
        name: 'login',
        component: Login
    },
    {
        path: '/me',
        name: 'me',
        component: Me,
        beforeEnter: requireAuth
    },
    {
        path: '/goods/write',
        name: 'write',
        component: Write,
        beforeEnter: requireAuth
    },
    {
        path: '/goods/modify/:gid',
        name: 'modify',
        component: Modify,
        beforeEnter: requireAuth
    },
    {
        path: '/goods/:categoryNum',
        name: 'goods',
        component: List,
        props: true
    },
    {
        path: '/goods/:categoryNum/:gid',
        name: 'detail',
        component: Detail,
        props: true
    },
    {
        path: '/signup',
        name: 'signup',
        component: Signup
    },
    {
        path: '/free',
        name: 'free',
        component: Free
    }
    // {
    //     path: '/display',
    //     name: 'display'
    // }
]

const router = new VueRouter({
    mode: 'history',
    base: process.env.BASE_URL,
    routes
})

export default router