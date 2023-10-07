import CryptoJs from 'crypto-js'

/**
 * 文件MD5加密
 */
export default {
  md5File(file) {
    return new Promise(resolve => {
      const fileReader = new FileReader()
      fileReader.onloadend = ev => {
        resolve(
          CryptoJs.MD5(CryptoJs.enc.Latin1.parse(ev.target.result)).toString(
            CryptoJs.enc.Hex
          )
        )
      }
      fileReader.readAsBinaryString(file)
    })
  }
}
