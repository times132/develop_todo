import Vue from 'vue'
import Vuex from 'vuex'
import SecureLS from 'secure-ls'
import LoginStore from './login'
import BoardStore from './board'
import createPersistedState from "vuex-persistedstate";

Vue.use(Vuex)

const ls = new SecureLS({ isCompression: true })

export default new Vuex.Store({
    state: {
    },
    modules: {
        login: LoginStore,
        board: BoardStore
    },
    plugins:  [
        createPersistedState({
            paths: ['login', 'board'],
            storage: {
                getItem: (key) => ls.get(key),
                setItem: (key, value) => ls.set(key, value),
                removeItem: (key) => ls.remove(key)
            }
        })
    ]
})