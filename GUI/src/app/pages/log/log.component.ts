import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { Subscription, interval } from 'rxjs';

@Component({
  selector: 'app-log',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './log.component.html',
  styleUrls: ['./log.component.css']
})
export class LogComponent implements OnInit, OnDestroy {
  logs: any[] = [];
  private pollingSubscription: Subscription | undefined;

  constructor(private http: HttpClient) {}

  ngOnInit(): void {
    this.startPolling();
  }

  ngOnDestroy(): void {
    this.stopPolling();
  }

  // Start periodic polling
  startPolling(): void {
    this.pollingSubscription = interval(1000).subscribe(() => this.fetchLogs());
  }

  // Stop periodic polling
  stopPolling(): void {
    if (this.pollingSubscription) {
      this.pollingSubscription.unsubscribe();
    }
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
