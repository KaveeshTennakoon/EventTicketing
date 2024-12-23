import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { BehaviorSubject, Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private tokenKey = 'auth_token';
  private userTypeKey = 'user_type';
  private isAuthenticatedSubject = new BehaviorSubject<boolean>(false);
  private nameKey = 'name';
  private idKey = 'id';

  constructor( private router: Router, private http: HttpClient) {
    // Check if token exists on service initialization
    this.isAuthenticatedSubject.next(this.isLoggedIn());
  }

  // Customer Login
  customerLogin(loginData: any): Observable<any> {
    return this.http.post('http://localhost:8080/customer/login', loginData);
  }

  // Vendor Login
  vendorLogin(loginData: any): Observable<any> {
    return this.http.post('http://localhost:8080/vendor/login', loginData);
  }

  // Set token and user type after successful login
  setToken(token: string, userType: string, name?: string, id?: number | string): void {
    localStorage.setItem(this.tokenKey, token);
    localStorage.setItem(this.userTypeKey, userType);
    this.isAuthenticatedSubject.next(true);
    if (name) {
      localStorage.setItem(this.nameKey, name);
    }
    if (id !== undefined) {
      localStorage.setItem(this.idKey, id.toString());
    }
  }

  // Get current token
  getToken(): string | null {
    return localStorage.getItem(this.tokenKey);
  }

  // Get current user type
  getUserType(): string | null {
    return localStorage.getItem(this.userTypeKey);
  }

  getName(): string | null {
    return localStorage.getItem(this.nameKey);
  }

  getId(): number | null {
    const id = localStorage.getItem(this.idKey);
    return id ? parseInt(id, 10) : null;
  }

  // Check if user is logged in
  isLoggedIn(requiredUserType?: string): boolean {
    const token = this.getToken();
    const userType = this.getUserType();
    
    if (!token || !userType) return false;
    
    // If a specific user type is required, check against it
    if (requiredUserType && userType !== requiredUserType) {
      return false;
    }

    return true;
  }

  // Observable to track authentication status
  get isAuthenticated$(): Observable<boolean> {
    return this.isAuthenticatedSubject.asObservable();
  }

  // Logout method
  logout(): void {
    localStorage.removeItem(this.tokenKey);
    localStorage.removeItem(this.userTypeKey);
    localStorage.removeItem(this.nameKey);
    localStorage.removeItem(this.idKey);
    this.isAuthenticatedSubject.next(false);
    this.router.navigate(['/VendorLogin']);
  }
}