import { Component, inject } from '@angular/core';
import { TableModule } from 'primeng/table';
import { Tag } from 'primeng/tag';
import { Button } from 'primeng/button';
import { RouterLink } from '@angular/router';
import { AuthService } from '../../core/services/auth.service';
import { UserService } from '../../core/services/user.service';

@Component({
  selector: 'app-users',
  imports: [TableModule, Tag, Button, RouterLink],
  templateUrl: './users.html',
  styleUrl: './users.scss',
})
export class Users {
  private authService = inject(AuthService);
  private userService = inject(UserService);
  users = this.userService.getUsers();

  currentUser = this.authService.currentUserSignal;

  deleteUser(userId: number): void {
    this.userService.delete(userId);
  }
}
