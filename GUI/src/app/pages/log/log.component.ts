import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-log',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './log.component.html',
  styleUrls: ['./log.component.css']
})
export class LogComponent implements OnInit {
  logs: any[] = [];

  constructor(private http: HttpClient) {}

  ngOnInit(): void {
    this.fetchLogs();
  }

  // Fetch ticket logs from the API
  fetchLogs(): void {
    this.http.get<any[]>('http://localhost:8080/ticketlogs/all').subscribe(
      (data) => {
        this.logs = data.reverse();
      },
      (error) => {
        console.error('Error fetching logs:', error);
      }
    );
  }
}
