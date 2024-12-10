import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-customer-register',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './customer-register.component.html',
  styleUrl: './customer-register.component.css'
})
export class CustomerRegisterComponent {

  customername: string = '';
  customeremail: string = '';
  customerpassword: string = '';

  constructor(private http: HttpClient, private router: Router) {}

  errorMessage: string = '';

  CustomerSave(registerForm: any) {
    if (registerForm.invalid) {
      return;
    }

    let bodyData = {
      customerName: this.customeremail,
      customerEmail: this.customeremail,
      customerPassword: this.customeremail,
    };

    this.http.post("http://localhost:8080/customer/save", bodyData, { responseType: 'text' })
      .subscribe(
        (resultData: any) => {
          console.log(resultData);
          alert("Customer registered successfully, Login again");
          this.router.navigate(['/CustomerLogin'])
        },
        (error) => {
          console.error("Error occurred during registration", error);
          this.errorMessage = "Failed to register. Try again using another email.";
        }
      );

  }

  goToVendorRegister() {
    this.router.navigate(['/VendorRegister']);
  }

  goToCustomerLogin() {
    this.router.navigate(['/CustomerLogin']);
  }

  goToVendorLogin() {
    this.router.navigate(['/VendorLogin']);
  }
}
