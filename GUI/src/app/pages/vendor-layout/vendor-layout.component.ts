import { Component } from '@angular/core';
import { VendorHomeComponent } from "../vendor-home/vendor-home.component";

@Component({
  selector: 'app-vendor-layout',
  standalone: true,
  imports: [VendorHomeComponent],
  templateUrl: './vendor-layout.component.html',
  styleUrl: './vendor-layout.component.css'
})
export class VendorLayoutComponent {

}
