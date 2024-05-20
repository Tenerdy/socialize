package com.tenerdy.mobileuserinfo.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "mobile-chat")
public interface ChatFeign{
    @GetMapping("/chatRecord/sendMessage")
    public void SendMessage(@RequestParam("id") Integer id, @RequestParam("message")String message);
    @GetMapping("/chatRecord/sendInitMessage")
    public void SendInitMessage(@RequestParam("id") Integer id, @RequestParam("groupId")Integer groupId);
}
