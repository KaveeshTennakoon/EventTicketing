import { HttpClient } from '@angular/common/http';
import { Component, OnInit, OnDestroy } from '@angular/core';
import { AuthService } from '../../auth/service/auth.service';
import { CommonModule } from '@angular/common';
import { LogComponent } from "../log/log.component";

@Component({
  selector: 'app-customer-ticketpool',
  standalone: true,
  imports: [CommonModule, LogComponent],
  templateUrl: './customer-ticketpool.component.html',
  styleUrls: ['./customer-ticketpool.component.css']
})
export class CustomerTicketpoolComponent implements OnInit, OnDestroy {
  ticketPools: any[] = [];
  errorMessage: string = '';
  activeTicketBuyPools: Set<number> = new Set();
  private pollingIntervalId!: any; // Stores the interval ID for clearing later

  constructor(private http: HttpClient, private authService: AuthService) {}

  ngOnInit(): void {
    this.loadTicketPools();
    this.startPolling(); // Start polling on component initialization
  }

  ngOnDestroy(): void {
    this.stopPolling(); // Stop polling when the component is destroyed
  }

  loadTicketPools(): void {
    this.http.get<any[]>('http://localhost:8080/ticketpool/all')
      .subscribe({
        next: (data) => this.ticketPools = data,
        error: (err) => this.errorMessage = 'Error fetching ticket pools!'
      });
  }

  toggleTicketAddition(ticketPool: any): void {
    if (this.activeTicketBuyPools.has(ticketPool.ticketpoolId)) {
      this.stopTicketBuy(ticketPool.ticketpoolId);
    } else {
      this.startTicketBuy(ticketPool);
    }
  }

  startTicketBuy(ticketPool: any): void {
    const payload = {
      eventId: ticketPool.ticketpoolId,
      userName: this.authService.getName(),
      userId: this.authService.getId()
    };
  
    this.http.post(`http://localhost:8080/ticketpool/start-ticket-buy/${ticketPool.ticketpoolId}/${this.authService.getId()}`, payload)
      .subscribe({
        next: () => {
          this.activeTicketBuyPools.add(ticketPool.ticketpoolId);
          this.loadTicketPools(); // Refresh the ticket pools after starting
        },
        error: (err) => {
          this.errorMessage = err.error.error || 'Error starting ticket buy';
          console.log(err);
        }
      });
  }

  stopTicketBuy(ticketPoolId: number): void {
    this.http.post(`http://localhost:8080/ticketpool/stop-ticket-buy/${ticketPoolId}/${this.authService.getId()}`, {})
      .subscribe({
        next: () => {
          this.activeTicketBuyPools.delete(ticketPoolId);
          this.loadTicketPools(); // Refresh the ticket pools after stopping
        },
        error: (err) => {
          this.errorMessage = err.error.error || 'Error stopping ticket buy';
          console.log(err);
        }
      });
  }

  startPolling(): void {
    this.pollingIntervalId = setInterval(() => {
      this.loadTicketPools();
    }, 1000);
  }

  stopPolling(): void {
    if (this.pollingIntervalId) {
      clearInterval(this.pollingIntervalId);
    }
  }
}
