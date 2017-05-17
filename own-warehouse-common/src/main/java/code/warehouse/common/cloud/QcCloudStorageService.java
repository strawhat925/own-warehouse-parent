package code.warehouse.common.cloud;

import java.io.InputStream;

import code.warehouse.common.entity.CloudStorageConfig;

/**
 * 腾讯云存储.
 * package code.warehouse.common.cloud
 *
 * @author zli [liz@yyft.com]
 * @version v1.0
 * @create 2017-05-16 16:48
 **/
public class QcCloudStorageService extends CloudStorageService {


    public QcCloudStorageService(CloudStorageConfig config){
        this.config = config;
    }

    @Override
    public String upload(byte[] data, String path) {
        return null;
    }

    @Override
    public String upload(byte[] data) {
        return null;
    }

    @Override
    public String upload(InputStream inputStream, String path) {
        return null;
    }

    @Override
    public String upload(InputStream inputStream) {
        return null;
    }
}
