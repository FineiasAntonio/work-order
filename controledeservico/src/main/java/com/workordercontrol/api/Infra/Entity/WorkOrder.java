package com.workordercontrol.api.Infra.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
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

    @ManyToOne
    @JoinColumn(name = "client_id")
    @Schema(exampleClasses = Client.class)
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

    @ManyToOne
    @JoinColumn(name = "employee_id")
    @Schema(exampleClasses = Employee.class)
    private Employee employee;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "reserve_id")
    private Reserve reserve;

    private double totalValue;
}
