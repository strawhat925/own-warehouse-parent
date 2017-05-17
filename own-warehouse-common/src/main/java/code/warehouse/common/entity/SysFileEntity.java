package code.warehouse.common.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 文件上传.
 * package code.warehouse.common.entity
 *
 * @author zli [liz@yyft.com]
 * @version v1.0
 * @create 2017-05-16 14:39
 **/
public class SysFileEntity implements Serializable {
    private static final long serialVersionUID = -1330304573132247112L;

    private Long id;
    /**
     * URL地址
     */
    private String url;
    /**
     * 创建时间
     */
    private Date createDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
