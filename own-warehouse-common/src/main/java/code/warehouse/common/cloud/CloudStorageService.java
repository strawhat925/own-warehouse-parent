package code.warehouse.common.cloud;

import org.apache.commons.lang3.StringUtils;

import java.io.InputStream;
import java.util.Date;
import java.util.UUID;

import code.warehouse.common.entity.CloudStorageConfig;
import code.warehouse.common.utils.DateUtils;

/**
 * 云存储.
 * package code.warehouse.common.cloud
 *
 * @author zli [liz@yyft.com]
 * @version v1.0
 * @create 2017-05-16 16:32
 **/
public abstract class CloudStorageService {

    /**
     * 云存储配置信息
     */
    CloudStorageConfig config;

    public String getPath(String prefix) {
        //
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");

        String path = DateUtils.format(new Date(), "yyyyMMdd") + "/" + uuid;
        if (StringUtils.isNotBlank(prefix)) {
            path = prefix + "/" + path;
        }

        return path;
    }


    /**
     * 文件上传
     *
     * @param data
     *         文件字节数组
     * @param path
     *         文件路径，包含文件名
     *
     * @return 返回http地址
     */
    public abstract String upload(byte[] data, String path);

    /**
     * 文件上传
     *
     * @param data
     *         文件字节数组
     *
     * @return 返回http地址
     */
    public abstract String upload(byte[] data);

    /**
     * 文件上传
     *
     * @param inputStream
     *         字节流
     * @param path
     *         文件路径，包含文件名
     *
     * @return 返回http地址
     */
    public abstract String upload(InputStream inputStream, String path);

    /**
     * 文件上传
     *
     * @param inputStream
     *         字节流
     *
     * @return 返回http地址
     */
    public abstract String upload(InputStream inputStream);


}
