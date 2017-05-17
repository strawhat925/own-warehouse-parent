package code.warehouse.controller;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import code.warehouse.common.cloud.CloudStorageFactory;
import code.warehouse.common.cloud.CloudStorageService;
import code.warehouse.common.entity.CloudStorageConfig;
import code.warehouse.common.entity.SysConfigEntity;
import code.warehouse.common.entity.SysFileEntity;
import code.warehouse.common.exception.CommonException;
import code.warehouse.common.exception.ValidationException;
import code.warehouse.common.utils.Code;
import code.warehouse.common.utils.Constants;
import code.warehouse.common.utils.Result;
import code.warehouse.common.validator.ValidatorUtils;
import code.warehouse.common.validator.group.AliyunGroup;
import code.warehouse.common.validator.group.QcloudGroup;
import code.warehouse.common.validator.group.QiniuGroup;
import code.warehouse.service.SysConfigService;
import code.warehouse.service.SysUploadService;

/**
 * 文件上传.
 * package code.warehouse.controller
 *
 * @author zli [liz@yyft.com]
 * @version v1.0
 * @create 2017-05-16 14:35
 **/
@SuppressWarnings("ALL")
@RestController
@RequestMapping("/sys/file")
public class SysUploadController extends AbstractController {

    @Autowired
    private SysUploadService sysUploadService;
    @Autowired
    private SysConfigService sysConfigService;


    /**
     * 列表
     *
     * @param params
     *
     * @return
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:upload:all")
    public String list(@RequestParam Map<String, Object> params) {
        //分页参数
        //当前页码
        int pageNum = Integer.parseInt(params.get("page").toString());
        //每页条数
        int pageSize = Integer.parseInt(params.get("limit").toString());
        Page<List<Map<String, Object>>> page = PageHelper.startPage(pageNum, pageSize);
        sysUploadService.queryList(params);
        return Result.newResult(Code.SUCCESS, new PageInfo(page)).toJson();
    }


    /**
     * 云存储配置信息
     *
     * @return
     */
    @RequestMapping("/config")
    @RequiresPermissions("sys:upload:all")
    public String config() {
        CloudStorageConfig cloudStorageConfig = sysConfigService.getConfigObject(CloudStorageConfig.CLOUD_STORAGE_CONFIG_KEY, CloudStorageConfig.class);

        return Result.newResult(Code.SUCCESS, cloudStorageConfig).toJson();
    }


    /**
     * 保存云存储配置信息
     *
     * @param cloudStorageConfig
     *
     * @return
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:upload:all")
    public String save(@RequestBody CloudStorageConfig cloudStorageConfig) {
        //校验类型
        ValidatorUtils.validateEntity(cloudStorageConfig);


        if (cloudStorageConfig.getType() == Constants.CloudService.QINIU.getValue()) {
            //校验七牛
            ValidatorUtils.validateEntity(cloudStorageConfig, QiniuGroup.class);
        } else if (cloudStorageConfig.getType() == Constants.CloudService.ALIYUN.getValue()) {
            //校验阿里云
            ValidatorUtils.validateEntity(cloudStorageConfig, AliyunGroup.class);
        } else if (cloudStorageConfig.getType() == Constants.CloudService.QCLOUD.getValue()) {
            //校验腾讯云
            ValidatorUtils.validateEntity(cloudStorageConfig, QcloudGroup.class);
        }

        String value = sysConfigService.getValue(CloudStorageConfig.CLOUD_STORAGE_CONFIG_KEY, null);
        if (StringUtils.isBlank(value)) {
            SysConfigEntity config = new SysConfigEntity();
            config.setKey(CloudStorageConfig.CLOUD_STORAGE_CONFIG_KEY);
            config.setValue(JSON.toJSONString(cloudStorageConfig));
            config.setRemark("云存储配置");
            sysConfigService.save(config);
        } else {
            sysConfigService.updateValueByKey(CloudStorageConfig.CLOUD_STORAGE_CONFIG_KEY, JSON.toJSONString(cloudStorageConfig));
        }
        return Result.SUCCESS;
    }


    /**
     * 上传文件到云存储
     *
     * @param file
     *
     * @return
     */
    @RequestMapping("/upload")
    @RequiresPermissions("sys:upload:all")
    public String upload(@RequestParam("file") MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new ValidationException("上传文件不能为空");
        }

        CloudStorageConfig config = sysConfigService.getConfigObject(CloudStorageConfig.CLOUD_STORAGE_CONFIG_KEY, CloudStorageConfig.class);
        CloudStorageService cloudStorageService = CloudStorageFactory.build(config);
        //上传文件到云存储
        String url = cloudStorageService.upload(file.getBytes());

        if (StringUtils.isBlank(url)) {
            throw new CommonException("上传失败");
        }
        //保存文件信息
        SysFileEntity sysFileEntity = new SysFileEntity();
        sysFileEntity.setUrl(url);
        sysFileEntity.setCreateDate(new Date());
        sysUploadService.save(sysFileEntity);

        return Result.newResult(Code.SUCCESS, url).toJson();
    }

    /**
     * 删除文件
     *
     * @param ids
     *
     * @return
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:upload:all")
    public String delete(@RequestBody Long[] ids) {
        //
        sysUploadService.deleteBatch(ids);

        //TODO 删除云存储
        return Result.SUCCESS;
    }
}
