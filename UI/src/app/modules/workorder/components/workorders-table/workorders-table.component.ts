import { Component, OnInit } from '@angular/core';
import { WorkOrderSummary } from '../../../../shared/models/workorder/workorder';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-workorders-table',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './workorders-table.component.html',
  styleUrl: './workorders-table.component.scss',
})
export class WorkordersTableComponent implements OnInit {
  protected tableData!: WorkOrderSummary[];

  ngOnInit(): void {
    this.tableData = [
      {
        number: 1,
        clientName: 'Acme Corp',
        equipment: 'Generator',
        exceptedDate: new Date('2024-07-01'),
        employee: 'John Smith',
        status: 'In Progress',
      },
      {
        number: 2,
        clientName: 'Beta Industries',
        equipment: 'Air Conditioner',
        exceptedDate: new Date('2024-07-05'),
        employee: 'Jane Doe',
        status: 'Completed',
      },
      {
        number: 3,
        clientName: 'Gamma LLC',
        equipment: 'Boiler',
        exceptedDate: new Date('2024-07-10'),
        employee: 'Alice Johnson',
        status: 'Pending',
      },
      {
        number: 4,
        clientName: 'Delta Ltd',
        equipment: 'Compressor',
        exceptedDate: new Date('2024-07-15'),
        employee: 'Bob Brown',
        status: 'In Progress',
      },
      {
        number: 5,
        clientName: 'Epsilon Inc',
        equipment: 'Cooling Tower',
        exceptedDate: new Date('2024-07-20'),
        employee: 'Charlie Davis',
        status: 'Scheduled',
      },
    ];
  }
}
