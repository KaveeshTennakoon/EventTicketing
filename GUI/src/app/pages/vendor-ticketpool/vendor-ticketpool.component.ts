import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../../auth/service/auth.service';

@Component({
  selector: 'app-vendor-ticketpool',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './vendor-ticketpool.component.html',
  styleUrl: './vendor-ticketpool.component.css'
})
export class VendorTicketpoolComponent implements OnInit{

  ticketPools: any[] = [];
  errorMessage: string = '';

  constructor(private http: HttpClient, private authService: AuthService) {
    console.log(this.loadTicketPools())
  }

  ngOnInit(): void {
    this.loadTicketPools();
  }

  loadTicketPools(): void {
    this.http.get<any[]>('http://localhost:8080/ticketpool/all')
      .subscribe({
        next: (data) => 
          this.ticketPools = data,
        error: (err) => 
          this.errorMessage = 'Error fetching ticket pools!'
      });
  }

  addTicketToPool(ticketPoolId: number): void {
    console.log(ticketPoolId);

    const payload = {
      eventId: ticketPoolId,
      userName: this.authService.getName(),
      userId: this.authService.getId()
    };
  
    this.http.post('http://localhost:8080/ticketpool/addtickets', payload)
      .subscribe({
        next: (response) => {
          this.loadTicketPools();
          alert('Ticket added successfully!');
        },
        error: (err) => {
          this.errorMessage = err.error.error || 'Error adding ticket';
          console.log(err);
        }
      });
  }

}
