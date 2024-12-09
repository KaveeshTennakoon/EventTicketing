import { Injectable } from '@angular/core';
import { 
  CanActivate, 
  ActivatedRouteSnapshot, 
  RouterStateSnapshot, 
  Router 
} from '@angular/router';
import { Observable } from 'rxjs';
import { AuthService } from '../service/auth.service';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {
  constructor(
    private authService: AuthService,
    private router: Router
  ) {}

  canActivate(
    route: ActivatedRouteSnapshot, 
    state: RouterStateSnapshot
  ): Observable<boolean> | Promise<boolean> | boolean {
    // Check if logged in and match the required user type
    const requiredUserType = route.data['userType'];
    
    if (this.authService.isLoggedIn(requiredUserType)) {
      return true;
    }

    // Redirect to appropriate login page based on user type
    const loginRoute = requiredUserType === 'VENDOR' ? '/VendorLogin' : '/CustomerLogin';
    this.router.navigate([loginRoute], {
      queryParams: { returnUrl: state.url }
    });
    return false;
  }
}