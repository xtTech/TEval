let _loginPage = '';
let _baseURL = '';
let _ossConfig = {};

switch (process.env.NODE_ENV) {
    case 'test': // 测试环境
        _loginPage = 'http://uat-ucenter.tairancloud.com/?appid=uc9939087f3a5506d6';
        _baseURL = 'http://app.fengdai.org/manager';
        _ossConfig = {
            region: 'oss-cn-shanghai',
            accessKeyId: '00bZUTBrUxY8Lf4f',
            accessKeySecret: 'GqlkhcdNagIUTd2ldY3UQfpXS4fZPt',
            bucket: 'yetao-test'
        };
        break;
    case 'uat': // 预发环境
        _loginPage = 'https://ucenter.trc.com?appid=ucb0ed4012a0d6ff14';
        _baseURL = 'https://appmanager.trc.com/api/manager';
        _ossConfig = {
            region: 'oss-cn-beijing',
            accessKeyId: '00bZUTBrUxY8Lf4f',
            accessKeySecret: 'GqlkhcdNagIUTd2ldY3UQfpXS4fZPt',
            bucket: 'xt-app'
        };
        break;
    case 'production': // 正式环境
        _loginPage = 'https://ucenter.trc.com/?appid=ucb0ed4012a0d6ff14';
        _baseURL = 'https://appmanager.trc.com/api/manager';
        _ossConfig = {
            region: 'oss-cn-beijing',
            accessKeyId: '00bZUTBrUxY8Lf4f',
            accessKeySecret: 'GqlkhcdNagIUTd2ldY3UQfpXS4fZPt',
            bucket: 'xt-app'
        };
        break;
    default: // 开发模式
        _loginPage = 'http://ucenter.fengdai.org?appid=ucccc1d645f088febd';
        _baseURL = 'http://10.200.4.213:8086';
        _ossConfig = {
            region: 'oss-cn-shanghai',
            accessKeyId: '00bZUTBrUxY8Lf4f',
            accessKeySecret: 'GqlkhcdNagIUTd2ldY3UQfpXS4fZPt',
            bucket: 'yetao-test'
        };
        break;
}

export const loginPage = _loginPage;
export const baseURL = _baseURL;
export const ossConfig = _ossConfig;

export default {
    loginPage,
    baseURL,
    ossConfig
};
