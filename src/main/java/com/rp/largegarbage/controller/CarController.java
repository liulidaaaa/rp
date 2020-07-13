package com.rp.largegarbage.controller;

import com.rp.largegarbage.dto.ResponseDTO;
import com.rp.largegarbage.entity.Car;
import com.rp.largegarbage.service.CarService;
import org.springframework.web.bind.annotation.PathVariable;
import com.rp.largegarbage.websocket.WebSocketServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.io.IOException;
import java.util.List;

/**
 * @Description
 * @Author liulida <2979284403@qq.com>
 * @Version v1.0.0
 * @Since 1.0
 * @Date 2020/7/1 15:08
 */
@RestController
@RequestMapping("car")
public class CarController {
    /**
     * logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(CarController.class);

    @Autowired
    private CarService carService;

    @PostMapping("carList")
    public ResponseDTO carList() {
        return ResponseDTO.buildSuccess(carService.carList());
    }

    @RequestMapping("/push/{toUserId}")
    public ResponseDTO pushCarInformation(String message, @PathVariable String toUserId) throws IOException {
        WebSocketServer.sendInfo(message,toUserId);
        return ResponseDTO.buildSuccess("success");
    }

}
