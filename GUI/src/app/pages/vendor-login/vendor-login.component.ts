import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './vendor-login.component.html',
  styleUrl: './vendor-login.component.css'
})
export class VendorLoginComponent {

  vendoremail: String = "";
  vendorpassword: String = "";

  constructor(private http: HttpClient, private router: Router) {}
  
  errorMessage: string = '';

  VendorLogin(){

    let bodyData = {
      "email" : this.vendoremail,
      "password" : this.vendorpassword
    };

    this.http.post("http://localhost:8080/vendor/login", bodyData).subscribe((resultData : any) =>
    {
      console.log(resultData);

      if (resultData.message == "Email is incorrect") {
        this.errorMessage = 'Email not found';
      }
      else if (resultData.message == "Login successful") {
        this.router.navigateByUrl('/dashboard');
      }
      else {
        this.errorMessage = 'Password incorrect';
      }

    });

  }

  goToVendorRegister() {
    this.router.navigate(['/VendorRegister']);
  }

}
