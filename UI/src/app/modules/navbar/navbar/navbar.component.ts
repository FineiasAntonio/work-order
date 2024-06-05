import { Component } from '@angular/core';

interface NavbarItem {
  item: string;
  icon: string;
  selected: boolean;
}

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [],
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.scss',
})
export class NavbarComponent {
  protected items: NavbarItem[] = [
    {
      item: 'Home',
      icon: 'assets/home-icon.svg',
      selected: false,
    },
    {
      item: 'Work Orders',
      icon: 'assets/work-order-icon.svg',
      selected: false,
    },
    {
      item: 'Storage',
      icon: '',
      selected: false,
    },
    {
      item: 'Clients',
      icon: '',
      selected: false,
    },
  ];
}
