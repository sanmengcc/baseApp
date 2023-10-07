const db = {
  save(key, value) {
    localStorage.setItem(key, JSON.stringify(value))
  },
  get(key, defaultValue = {}) {
    try{
      return JSON.parse(localStorage.getItem(key)) || defaultValue
    }catch (e) {
      return null
    }
  },
  remove(key) {
    localStorage.removeItem(key)
  },
  clear() {
    localStorage.clear()
  }
}

export default db
