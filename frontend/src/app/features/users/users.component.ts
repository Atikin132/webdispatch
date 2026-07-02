import { Component, inject } from '@angular/core';
import { TableModule } from 'primeng/table';
import { Tag } from 'primeng/tag';
import { Button } from 'primeng/button';
import { RouterLink } from '@angular/router';
import { AuthService } from '../../core/services/auth.service';
import { UserService } from '../../core/services/user.service';
import { TranslatePipe } from '@ngx-translate/core';

@Component({
  selector: 'app-users',
  imports: [TableModule, Tag, Button, RouterLink, TranslatePipe],
  templateUrl: './users.component.html',
  styleUrl: './users.component.scss',
})
export class UsersComponent {
  private authService = inject(AuthService);
  private userService = inject(UserService);
  users = this.userService.getUsers();

  currentUser = this.authService.currentUser;

  deleteUser(userId: number): void {
    this.userService.delete(userId);
  }
}
