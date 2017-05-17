package code.warehouse.common.cloud;

import code.warehouse.common.entity.CloudStorageConfig;
import code.warehouse.common.utils.Constants;

/**
 * 云存储工厂.
 * package code.warehouse.common.cloud
 *
 * @author zli [liz@yyft.com]
 * @version v1.0
 * @create 2017-05-16 16:48
 **/
public class CloudStorageFactory {


    public static CloudStorageService build(CloudStorageConfig config) {
        if (config.getType() == Constants.CloudService.QINIU.getValue()) {
            return new QiniuCloudStorageService(config);
        } else if (config.getType() == Constants.CloudService.ALIYUN.getValue()) {
            return new AliyunCloudStorageService(config);
        } else if (config.getType() == Constants.CloudService.QCLOUD.getValue()) {
            return new QcCloudStorageService(config);
        }

        return null;
    }

}
