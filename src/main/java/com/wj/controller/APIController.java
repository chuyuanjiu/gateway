/*
package com.wj.controller;

import com.alibaba.fastjson.JSONObject;
import com.wj.utils.HttpClientUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

*/
/**
 * @author wangjun
 * @date 18-3-10 下午4:35
 * @description
 * @modified by
 *//*


@RestController
@RequestMapping("/api")
public class APIController {

    private static Logger logger = LoggerFactory.getLogger(APIController.class);

    private final static String GET_METHOD = "GET";

    private final static String POST_METHOD = "POST";

    private final static String host = "http://localhost:9800/";

    @RequestMapping("/{pathName1}/{pathName2}")
    public Object gateWay(HttpServletRequest request,
                       @PathVariable String pathName1,
                       @PathVariable String pathName2) {
        String path = pathName1 + "/" + pathName2;
        String methodName = request.getMethod();
        String result = "";
        if (GET_METHOD.equalsIgnoreCase(methodName)) {
            //String url = request.getRequestURI();
            path = host + path;
            logger.info("request info:" + path);
            result = HttpClientUtil.sendGet(path);
            logger.info("response info:" + result);
        }
        JSONObject resultJson = JSONObject.parseObject(result);
        return resultJson;
    }
}
*/
