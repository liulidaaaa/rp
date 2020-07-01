package com.rp.largegarbage.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/** 通知公告
 * @Description
 * @Author liulida <2979284403@qq.com>
 * @Version v1.0.0
 * @Since 1.0
 * @Date 2020/6/29 18:09
 */
@Data
@Entity
@Table(name="notice")
public class Notice  extends BaseEntity implements Serializable {
    //编号
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "notice_id", nullable = false, columnDefinition = "BIGINT UNSIGNED")
    private Integer noticeId ;
    //公告标题
    @Column(name = "title",nullable = false,columnDefinition = "varchar(127) COMMENT '公告标题'")
    private String title;
    //公告作者
    @Column(name = "author",nullable = false,columnDefinition = "varchar(63) COMMENT '公告作者'")
    private String author;
    //公告内容
    @Column(name = "content",nullable = false,columnDefinition = "longtext COMMENT '公告内容'")
    private String content;
    //浏览量
    @Column(name = "pv",columnDefinition = "int(32) COMMENT '浏览量'")
    private Integer pv;
}
