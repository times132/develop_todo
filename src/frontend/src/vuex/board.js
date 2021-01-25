const state = {
    criteria: {}
}

const mutations = {
    saveCriteria(state, value) {
        state.criteria = value
    }
}

const actions = {
    setCriteria( {commit}, criteria) {
        commit('saveCriteria', criteria)
    }
}

const getters = {
    getCriteria: function (state) {
        return state.criteria
    }
}

export default {
    state,
    mutations,
    actions,
    getters
}