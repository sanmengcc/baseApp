import request from '@/utils/request'
import { post } from '@/utils/request'
import fileUtil from './file'
import axios from 'axios'

/**
 * 获取数据字典
 * @param dictTypes
 * @returns {*}
 */
export function getDict(dictTypes) {
  return request({
    url: '/gateway/api/dict/getDicMap?dictTypes=' + dictTypes,
    method: 'get'
  })
}

/**
 * 获取上传文件的签名
 * @param fileName
 * @param bucketPrivate
 * @param keepOrigName
 * @returns {Promise | Promise<unknown>}
 */
export function getUploadSignature(fileName, bucketPrivate, keepOrigName, md5, fileSize) {
  const params = {
    'fileName': fileName,
    'bucketPrivate': bucketPrivate,
    'keepOrigName': keepOrigName,
    'md5': md5,
    fileSize: fileSize
  }
  return post('/gateway/api/common/oss/getUploadSignature', params, true)
}

/**
 * 文件上传成功上报文件服务器
 * @param dataJson
 * @returns {Promise<unknown>}
 */
export function callbackFile(dataJson) {
  return post('/gateway/api/common/oss/callbackFile', dataJson, true)
}

/**
 * 文件上传
 * @param res
 * @returns {Promise<unknown>}
 */
export async function uploadFile(res) {
  const file = res.file
  // 文件进行MD5
  return new Promise((resolve, reject) => {
    fileUtil.md5File(file).then(md5 => {
      getUploadSignature(file.name, false, false, md5, file.size).then(res => {
        const exist = res.data.exist
        const url = res.data.url
        const data = {}
        if (exist) {
          // 秒传
          data.url = url
          data.code = '200'
          data.desc = '文件上传成功'
          resolve(data)
          return
        }
        const signature = res.data.signature
        data.code = '500'
        data.desc = '文件上传失败'
        let  headers = {
          'Content-Type': 'multipart/form-data'
        }
        if (file.name.endsWith('svg')) {
          headers = {
            'Content-Type': 'image/svg+xml'
          }
        }
        axios({
          method: 'put',
          url: signature,
          headers: headers,
          data: file
        }).then(response => {
          if (response.status === 200) {
            const dataJson = {
              fileName: file.name,
              fileSize: file.size,
              fileUrl: url,
              fileMd5: md5
            }
            callbackFile(dataJson)
            data.url = url
            data.code = '200'
            data.desc = '文件上传成功'
          }
          resolve(data)
        });
      })
    })
  })
}
