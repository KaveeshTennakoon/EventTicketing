import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../../auth/service/auth.service';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { LoginResponse } from '../../model/login-response.model';

@Component({
  selector: 'app-customer-login',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './customer-login.component.html',
  styleUrl: './customer-login.component.css'
})
export class CustomerLoginComponent {

  customeremail: String = "";
  customerpassword: String = "";

  constructor(private http: HttpClient, private router: Router, private authService: AuthService) {}
  
  errorMessage: string = '';

  CustomerLogin(){

    let bodyData = {
      "email" : this.customeremail,
      "password" : this.customerpassword
    };

    this.http.post<LoginResponse>('http://localhost:8080/customer/login', bodyData)
      .subscribe({
        next: (response) => {
          if (response.status && response.token) {
            // Store the token
            this.authService.setToken(response.token, 'CUSTOMER', response.name);
            
            // Navigate to dashboard
            this.router.navigate(['/customer/home']);
          } else {
            // Handle login failure
            this.errorMessage = 'Login failed. ' + response.message;
          }
        },
        error: (err) => {
          this.errorMessage = 'Login error' + err;
        }
      });

  }

  goToVendorRegister() {
    this.router.navigate(['/VendorRegister']);
  }

  goToVendorLogin() {
    this.router.navigate(['/VendorLogin']);
  }

  goToCustomerRegister() {
    this.router.navigate(['/CustomerRegister']);
  }


}
