package couchbase.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import couchbase.standard.CodeConductStandard;

import java.io.Serializable;
import java.util.Date;

/**
 * 所有业务实体基类--因为是文档型数据库所以把最终的实体叫做文档
 */
public class Document implements Serializable {
    /**
     * 数据库唯一标识ID
     */
    public String Id;

    /**
     * 存入数据实体类型
     */
    public String ClassTypeName = this.getClass().getName();

    /**
     * 创建时间
     */
    @JsonFormat(pattern = CodeConductStandard.Document_DateFormat, timezone = CodeConductStandard.Document_TimeZone)
    public Date CreateTime;

    /**
     * 创建人ID
     */
    public String CreateManId;

    /**
     * 最后使用时间
     */
    @JsonFormat(pattern = CodeConductStandard.Document_DateFormat, timezone = CodeConductStandard.Document_TimeZone)
    public Date UpdateTime;

    /**
     * 修改人ID
     */
    public String UpdateManId;

    /**
     * 数据有效性
     */
    public Integer IsValidity;
}
