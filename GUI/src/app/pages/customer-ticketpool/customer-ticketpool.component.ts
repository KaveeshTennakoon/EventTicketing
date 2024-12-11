import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../auth/service/auth.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-customer-ticketpool',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './customer-ticketpool.component.html',
  styleUrl: './customer-ticketpool.component.css'
})
export class CustomerTicketpoolComponent implements OnInit {
  ticketPools: any[] = [];
  errorMessage: string = '';
  activeTicketBuyPools: Set<number> = new Set();

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
        next: (response) => {
          this.activeTicketBuyPools.add(ticketPool.ticketpoolId);
          this.loadTicketPools();  // Refresh the ticket pools after starting
        },
        error: (err) => {
          this.errorMessage = err.error.error || 'Error starting ticket addition';
          console.log(err);
        }
      });
  }

  stopTicketBuy(ticketPoolId: number): void {
    this.http.post(`http://localhost:8080/ticketpool/stop-ticket-buy/${ticketPoolId}/${this.authService.getId()}`, {})
      .subscribe({
        next: (response) => {
          this.activeTicketBuyPools.delete(ticketPoolId);
          this.loadTicketPools();  // Refresh the ticket pools after stopping
        },
        error: (err) => {
          this.errorMessage = err.error.error || 'Error stopping ticket addition';
          console.log(err);
        }
      });
  }
}
