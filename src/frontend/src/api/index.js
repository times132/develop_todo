import axios from 'axios'
import store from '../vuex'

const instance = axios.create({
    baseURL: 'http://localhost:9000',
    withCredentials: true,
})

instance.interceptors.request.use(
    function (config) {
        config.headers.post['Content-Type'] = 'application/json'
        config.headers.put['Content-Type'] = 'application/json'
        return config
    },
    function (error) {
        return Promise.reject(error)
    }
)

instance.interceptors.response.use(
    function (response) {
        return response
    },
    function (error) {
        if (error.response.status === 401){
            console.log(error.response)
            if (error.response.data.message !== 'login error'){
                store.dispatch("logout")
            }
        }
        return Promise.reject(error)
    }
)

// 상품
function getDetail(subUrl, id) {
    return instance({
        url: subUrl + '/' + id,
        method: 'get'
    })
}

function writeGoods(goodsData) {
    return instance({
        url: '/api/goods',
        method: 'post',
        data: goodsData
    })
}

function getGoodsList(categoryNum, criteria) {
    return instance({
        url: '/api/goods/' + categoryNum,
        method: 'get',
        params: {
            page: criteria.page,
            pageSize: criteria.pageSize,
            type: criteria.type,
            keyword: criteria.keyword
        }
    })
}

function updateGoods(goodsData, gid) {
    return instance({
        url: '/api/goods/' + gid,
        method: 'put',
        data: goodsData
    })
}

function deleteGoods(gid) {
    return instance({
        url: '/api/goods/' + gid,
        method: 'delete'
    })
}

function displayGoodsImage(imageName) {
    console.log(imageName)
    return instance({
        url: '/api/display',
        method: 'get',
        params: {
            imageName: imageName
        }
    })
}

function uploadGoodsImage(file) {
    return instance({
        url: '/api/upload/goods',
        method: 'post',
        data: file,
    })
}

// 회원
function userSignup(signupData) {
    return instance({
        url: '/user/signup',
        method: 'post',
        data: signupData
    })
}

function userLogin(userData) {
    return instance({
        url: '/user/login',
        method: 'post',
        data: userData
    })
}

function userLogout() {
    return instance({
        url: '/user/logout',
        method: 'post'
    })
}

function getUserInfo() {
    return instance({
        url: '/user/me',
        method: 'get'
    })
}

export {
    getDetail,
    writeGoods,
    getGoodsList,
    updateGoods,
    deleteGoods,
    displayGoodsImage,
    uploadGoodsImage,
    userSignup,
    userLogin,
    userLogout,
    getUserInfo
}