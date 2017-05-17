package code.warehouse.common.cloud;

import java.io.InputStream;

import code.warehouse.common.entity.CloudStorageConfig;

/**
 * 阿里云存储.
 * package code.warehouse.common.cloud
 *
 * @author zli [liz@yyft.com]
 * @version v1.0
 * @create 2017-05-16 16:47
 **/
public class AliyunCloudStorageService extends CloudStorageService {

    public AliyunCloudStorageService(CloudStorageConfig config) {
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
