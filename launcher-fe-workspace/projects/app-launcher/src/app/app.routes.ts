import { ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { SdkConfigResolverService, SdkEmptyLayoutWidget } from '@maggioli/sdk-widgets';

import { inject } from '@angular/core';
import { SdkAppWidget } from './modules/layout/components/sdk-pages/sdk-app.widget';
import { SdkNotFoundWidget } from './modules/layout/components/sdk-pages/sdk-not-found.widget';
import { AuthenticationResolver } from './modules/resolvers/authentication.resolver';

export const routes: Routes = [
    {
        path: 'page/:slug',
        component: SdkAppWidget,
        resolve: {
            config: (route: ActivatedRouteSnapshot, state: RouterStateSnapshot) => inject(SdkConfigResolverService).resolve(route, state)
        }
    },
    {
        path: '',
        component: SdkEmptyLayoutWidget,
        resolve: {
            authentication: (route: ActivatedRouteSnapshot, state: RouterStateSnapshot) => inject(AuthenticationResolver).resolve(route, state)
        }
    },
    { path: '**', component: SdkNotFoundWidget }
];