package io.github.mofa3.lang.jmh;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author
 */
@Data
public class CmsUser1 implements Serializable {
    private static final long serialVersionUID = 1711634898112238126L;
    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 密码
     */
    private String password;

    /**
     * 登录次数
     */
    private Long loginCount;

    /**
     * 登录错误次数
     */
    private Long errorCount;

    /**
     * 扩展信息
     */
    private String extInfo;

    /**
     * 是否启用（1.true 0.false）
     */
    private Boolean enable;

    /**
     * 创建者
     */
    private String cretor;

    /**
     * 修改者
     */
    private String lastModer;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

}