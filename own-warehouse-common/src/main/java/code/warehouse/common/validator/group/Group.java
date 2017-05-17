package code.warehouse.common.validator.group;

import javax.validation.GroupSequence;

/**
 * 定义校验顺序，如果AddGroup组失败，则UpdateGroup组不会再校验
 * package code.warehouse.common.validator.group
 *
 * @author zli [liz@yyft.com]
 * @version v1.0
 * @create 2017-05-15 15:37
 **/
@GroupSequence({ AddGroup.class, UpdateGroup.class })
public interface Group {
}
