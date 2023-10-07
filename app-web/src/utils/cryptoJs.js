import CryptoJS from 'crypto-js'


// 默认的 KEY 与 iv 如果没有给
const KEY = '68dd2a6613ede433'
const IV = '68dd2a6613ede433'





export function encode(data,key,iv){
  var key = CryptoJS.enc.Utf8.parse(KEY);
  var iv = CryptoJS.enc.Utf8.parse(IV);
  var encrypted =CryptoJS.AES.encrypt(data,key,{
    iv:iv,
    mode:CryptoJS.mode.CBC,
    padding:CryptoJS.pad.Pkcs7
  });
  //返回的是base64格式的密文
  return encrypted;
}

// encrypted 为是base64格式的密文
export function decrypt(encrypted,key,iv){
  var key = CryptoJS.enc.Utf8.parse(KEY);
  var iv = CryptoJS.enc.Utf8.parse(IV);
  var decrypted =CryptoJS.AES.decrypt(encrypted,key,{
    iv:iv,
    mode:CryptoJS.mode.CBC,
    padding:CryptoJS.pad.Pkcs7
  });
  return decrypted.toString(CryptoJS.enc.Utf8).toString();
}
