package com.rp.largegarbage.service;

import com.rp.largegarbage.dto.ResponseDTO;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Description
 * @Author liulida <2979284403@qq.com>
 * @Version v1.0.0
 * @Since 1.0
 * @Date 2020/6/30 13:51
 */
public interface FileService {
    /**
     * 单文件上传
     * @param file
     * @return
     * @throws Exception
     */
    ResponseDTO upload(MultipartFile file) throws Exception;

    /**
     * 批量上传
     * @param files
     * @return
     * @throws Exception
     */
    ResponseDTO batchUpload(MultipartFile[] files) throws Exception;

    /**
     * 文件下载
     * @param fileId
     * @param request
     * @param response
     * @return
     */
    public ResponseDTO downloadFile(Integer fileId, HttpServletRequest request, HttpServletResponse response);
}
