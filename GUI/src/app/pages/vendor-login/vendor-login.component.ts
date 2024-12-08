import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../auth/service/auth.service';
import { LoginResponse } from '../../model/login-response.model';

@Component({
  selector: 'app-vendor-login',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './vendor-login.component.html',
  styleUrl: './vendor-login.component.css'
})
export class VendorLoginComponent {

  vendoremail: String = "";
  vendorpassword: String = "";

  constructor(private http: HttpClient, private router: Router, private authService: AuthService) {}
  
  errorMessage: string = '';

  VendorLogin(){

    let bodyData = {
      "email" : this.vendoremail,
      "password" : this.vendorpassword
    };

    this.http.post<LoginResponse>('http://localhost:8080/vendor/login', bodyData)
      .subscribe({
        next: (response) => {
          if (response.status && response.token) {
            // Store the token
            this.authService.setToken(response.token);
            
            // Navigate to dashboard
            this.router.navigate(['/dashboard']);
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

}
