import { Component } from '@angular/core';
import { User } from '../../core/models/user';
import { USERS } from './mock/users.mock';
import { TableModule } from 'primeng/table';
import { Tag } from 'primeng/tag';
import { Button } from 'primeng/button';

@Component({
  selector: 'app-users',
  imports: [TableModule, Tag, Button],
  templateUrl: './users.html',
  styleUrl: './users.scss',
})
export class Users {
  users: User[] = USERS;

  currentUser = 'admin1';

  //TODO edit user
  // edit(user: User) {}
  //TODO delete user
  // delete(user: User) {}
  //TODO add user
  // add() {}
}
