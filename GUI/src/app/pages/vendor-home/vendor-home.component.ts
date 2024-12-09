import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-vendor-home',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './vendor-home.component.html',
  styleUrls: ['./vendor-home.component.css']
})
export class VendorHomeComponent {

  // Initialize fields with appropriate default values
  eventName: string = "";  // Event name must be a string
  ticketPrice: number = 1;  // Default ticket price
  eventDate: string = new Date().toISOString().split('T')[0];  // Default date is today's date
  totalTickets: number = 10;  // Default total tickets
  ticketpool: number = 1;  // Default ticket pool should not be 0, setting to 1 for valid entry
  ticketReleaseRate: number = 1;  // Default release rate
  customerRetrievalRate: number = 1;  // Default retrieval rate

  onSubmit() {
    console.log('Form submitted with the following values:');
    console.log({
      eventName: this.eventName,
      ticketPrice: this.ticketPrice,
      eventDate: this.eventDate,
      totalTickets: this.totalTickets,
      ticketpool: this.ticketpool,
      ticketReleaseRate: this.ticketReleaseRate,
      customerRetrievalRate: this.customerRetrievalRate
    });
  }
}
