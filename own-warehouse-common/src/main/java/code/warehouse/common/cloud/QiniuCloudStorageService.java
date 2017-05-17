package code.warehouse.common.cloud;

import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;

import code.warehouse.common.entity.CloudStorageConfig;
import code.warehouse.common.exception.CommonException;

/**
 * 七牛云存储.
 * package code.warehouse.common.cloud
 *
 * @author zli [liz@yyft.com]
 * @version v1.0
 * @create 2017-05-16 16:31
 **/
public class QiniuCloudStorageService extends CloudStorageService {
    private UploadManager uploadManager;
    private String token;

    public QiniuCloudStorageService(CloudStorageConfig config) {
        this.config = config;

        //初始化
        init();
    }

    private void init() {
        uploadManager = new UploadManager(new Configuration(Zone.autoZone()));
        token = Auth.create(config.getQiniuAccessKey(), config.getQiniuSecretKey()).uploadToken(config.getQiniuBucketName());
    }

    @Override
    public String upload(byte[] data, String path) {

        try {
            Response response = uploadManager.put(data, path, token);
            if (!response.isOK()) {
                throw new CommonException("上传七牛出错：" + response.toString());
            }
        } catch (QiniuException e) {
            throw new CommonException("上传文件失败，请核对七牛配置信息", e);
        }

        return config.getQiniuDomain() + "/" + path;
    }

    @Override
    public String upload(byte[] data) {

        return this.upload(data, getPath(config.getQiniuPrefix()));
    }

    @Override
    public String upload(InputStream inputStream, String path) {
        try {
            byte[] data = IOUtils.toByteArray(inputStream);
            return this.upload(data, path);
        } catch (IOException e) {
            throw new CommonException("上传文件失败", e);
        }
    }

    @Override
    public String upload(InputStream inputStream) {

        return this.upload(inputStream, getPath(config.getQiniuPrefix()));
    }


}
