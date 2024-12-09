import { Routes } from '@angular/router';
import { VendorLoginComponent } from './pages/vendor-login/vendor-login.component';
import { VendorRegisterComponent } from './pages/vendor-register/vendor-register.component';
import { AuthGuard } from './auth/guard/auth.guard';
import { VendorHomeComponent } from './pages/vendor-home/vendor-home.component';
import { CustomerHomeComponent } from './pages/customer-home/customer-home.component';
import { CustomerRegisterComponent } from './pages/customer-register/customer-register.component';
import { CustomerLoginComponent } from './pages/customer-login/customer-login.component';
import { VendorLayoutComponent } from './pages/vendor-layout/vendor-layout.component';
import { CustomerLayoutComponent } from './pages/customer-layout/customer-layout.component';

export const routes: Routes = [
    {
        path: '',
        redirectTo: 'VendorLogin',
        pathMatch: 'full'
    },
    {
        path: 'VendorLogin',
        component: VendorLoginComponent
    },
    {
        path: 'VendorRegister',
        component: VendorRegisterComponent
    },
    {
        path: 'CustomerRegister',
        component: CustomerRegisterComponent
    },
    {
        path: 'CustomerLogin',
        component: CustomerLoginComponent
    },
    {
        path: 'vendor',
        component: VendorLayoutComponent,
        children: [
            {
                path: 'home',
                canActivate: [AuthGuard],
                component: VendorHomeComponent
            },
        ]
    },
    {
        path: 'customer',
        component: CustomerLayoutComponent,
        children: [
            {
                path: 'home',
                canActivate: [AuthGuard],
                component: CustomerHomeComponent
            },
        ]
    }
];
