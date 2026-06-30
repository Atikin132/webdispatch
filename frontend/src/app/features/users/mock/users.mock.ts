import { User } from '../../../core/models/user';
import { Role } from '../../../core/models/role';

const ROLES: Role[] = [
  { id: 1, name: 'Administrator' },
  { id: 2, name: 'Manager' },
  { id: 3, name: 'Bookkeeper' },
  { id: 4, name: 'Developer' },
  { id: 5, name: 'Designer' },
];

export const USERS: User[] = [
  {
    id: 1,
    login: 'u1',
    password: '111111',
    name: 'Peter',
    birthDate: '1990-01-01',
    age: 36,
    salary: 3000,
    roles: [ROLES[1], ROLES[3]],
  },
  {
    id: 2,
    login: 'u2',
    password: '222222',
    name: 'Ivan',
    birthDate: '2004-06-18',
    age: 22,
    salary: 5000,
    roles: [ROLES[1]],
  },
  {
    id: 3,
    login: 'u3',
    password: '333333',
    name: 'Vasili',
    birthDate: '1984-05-09',
    age: 42,
    salary: 2000,
    roles: [ROLES[2]],
  },
  {
    id: 4,
    login: 'admin1',
    password: 'admin1',
    name: 'Admin',
    birthDate: '2000-01-01',
    age: 26,
    salary: 8000,
    roles: [ROLES[0], ROLES[1], ROLES[2], ROLES[3], ROLES[4]],
  },
  {
    id: 5,
    login: 'admin2',
    password: 'admin2',
    name: 'Anton',
    birthDate: '1999-06-09',
    age: 27,
    salary: 8000,
    roles: [ROLES[0]],
  },
];
