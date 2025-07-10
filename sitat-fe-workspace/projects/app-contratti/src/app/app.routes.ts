import { ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { BackwardGuard } from '@maggioli/app-commons';
import { SdkConfigResolverService, SdkEmptyLayoutWidget } from '@maggioli/sdk-widgets';

import { inject } from '@angular/core';
import { AuthenticationGuard } from './modules/guards/authentication.guard';
import { SdkAppWidget } from './modules/layout/components/sdk-pages/sdk-app.widget';
import { SdkNotFoundWidget } from './modules/layout/components/sdk-pages/sdk-not-found.widget';
import { AuthenticationResolverService } from './modules/resolvers/authentication.resolver';
import { AppCodeResolverService } from './modules/resolvers/appCode.resolver';

export const routes: Routes = [
    {
        path: 'page/:slug',
        component: SdkAppWidget,
        resolve: {
            config: (route: ActivatedRouteSnapshot, state: RouterStateSnapshot) => inject(SdkConfigResolverService).resolve(route, state),
            appCode: (route: ActivatedRouteSnapshot, state: RouterStateSnapshot) => inject(AppCodeResolverService).resolve(route, state)
        },
        canActivate: [(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) => inject(AuthenticationGuard).canActivate(route, state)],
        canDeactivate: [() => inject(BackwardGuard).canDeactivate()]
    },
    {
        path: '',
        component: SdkEmptyLayoutWidget,
        resolve: {
            authentication: (route: ActivatedRouteSnapshot, state: RouterStateSnapshot) => inject(AuthenticationResolverService).resolve(route, state)
        }
    },
    { path: '**', component: SdkNotFoundWidget }
];