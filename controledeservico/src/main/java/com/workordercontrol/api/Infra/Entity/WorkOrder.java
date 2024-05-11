package com.workordercontrol.api.Infra.Entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "workorders")
public class WorkOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int workOrderId;

    @JoinColumn(name = "client_id")
    private Client client;

    private Date createdAt;
    private Date exceptedDate;
    private Date finishedAt;
    private Date deliveredAt;

    private String equipment;
    private String serialNumber;
    private String notes;
    private String service;
    private Status status;

    @JoinColumn(name = "employee_id")
    private Employee employee;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "reserve_id")
    private Reserve reserve;

    private double totalValue;
}
