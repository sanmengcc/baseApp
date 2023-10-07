const domain = function(param) {
  if (process.env.NODE_ENV === 'test') {
    return param.test
  }
  if (process.env.NODE_ENV === 'dev') {
    return param.dev
  }
  if (process.env.NODE_ENV === 'prod') {
    return param.prod
  }
}

module.exports = {
  '/gateway': {
    target: domain({
      dev: 'http://127.0.0.1:9600',
      test: 'http://124.70.77.178:81',
      prod: 'http://gateway.popccc.cc:81'
    }),
    pathRewrite: {
      "^/gateway": "/",//重写地址
    },
  }
}
