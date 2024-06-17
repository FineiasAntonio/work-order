import { Routes } from '@angular/router';
import { LoginFormComponent } from './modules/authentication/login-form-page/login-form/login-form.component';
import { WorkordersTableComponent } from './modules/workorder/components/workorders-table/workorders-table.component';

export const routes: Routes = [
  {
    path: 'workorders',
    component: WorkordersTableComponent,
  },
];
