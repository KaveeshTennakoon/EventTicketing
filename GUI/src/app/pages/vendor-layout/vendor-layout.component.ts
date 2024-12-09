import { Component } from '@angular/core';
import { VendorHomeComponent } from "../vendor-home/vendor-home.component";
import { AuthService } from '../../auth/service/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-vendor-layout',
  standalone: true,
  imports: [VendorHomeComponent],
  templateUrl: './vendor-layout.component.html',
  styleUrl: './vendor-layout.component.css'
})
export class VendorLayoutComponent {

  constructor(private authService: AuthService, private router: Router) {}

  logout(): void {
    this.authService.logout();
  }

}
