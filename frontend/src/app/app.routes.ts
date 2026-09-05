import { Routes } from '@angular/router';
import { authGuard } from './core/guards/auth.guard';

export const routes: Routes = [
    {
        path: 'login',
        loadComponent: () => import('./features/auth/login/login').then(m => m.Login)
    },

    {
        path: 'register',
        loadComponent: () => import('./features/auth/register/register').then(m => m.Register)
    },
    {
        path:'',
        canActivate:[authGuard],
        loadComponent: () => import('./layout/app-layout/app-layout').then(m => m.AppLayout),
        children:[
             {
        path: 'projects',
        loadComponent: () => import('./features/projects/project-list/project-list').then(m => m.ProjectList)
            },
            {
                path: 'dashboard',
                loadComponent: () => import('./features/dashboard/dashboard-component/dashboard-component').then(m => m.DashboardComponent)
            },
            {
                path: 'tasks',
                loadComponent: () => import('./features/tasks/tasks-list/tasks-list').then(m => m.TasksList)
            },
            {
                path: 'projects/create',
                loadComponent: () => import('./features/projects/project-create/project-create').then(m => m.ProjectCreate)
            },
            {
                path: 'projects/:id',
                loadComponent: () => import('./features/projects/project-details/project-details').then(m => m.ProjectDetails)
            },
            {
                path: 'projects/:id/members/add',
                loadComponent: () => import('./features/members/members-add/members-add').then(m => m.MembersAdd)
            },
            {
                path: 'projects/:id/update',
                loadComponent: () => import('./features/projects/projects-update/projects-update').then(m => m.ProjectsUpdate)
            },
            {
                path: 'projects/:projectId/tasks/create',
                loadComponent: () => import('./features/tasks/tasks-create/tasks-create').then(m => m.TasksCreate)
            },
            {
                path: 'projects/:id/tasks/:taskId/edit',
                loadComponent: () => import('./features/tasks/tasks-create/tasks-create').then(m => m.TasksCreate)
            },
            
        ]
    },
    {
        path: '',
        redirectTo: 'dashboard',
        pathMatch: 'full',
    }
];
