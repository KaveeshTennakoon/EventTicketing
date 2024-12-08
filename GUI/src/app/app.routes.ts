import { Routes } from '@angular/router';
import { LauyoutComponent } from './pages/lauyout/lauyout.component';
import { DashboardComponent } from './pages/dashboard/dashboard.component';
import { VendorLoginComponent } from './pages/vendor-login/vendor-login.component';
import { VendorRegisterComponent } from './pages/vendor-register/vendor-register.component';

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
        path: '',
        component: LauyoutComponent,
        children: [
            {
                path: 'dashboard',
                component: DashboardComponent
            }
        ]
    }
];
