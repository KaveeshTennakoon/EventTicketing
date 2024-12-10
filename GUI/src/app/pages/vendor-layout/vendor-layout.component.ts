import { Component } from '@angular/core';
import { VendorConfigureComponent } from "../vendor-configure/vendor-configure.component";
import { AuthService } from '../../auth/service/auth.service';
import { Router } from '@angular/router';
import { VendorTicketpoolComponent } from "../vendor-ticketpool/vendor-ticketpool.component";
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-vendor-layout',
  standalone: true,
  imports: [VendorConfigureComponent, VendorTicketpoolComponent, CommonModule],
  templateUrl: './vendor-layout.component.html',
  styleUrl: './vendor-layout.component.css'
})
export class VendorLayoutComponent {

  vendorName: string | null = null;
  activeSection = 'configure';

  constructor(private authService: AuthService, private router: Router) {
    this.vendorName = this.authService.getName();
  }

  showSection(section: string) {
    this.activeSection = section;
  }

  logout(): void {
    this.authService.logout();
  }

}
