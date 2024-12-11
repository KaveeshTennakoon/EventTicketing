import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../auth/service/auth.service';
import { CommonModule } from '@angular/common';
import { LogComponent } from "../log/log.component";

@Component({
  selector: 'app-customer-ticketpool',
  standalone: true,
  imports: [CommonModule, LogComponent],
  templateUrl: './customer-ticketpool.component.html',
  styleUrl: './customer-ticketpool.component.css'
})
export class CustomerTicketpoolComponent implements OnInit {
  ticketPools: any[] = [];
  errorMessage: string = '';
  activeTicketAdditionPools: Set<number> = new Set();
  private pollingIntervalId!: any; // Stores the interval ID for clearing later

  constructor(private http: HttpClient, private authService: AuthService) {}

  ngOnInit(): void {
    this.loadTicketPools();
    this.startPolling(); // Start polling when the component initializes
  }

  ngOnDestroy(): void {
    this.stopPolling(); // Clear the interval when the component is destroyed
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
        next: () => {
          this.activeTicketAdditionPools.add(ticketPool.ticketpoolId);
          this.loadTicketPools(); // Refresh the ticket pools after starting
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
        next: () => {
          this.activeTicketAdditionPools.delete(ticketPoolId);
          this.loadTicketPools(); // Refresh the ticket pools after stopping
        },
        error: (err) => {
          this.errorMessage = err.error.error || 'Error stopping ticket addition';
          console.log(err);
        }
      });
  }

  startPolling(): void {
    this.pollingIntervalId = setInterval(() => {
      this.loadTicketPools();
    }, 1000); // Poll every 5 seconds
  }

  stopPolling(): void {
    if (this.pollingIntervalId) {
      clearInterval(this.pollingIntervalId);
    }
  }
}