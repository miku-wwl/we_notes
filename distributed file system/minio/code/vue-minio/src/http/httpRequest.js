import axios from "axios";

//后端java的http接口地址前缀
axios.defaults.baseURL = "http://localhost:8080"

/**
 * axios发送get请求
 *
 * @param url --->  '/api/user'
 * @param params --->  {
 *             ID: 12345
 *         }
 */
export function doGet(url, params) {
    // 上述请求也可以按以下方式完成（可选）
    return axios.get(url, {
        params: params
    })
}

/**
 * axios发送delete请求
 *
 * @param url --->  '/api/user'
 * @param params --->  {
 *             ID: 12345
 *         }
 */
export function doDelete(url, params) {
    // 上述请求也可以按以下方式完成（可选）
    return axios.delete(url, {
        params: params
    })
}

/**
 * axios发送post请求
 *
 * @param url ---> '/api/user'
 * @param data ---> {
 *         firstName: 'Fred',
 *         lastName: 'Flintstone'
 *     }
 */
export function doPost(url, data) {
    return axios.post(url, data);
}

/**
 * axios发送put请求
 *
 * @param url ---> '/api/user'
 * @param data ---> {
 *         firstName: 'Fred',
 *         lastName: 'Flintstone'
 *     }
 */
export function doPut(url, data) {
    return axios.put(url, data);
}