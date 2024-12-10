import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';

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

  constructor(private http: HttpClient) {
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


}
