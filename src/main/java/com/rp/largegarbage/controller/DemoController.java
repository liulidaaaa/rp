package com.rp.largegarbage.controller;

import com.rp.largegarbage.dto.ResponseDTO;
import com.rp.largegarbage.websocket.WebSocketServer;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import java.io.IOException;
/**
 * @Description 测试WebSocket
 * @Author liulida <2979284403@qq.com>
 * @Version v1.0.0
 * @Since 1.0
 * @Date 2020/7/10 14:08
 */
@RestController
public class DemoController {

    @GetMapping("index")
    public ResponseDTO index(){
        return ResponseDTO.buildSuccess("success");
    }

    @GetMapping("page")
    public ModelAndView page(){
        return new ModelAndView("/static/index.html");
    }

    @RequestMapping("/push/{toUserId}")
    public ResponseDTO pushToWeb(String message, @PathVariable String toUserId) throws IOException {
        WebSocketServer.sendInfo(message,toUserId);
        return ResponseDTO.buildSuccess("success");
    }
}
