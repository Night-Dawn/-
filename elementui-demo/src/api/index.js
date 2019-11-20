import Axios from '../libs/AxiosPlugin'

// 接口地址
const BASE_PATH = process.env.API_URL

// 用户登录
export const login = params => { return Axios.post(`${BASE_PATH}/user/login`, params).then(res => res.data); };

// 获取用户列表
export const getUserList = params => { return Axios.get(`${BASE_PATH}/user/list`, { params: params }).then(res => res.data); };

//获取图书列表
export const getBookList = params => { return Axios.get(`${BASE_PATH}/book/list`, { params: params }).then(res => res.data); };

// 首页加载数据
export const loadData = params => { return Axios.get(`${BASE_PATH}/home/loadData`).then(res => res.data) };

// 文件上传地址
export const uploadFile = `${BASE_PATH}/file/upload`;

//获取书籍详细信息
export const getBookDetails = params => { return Axios.get(`${BASE_PATH}/book/detail`, { params: params }).then(res => res.data); };

//添加书籍
export const addBook = params => { return Axios.post(`${BASE_PATH}/book/add`, params).then(res => res.data); };

//更新书籍
export const updateBook = params => { return Axios.post(`${BASE_PATH}/book/update`, params).then(res => res.data); };

//删除书籍
export const deleteBook = params => { return Axios.get(`${BASE_PATH}/book/delete`, { params: params }).then(res => res.data); };

//获取借阅列表
export const getBorrowList = params => { return Axios.get(`${BASE_PATH}/borrow/list`, { params: params }).then(res => res.data); };

//更新借阅日志
export const updateBorrow = params => { return Axios.post(`${BASE_PATH}/borrow/update`, params).then(res => res.data); };

//删除书籍
export const deleteBorrow = params => { return Axios.get(`${BASE_PATH}/borrow/delete`, { params: params }).then(res => res.data); };

//检查用户名是否存在
export const checkUser = params => { return Axios.get(`${BASE_PATH}/user/check`, { params: params }).then(res => res.data); };

//添加用户
export const addUser = params => { return Axios.post(`${BASE_PATH}/user/add`, params).then(res => res.data); };

//更新用户
export const updateUser = params => { return Axios.post(`${BASE_PATH}/user/update`, params).then(res => res.data); };

//删除用户
export const deleteUser= params => { return Axios.get(`${BASE_PATH}/user/delete`, { params: params }).then(res => res.data); };

//获取用户书籍信息
export const getUserbook = params => { return Axios.get(`${BASE_PATH}/book/book`, { params: params }).then(res => res.data); };

//判断用户是否借阅书籍
export const isBorrow = params => { return Axios.get(`${BASE_PATH}/book/isborrow`, { params: params }).then(res => res.data); };

//借阅图书
export const insertBorrow = params => { return Axios.post(`${BASE_PATH}/borrow/insert`, params).then(res => res.data); };

//退订图书
export const deleteborrow = params => { return Axios.get(`${BASE_PATH}/borrow/deleteborrow`, { params: params }).then(res => res.data); };

//获取用户借阅图书
export const getborrowBook = params => { return Axios.get(`${BASE_PATH}/borrow/borrowbook`, { params: params }).then(res => res.data); };
