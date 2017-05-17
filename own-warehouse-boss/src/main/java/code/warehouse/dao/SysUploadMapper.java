package code.warehouse.dao;

import org.apache.ibatis.annotations.Mapper;

import code.warehouse.common.dao.BaseMapper;
import code.warehouse.common.entity.SysFileEntity;

/**
 * 文件上传持久化.
 * package code.warehouse.dao
 *
 * @author zli [liz@yyft.com]
 * @version v1.0
 * @create 2017-05-16 14:41
 **/
@Mapper
public interface SysUploadMapper extends BaseMapper<SysFileEntity> {
}
