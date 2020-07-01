package com.rp.largegarbage.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @Description
 * @Author liulida <2979284403@qq.com>
 * @Version v1.0.0
 * @Since 1.0
 * @Date 2020/6/24 16:19
 */
@Data
@Entity
@Table(name="file_info")
public class FileInfo extends BaseEntity implements Serializable{
    //编号
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "file_id", nullable = false, columnDefinition = "BIGINT UNSIGNED")
    private Integer fileId;
    //文件名
    @Column(name = "file_name",unique = true,nullable = false,columnDefinition = "varchar(50) COMMENT '文件名称'")
    private String fileName;
    //文件路径
    @Column(name = "file_path",unique = false,nullable = false,columnDefinition = "varchar(255) COMMENT '文件路径'")
    private String filePath;
    //文件大小
    @Column(name = "file_size",unique = false,nullable = false,columnDefinition = "varchar(100) COMMENT '文件大小'")
    private Long fileSize;

    public Integer getFileId() {
        return fileId;
    }

    public void setFileId(Integer fileId) {
        this.fileId = fileId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }
}
