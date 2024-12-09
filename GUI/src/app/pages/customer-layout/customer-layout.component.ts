import { Component } from '@angular/core';
import { AuthService } from '../../auth/service/auth.service';
import { Router } from '@angular/router';
import { CustomerHomeComponent } from "../customer-home/customer-home.component";

@Component({
  selector: 'app-customer-layout',
  standalone: true,
  imports: [CustomerHomeComponent],
  templateUrl: './customer-layout.component.html',
  styleUrl: './customer-layout.component.css'
})
export class CustomerLayoutComponent {

  customerName: string | null = null;

  constructor(private authService: AuthService, private router: Router) {
    this.customerName = this.authService.getName();
  }

  logout(): void {
    this.authService.logout();
  }
  
}
