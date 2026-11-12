import { Component, inject, signal } from '@angular/core';
import { AuthService } from '../../core/services/auth';
import { Router, RouterLink } from '@angular/router';
import { NotificationService } from '../../core/services/notification/notification';
import { UserResponse } from '../../core/models/auth.models';
import { Notification as NotificationModel } from '../../core/models/notification.model';
import { DatePipe } from '@angular/common';

@Component({
  selector: 'app-navbar',
  imports: [
    RouterLink,
    DatePipe
  ],
  templateUrl: './navbar.html',
  styleUrl: './navbar.css',
})
export class Navbar {
  private authService = inject(AuthService);
  private router = inject(Router);
  private notifService = inject(NotificationService);

  notifications = signal<NotificationModel[]>([]);
  showNotifications = signal(false);
  currentUser: UserResponse | null = null;

  ngOnInit() {
    this.getCurrentUser();
    this.getNotifications();
  }

  getCurrentUser(){
    this.authService.getCurrentUser().subscribe({
      next:(user) =>{
        this.currentUser = user;
        this.getNotifications();
      },
      error:(error) =>{
        console.error('Error fetching current user:', error);
      }
    })
  }

 getNotifications(){
    if (this.currentUser) {
      this.notifService.getNotifications(this.currentUser.id).subscribe({
        next: (notifications) => {
          this.notifications.set(notifications);
          this.showNotifications.set(true);
        },
        error: (error) => {
          console.error('Error fetching notifications:', error);
        }
      })
    }
  }

  toggleNotifications() {
    this.showNotifications.update((value) => !value);
  }

  Logout(): void {
    this.authService.logout();
    this.router.navigate(['/login']);
  }

  get countUnreadNotifications(): number{
    return this.notifications().filter(notification => !notification.read).length;
  }

  markAsRead(notification: NotificationModel){
    this.notifService.markAsRead(notification.id).subscribe({
      next: () => {
        this.notifications.update(notifications => 
          notifications.map(n => 
            n.id ===notification.id ? {...n,read:true} : n
          )
        )
      },
      error: (error) => {
        console.error('Error marking notification as read:', error);
      }
    })
  }


}
