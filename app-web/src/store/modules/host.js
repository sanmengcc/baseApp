import db from '@/utils/localstorage'

export default {
  namespaced: true,
  state: {
    hostConfig: db.get('HOST_CONFIG')
  },
  mutations: {
    setHostConfig(state,val){
      db.save('HOST_CONFIG', val)
      state.hostConfig = val
    },
  }
}
