package com.github.downgoon.jresty.data.orm.dao.example;

import java.util.Date;

import com.github.downgoon.jresty.data.orm.annotation.ORMField;
import com.github.downgoon.jresty.data.orm.annotation.ORMTable;

/**
 * @title Material
 * @description 使用样例之POJO： 视频物料实体类
 * @author liwei39
 * @date 2014年6月17日
 * @version 1.0
 */
@ORMTable(name = "FC_Video.material")
public class Material {

    public static final String AUDIT_STATUS = "auditStatus";

    /** 视频时长保留的小数位数 */
    public static final int DURATION_DECIMALS = 2;

    /** 物料内审通过 */
    public static final int AUDIT_STATUS_OK = 0;
    /** 等待物料内审 */
    public static final int AUDIT_STATUS_WAIT = 1;
    /** 物料内审未通过 */
    public static final int AUDIT_STATUS_UNPASS = 2;

    private Long materialId;

    /** 广告主ID */
    private Long userId;

    /** 物料名称：UserId + 物料名称 必须唯一 */
    private String materialName;

    /** 物料原始URL地址：广告主上传到BCS的原始地址（非转码后的地址） */
    private String rawUrl;

    /**
     * 物料视频文件的MD5值（FE生成发送给业务端服务器的） UserId + MD5 必须唯一
     * */
    private String materialMD5;

    /** 原始物料文件的格式 ：mov,mp4 */
    private String fileFormat;

    /** '物料时长' 单位：秒 */
    private Double duration;

    /** '物料宽' */
    private Integer width;

    /** '物料高' */
    private Integer height;

    /** '码率' (总码率，包括音频和视频 ) 单位： bps */
    private Integer bitrate;

    /** 帧率（29.97） */
    private Double frameRate;

    /** '缩略图地址' */
    private String thumbnail;

    /** '预览物料地址' */
    private String previewUrl;

    /**
     * '机审内部状态 0 流程成功结束 1 初始值 10|12 META提取成功|失败 20|22 规范审核成功|失败 30|32 转码成功|失败',
     * */
    private Integer robotMinorStatus;

    /**
     * '机审外用状态 0 满足内审条件 1 初始值 2 无法提交内审'
     * */
    private Integer robotMajorStatus;

    /**
     * '机审时间时间'
     * */
    private Date robotMajorTime;

    /** '内审状态 0 审核通过 1 待审 2 审核未通过', */
    private Integer auditStatus;

    /** 审核未通过原因 */
    private Integer unpassReason;

    /** 审核未通过其他原因 */
    private String unpassOtherReason;

    /** '内审时间' */
    private Date auditTime;

    /** '内审人员ID' */
    private Integer autitor;

    private Date createTime;

    private Date updateTime;

    /** 删除标记： '0 正常, 1 删除' */
    private Integer isDel;

    /** 物料上传来源 */
    private Integer referer;

    @ORMField(name = "materialid", isKey = true, isAutoIncrement = true)
    public Long getMaterialId() {
        return materialId;
    }

    public void setMaterialId(Long materialId) {
        this.materialId = materialId;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    /** 物料原始URL地址：广告主上传到BCS的原始地址（非转码后的地址） */
    public String getRawUrl() {
        return rawUrl;
    }

    public void setRawUrl(String url) {
        this.rawUrl = url;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    @ORMField(name = "videobitrate")
    public Integer getBitrate() {
        return bitrate;
    }

    public void setBitrate(Integer bitrate) {
        this.bitrate = bitrate;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getPreviewUrl() {
        return previewUrl;
    }

    public void setPreviewUrl(String previewUrl) {
        this.previewUrl = previewUrl;
    }

    public Integer getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(Integer auditStatus) {
        this.auditStatus = auditStatus;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @ORMField(name = "materialid", isSkip = true)
    public Long getId() {
        return materialId;
    }

    @ORMField(name = "userid", isKey = false)
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getMaterialMD5() {
        return materialMD5;
    }

    public void setMaterialMD5(String materialMD5) {
        this.materialMD5 = materialMD5;
    }

    public Integer getUnpassReason() {
        return unpassReason;
    }

    public void setUnpassReason(Integer unpassReason) {
        this.unpassReason = unpassReason;
    }

    public String getUnpassOtherReason() {
        return unpassOtherReason;
    }

    public void setUnpassOtherReason(String unpassOtherReason) {
        this.unpassOtherReason = unpassOtherReason;
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

    public Double getDuration() {
        return duration;
    }

    public void setDuration(Double duration) {
        this.duration = duration;
    }

    public String getFileFormat() {
        return fileFormat;
    }

    public void setFileFormat(String fileFormat) {
        this.fileFormat = fileFormat;
    }

    public Double getFrameRate() {
        return frameRate;
    }

    public void setFrameRate(Double frameRate) {
        this.frameRate = frameRate;
    }

    public Integer getRobotMinorStatus() {
        return robotMinorStatus;
    }

    public void setRobotMinorStatus(Integer robotMinorStatus) {
        this.robotMinorStatus = robotMinorStatus;
    }

    public Integer getRobotMajorStatus() {
        return robotMajorStatus;
    }

    public void setRobotMajorStatus(Integer robotMajorStatus) {
        this.robotMajorStatus = robotMajorStatus;
    }

    public Date getRobotMajorTime() {
        return robotMajorTime;
    }

    public void setRobotMajorTime(Date robotMajorTime) {
        this.robotMajorTime = robotMajorTime;
    }

    public Date getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(Date auditTime) {
        this.auditTime = auditTime;
    }

    @ORMField(name = "auditor")
    public Integer getAutitor() {
        return autitor;
    }

    public void setAutitor(Integer autitor) {
        this.autitor = autitor;
    }

    public Integer getReferer() {
        return referer;
    }

    public void setReferer(Integer referer) {
        this.referer = referer;
    }

    @Override
    public String toString() {
        return "Material [materialId=" + materialId + ", userId=" + userId + ", materialName=" + materialName
                + ", rawUrl=" + rawUrl + ", materialMD5=" + materialMD5 + ", fileFormat=" + fileFormat + ", duration="
                + duration + ", width=" + width + ", height=" + height + ", bitrate=" + bitrate + ", frameRate="
                + frameRate + ", thumbnail=" + thumbnail + ", previewUrl=" + previewUrl + ", robotMinorStatus="
                + robotMinorStatus + ", robotMajorStatus=" + robotMajorStatus + ", robotMajorTime=" + robotMajorTime
                + ", auditStatus=" + auditStatus + ", unpassReason=" + unpassReason + ", unpassOtherReason="
                + unpassOtherReason + ", auditTime=" + auditTime + ", autitor=" + autitor + ", createTime="
                + createTime + ", updateTime=" + updateTime + ", isDel=" + isDel + ", referer=" + referer + "]";
    }

}
