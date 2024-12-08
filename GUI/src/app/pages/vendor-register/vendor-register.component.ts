import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-vendor-register',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './vendor-register.component.html',
  styleUrls: ['./vendor-register.component.css'],
})
export class VendorRegisterComponent {
  vendorname: string = '';
  vendoremail: string = '';
  vendorpassword: string = '';

  constructor(private http: HttpClient, private router: Router) {}

  errorMessage: string = '';

  VendorSave(registerForm: any) {
    if (registerForm.invalid) {
      return;
    }

    let bodyData = {
      vendorName: this.vendorname,
      vendorEmail: this.vendoremail,
      vendorPassword: this.vendorpassword,
    };

    this.http.post("http://localhost:8080/vendor/save", bodyData, { responseType: 'text' })
      .subscribe(
        (resultData: any) => {
          console.log(resultData);
          alert("Vendor registered successfully, Login again");
          this.router.navigate(['/VendorLogin'])
        },
        (error) => {
          console.error("Error occurred during registration", error);
          this.errorMessage = "Failed to register. Try again using another email.";
        }
      );

  }

  goToVendorLogin() {
    this.router.navigate(['/VendorLogin'])
  }

}
