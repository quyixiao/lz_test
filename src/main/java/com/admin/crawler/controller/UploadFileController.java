package com.admin.crawler.controller;

import com.admin.crawler.utils.R;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by wutao on 2020/3/23.
 */
@RestController
@RequestMapping("/upload")
public class UploadFileController {
    private static Logger logger = LoggerFactory.getLogger(UploadFileController.class);


    /**
     * @param request
     * @param response
     * @param file
     * @throws IOException
     */
    @PostMapping("/uploadOCRPhoto")
    public R uploadOCRPhoto(HttpServletRequest request, @RequestParam("file") MultipartFile file) throws IOException {
        String userName = request.getParameter("userPhone");
        String type = request.getParameter("type");
        logger.info("UnicomUploadIdNumberController uploadOCRPhoto begin + userPhone = {} + type = {}", userName, type);
        // 请求face++ 进行人脸认证
        return R.ok("上传文件成功");
    }
}
