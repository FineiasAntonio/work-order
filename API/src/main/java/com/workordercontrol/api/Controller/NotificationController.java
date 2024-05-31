package com.workordercontrol.api.Controller;

import com.workordercontrol.api.Domain.Service.NotificationService;
import com.workordercontrol.api.Infra.DTO.NotificationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping ("/notifications")
public class NotificationController {

    @Autowired
    private NotificationService service;

    @GetMapping
    public ResponseEntity<List<NotificationDTO>> getNotifications(){
        List<NotificationDTO> notificationPool = service.getNotificationPool();

        return new ResponseEntity<>(notificationPool, HttpStatus.OK);
    }

}
