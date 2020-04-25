// 引入mock
const Mock = require('mockjs')

const produceTokens = function () {
  let token = 'test'
  let student = {
    'ticket-number': '9876',
    'name': '张三',
    'dgree-type': '学术学位',
    'department': '计算机科学技术学院',
    'major': '计算机系统结构',
    'direction': '分布式存储系统与网络',
    'id-number': '12345',
    'first-choice-major': '计算机系统结构',
    'first-choice-direction': '分布式存储系统与网络',
    'second-choice-major': '',
    'second-choice-direction': '',
    'adjust-major': false,
    'adjust-degree-type': false,
    'last-modify-time': ''
  }
  return {
    'token': token,
    'student': student
  }
}

// 登录的拦截请求，配置数据
Mock.mock('/api/student/login', 'post', produceTokens)

// 志愿填报情况
const choiceOverview = function () {
  let overview = []
  overview.push({
    'major': '计算机系统结构',
    'spots': '40',
    'first-choice-major-numbers': '0'
  })
  overview.push({
    'major': '计算机软件与理论',
    'spots': '50',
    'first-choice-major-numbers': '0'
  })
  return {
    overview
  }
}

Mock.mock('/api/student/choices-overview', 'get', choiceOverview)

// 招生目录
const majorInfo = function () {
  let resp = []
  resp.push({
    'degree-type': '学术学位',
    'major': '计算机系统结构',
    'direction': '下一代网络体系结构',
    'display': '计算机系统结构－下一代网络体系结构',
    'spots': '40'
  })
  resp.push({
    'degree-type': '专业学位',
    'major': '计算机科学与技术',
    'direction': '智能媒体计算',
    'display': '计算机科学与技术－智能媒体计算',
    'spots': '30'
  })
  resp.push({
    'degree-type': '专业学位',
    'major': '人类表型组',
    'direction': '',
    'display': '人类表型组',
    'spots': '30'
  })
  return {
    resp
  }
}
Mock.mock('/api/student/majors', 'get', majorInfo)

// 文件目录
const fileInfo = function () {
  let resp = []
  resp.push({
    'id': '1',
    'name': 'test.txt'
  })
  resp.push({
    'id': '2',
    'name': 'test2.txt'
  })
  return {
    resp
  }
}
Mock.mock('/api/student/files', 'get', fileInfo)
