import {ossConfig} from './config';
import co from 'co';
import OSS from 'ali-oss';

const client = new OSS(ossConfig);

const uploadFileToOSS = (file, fileName = '', callBack, progressCB = null) => {
    co(function * () {
        let name = fileName === '' ? file.name : fileName;
        yield client.multipartUpload(name, file, {
            progress: function * (percentage) {
                if (progressCB !== null) { progressCB(percentage * 100); }
            }
        });
        // 上传完成
        let response = yield client.head(name);
        callBack(response);
    }).catch(() => {
    });
};

export default uploadFileToOSS;
