import { Routes } from '@angular/router';
import { VendorLoginComponent } from './pages/vendor-login/vendor-login.component';
import { VendorRegisterComponent } from './pages/vendor-register/vendor-register.component';
import { AuthGuard } from './auth/guard/auth.guard';
import { VendorConfigureComponent } from './pages/vendor-configure/vendor-configure.component';
import { CustomerRegisterComponent } from './pages/customer-register/customer-register.component';
import { CustomerLoginComponent } from './pages/customer-login/customer-login.component';
import { VendorLayoutComponent } from './pages/vendor-layout/vendor-layout.component';
import { CustomerLayoutComponent } from './pages/customer-layout/customer-layout.component';
import { VendorTicketpoolComponent } from './pages/vendor-ticketpool/vendor-ticketpool.component';
import { CustomerTicketpoolComponent } from './pages/customer-ticketpool/customer-ticketpool.component';

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
                path: 'configure',
                canActivate: [AuthGuard],
                component: VendorConfigureComponent,
                data: { userType: 'VENDOR' }
            },
            {
                path: 'ticketpool',
                canActivate: [AuthGuard],
                component: VendorTicketpoolComponent,
                data: { userType: 'VENDOT'}
            }
        ]
    },
    {
        path: 'customer',
        component: CustomerLayoutComponent,
        children: [
            {
                path: 'ticketpool',
                canActivate: [AuthGuard],
                component: CustomerTicketpoolComponent,
                data: { userType: 'CUSTOMER' }
            },
        ]
    }
];
