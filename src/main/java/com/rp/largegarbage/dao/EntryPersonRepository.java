package com.rp.largegarbage.dao;

import com.rp.largegarbage.entity.EntryPerson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @program: largegarbage
 * @description: 发起人模型
 * @author: lld
 * @create: 2020-06-19 10:08
 **/

public interface EntryPersonRepository extends PagingAndSortingRepository<EntryPerson, Long>, JpaSpecificationExecutor<EntryPerson> {
        /**
         * 设置 nativeQuery=true 即可以使用原生的 SQL 查询
         */
       /* @Query(value = "SELECT COUNT(id) FROM sys_entry_person" , nativeQuery = true)
        int getTotalCount();*/
    /**
     * 原生SQL实现更新方法接口
     */
   /* @Modifying
    @Query(value = "update notice set notice_desc = :name where id = :id ", nativeQuery = true)
    void updateOne(String noticeDesc,int id);*/
        /**
         * @Query 注解update、delete操作，不支持insert
         *//*
        @Modifying
        @Query("UPDATE EntryPerson u SET u.age = :age")
        @Transactional
        void updateEntryPersonAge(@Param("age") Integer age);
        *//**
         * 查询id 值最大的那个EntryPerson 使用@Query 主键可以自定义JPQL语句以实现更灵活的查询
         *//*
        @Query("SELECT u FROM EntryPerson u WHERE u.id = (SELECT MAX(p.id) FROM EntryPerson p)")
        EntryPerson getMaxIdEntryPerson();

        *//**
         * @Query 注解传递参数的方式一：占位符方式
         *//*
        @Query("SELECT u FROM EntryPerson u WHERE u.EntryPersonname = ?1 AND u.age = ?2")
        List<EntryPerson> testQueryAnnotationEntryPerson1(String EntryPersonname , Integer age);

        *//**
         * @Query 注解传递参数的方式二：命名参数方式
         *//*
        @Query("SELECT u FROM EntryPerson u WHERE u.EntryPersonname = :EntryPersonname AND u.age = :age")
        List<EntryPerson> testQueryAnnotationEntryPerson2(@Param("EntryPersonname") String EntryPersonname , @Param("age")Integer age);

        *//**
         * 根据EntryPersonname来获取对应的EntryPerson
         *//*
        EntryPerson getByEntryPersonname(String EntryPersonname);

        *//**
         * WHERE EntryPersonname LIKE %?
         *//*
        List<EntryPerson> findByEntryPersonnameStartingWith(String EntryPersonname);

        *//**
         * WHERE EntryPersonname LIKE ?%
         *//*
        List<EntryPerson> findByEntryPersonnameEndingWith(String EntryPersonname);

        *//**
         * WHERE EntryPersonname id < ?
         *//*
        List<EntryPerson> findByIdLessThan(Long id);*/
    }
