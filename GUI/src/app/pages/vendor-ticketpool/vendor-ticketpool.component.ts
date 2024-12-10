import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../auth/service/auth.service';

@Component({
  selector: 'app-vendor-ticketpool',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './vendor-ticketpool.component.html',
  styleUrls: ['./vendor-ticketpool.component.css']
})
export class VendorTicketpoolComponent implements OnInit {
  ticketPools: any[] = [];
  errorMessage: string = '';
  activeTicketAdditionPools: Set<number> = new Set();

  constructor(private http: HttpClient, private authService: AuthService) {}

  ngOnInit(): void {
    this.loadTicketPools();
  }

  loadTicketPools(): void {
    this.http.get<any[]>('http://localhost:8080/ticketpool/all')
      .subscribe({
        next: (data) => this.ticketPools = data,
        error: (err) => this.errorMessage = 'Error fetching ticket pools!'
      });
  }

  toggleTicketAddition(ticketPool: any): void {
    if (this.activeTicketAdditionPools.has(ticketPool.ticketpoolId)) {
      this.stopTicketAddition(ticketPool.ticketpoolId);
    } else {
      this.startTicketAddition(ticketPool);
    }
  }

  startTicketAddition(ticketPool: any): void {
    const payload = {
      eventId: ticketPool.ticketpoolId,
      userName: this.authService.getName(),
      userId: this.authService.getId()
    };
  
    this.http.post(`http://localhost:8080/ticketpool/start-ticket-addition/${ticketPool.ticketpoolId}/${this.authService.getId()}`, payload)
      .subscribe({
        next: (response) => {
          this.activeTicketAdditionPools.add(ticketPool.ticketpoolId);
          this.loadTicketPools();  // Refresh the ticket pools after starting
        },
        error: (err) => {
          this.errorMessage = err.error.error || 'Error starting ticket addition';
          console.log(err);
        }
      });
  }

  stopTicketAddition(ticketPoolId: number): void {
    this.http.post(`http://localhost:8080/ticketpool/stop-ticket-addition/${ticketPoolId}/${this.authService.getId()}`, {})
      .subscribe({
        next: (response) => {
          this.activeTicketAdditionPools.delete(ticketPoolId);
          this.loadTicketPools();  // Refresh the ticket pools after stopping
        },
        error: (err) => {
          this.errorMessage = err.error.error || 'Error stopping ticket addition';
          console.log(err);
        }
      });
  }
}
