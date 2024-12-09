import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { AuthService } from '../../auth/service/auth.service';

@Component({
  selector: 'app-vendor-configure',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './vendor-configure.component.html',
  styleUrls: ['./vendor-configure.component.css']
})
export class VendorConfigureComponent {

  eventName: string = '';
  ticketPrice: number = 1;
  eventDate: string = new Date().toISOString().split('T')[0];
  totalTickets: number = 10;
  ticketpool: number = 1;
  ticketReleaseRate: number = 1;
  customerRetrievalRate: number = 1;


  constructor(private http: HttpClient ,private authService: AuthService) {}

  createTicketPool() {

    const id = this.authService.getId();

    let ticketPoolData = {
      eventName: this.eventName,
      ticketPrice: this.ticketPrice,
      totalTickets: this.totalTickets,
      eventDate: this.eventDate,
      ticketpool: this.ticketpool,
      ticketReleaseRate: this.ticketReleaseRate,
      customerRetrievalRate: this.customerRetrievalRate,
      ticketsAvailable: 0,
      vendorId: id
    };

    this.http.post('http://localhost:8080/ticketpool/create', ticketPoolData).subscribe({
      next: (response) => {
        alert("TicketPool for " + this.eventName + " is created successfully");
      },
      error: (error) => {
        alert("Failed to create the TicketPool");
      }
    })

  }
}
