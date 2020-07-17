package com.rp.largegarbage.service.impl;

import com.rp.largegarbage.config.GlobalProperties;
import com.rp.largegarbage.dao.FileInfoDao;
import com.rp.largegarbage.dto.ResponseDTO;
import com.rp.largegarbage.entity.FileInfo;
import com.rp.largegarbage.service.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @Description
 * @Author liulida <2979284403@qq.com>
 * @Version v1.0.0
 * @Since 1.0
 * @Date 2020/6/30 13:51
 */
@Service
public class FileServiceImpl implements FileService {
    /** logger */
    private static final Logger LOGGER = LoggerFactory.getLogger(FileServiceImpl.class);


    /**
     * 默认大小 50M
     */
    public static final long DEFAULT_MAX_SIZE = 50 * 1024 * 1024;

    @Autowired
    private FileInfoDao fileInfoDao;

    @Autowired
    private GlobalProperties globalProperties;

    /**
     * 文件上传页面
     * @return
     */
    //@GetMapping("/")
    /*public String updatePage() {
        return "file";
    }*/
    /**
     * 单文件上传
     * @param file
     * @return
     */
    @Override
    public ResponseDTO upload(MultipartFile file) throws Exception {
        // 获取文件在服务器上的存储位置
        String serverPath = globalProperties.getServerPath();

        // 获取允许上传的文件扩展名
        String extension = globalProperties.getExtension();

        File filePath = new File(serverPath);
        LOGGER.info("文件保存的路径为：" + filePath);
        if (!filePath.exists() && !filePath.isDirectory()) {
            LOGGER.info("目录不存在，则创建目录：" + filePath);
            filePath.mkdir();
        }

        // 判断文件是否为空
        if (file.isEmpty()) {
            LOGGER.info("文件为空");
            return new ResponseDTO(-1, 0, null);
        }
        //判断文件是否为空文件
        if (file.getSize() <= 0) {
            return new ResponseDTO(-1, "文件大小为空，上传失败 ", null);
        }

        // 判断文件大小不能大于50M
        if (DEFAULT_MAX_SIZE != -1 && file.getSize() > DEFAULT_MAX_SIZE) {
            return new ResponseDTO(-1, "上传的文件不能大于50M ", null);
        }

        // 获取文件名
        String fileName = file.getOriginalFilename();
        // 获取文件扩展名
        String fileExtension = fileName.substring(fileName.lastIndexOf(".")).toLowerCase();

        // 判断文件扩展名是否正确
        if (!extension.contains(fileExtension)) {
            return new ResponseDTO(-1, "文件扩展名不正确 ", null);
        }

        FileInfo sysFileInfo = new FileInfo();
        // 重新生成的文件名
        String saveFileName = System.currentTimeMillis() + fileExtension;
        // 在指定目录下创建该文件
        File targetFile = new File(filePath, saveFileName);

        LOGGER.info("将文件保存到指定目录");
        try {
            file.transferTo(targetFile);
        } catch (IOException e) {
            throw new Exception(e.getMessage());
        }
        long size = file.getSize();
        // 保存数据
        sysFileInfo.setFileName(fileName);
        sysFileInfo.setFilePath(serverPath + "/" + saveFileName);
        sysFileInfo.setFileSize(file.getSize());

        LOGGER.info("新增文件数据");
        // 新增文件数据
        FileInfo save = fileInfoDao.save(sysFileInfo);
        Integer id = save.getFileId();
        return new ResponseDTO(1, id.toString() , "上传成功 ");
    }

    /**
     * 批量文件上传
     * @param files
     * @return
     * @throws Exception
     */
    @Override
    public  ResponseDTO batchUpload(MultipartFile[] files) throws Exception {
        if (null == files) {
            return new ResponseDTO(-1, "参数为空 ", null);
        }
        StringBuilder sb = new StringBuilder();
        for (MultipartFile multipartFile : files) {
            ResponseDTO upload = upload(multipartFile);
            sb.append(upload.getData());
            sb.append(",");
        }
        String ids = sb.toString();
        ids = ids.substring(0, ids.length() - 1);
        return new ResponseDTO(-1, ids , "批量上传成功 ");
    }

    public static void main(String[] args) {
        StringBuilder sb = new StringBuilder();
        sb.append(1);
        sb.append(",");
        sb.append(2);
        sb.append(",");
        String ids = sb.toString();
        ids = ids.substring(0, ids.length() - 1);
        System.out.println(ids);
    }


    /**
     * 下载
     * @param fileId
     * @param request
     * @param response
     * @return
     */
    @Override
    public ResponseDTO downloadFile(Integer fileId, HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("文件ID为：" + fileId);
        // 判断传入参数是否非空
        if (fileId == null) {
            return new ResponseDTO(-1, "请求参数不能为空 ", null);
        }
        // 根据fileId查询文件表
        Optional<FileInfo> sysFileInfo = fileInfoDao.findById(fileId);
        //todo moke
        if (null==sysFileInfo) {//sysFileInfo.isEmpty()
            return new ResponseDTO(-1, "下载的文件不存在 ", null);
        }
        // 获取文件全路径
        File file = new File(sysFileInfo.get().getFilePath());
        String fileName = sysFileInfo.get().getFileName();
        // 判断是否存在磁盘中
        if (file.exists()) {
            // 设置强制下载不打开
            response.setContentType("application/force-download");
            // 设置文件名
            response.addHeader("Content-Disposition", "attachment;fileName=" + fileName);
            byte[] buffer = new byte[1024];
            FileInputStream fis = null;
            BufferedInputStream bis = null;
            try {
                fis = new FileInputStream(file);
                bis = new BufferedInputStream(fis);
                OutputStream os = response.getOutputStream();
                int i = bis.read(buffer);
                while (i != -1) {
                    os.write(buffer, 0, i);
                    i = bis.read(buffer);
                }
                return new ResponseDTO(-1, "下载成功 ", null);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (bis != null) {
                    try {
                        bis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (fis != null) {
                    try {
                        fis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } else {
            return new ResponseDTO(-1, "数据库查询存在，本地磁盘不存在文件 ", null);
        }
        return new ResponseDTO(-1, "下载失败 ", null);
    }
}
