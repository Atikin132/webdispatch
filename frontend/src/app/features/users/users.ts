import { Component, inject } from '@angular/core';
import { User } from '../../core/models/user';
import { USERS } from '../../core/mock/users.mock';
import { TableModule } from 'primeng/table';
import { Tag } from 'primeng/tag';
import { Button } from 'primeng/button';
import { RouterLink } from '@angular/router';
import { AuthService } from '../../core/services/auth-service';

@Component({
  selector: 'app-users',
  imports: [TableModule, Tag, Button, RouterLink],
  templateUrl: './users.html',
  styleUrl: './users.scss',
})
export class Users {
  private authService = inject(AuthService);
  users: User[] = USERS;

  currentUser = this.authService.currentUserSignal;

  //TODO edit user
  // edit(user: User) {}
  //TODO delete user
  // delete(user: User) {}
  //TODO add user
  // add() {}
}
