import { Role } from './role';

export interface User {
  id: number;
  login: string;
  password: string;
  name: string;
  birthDate?: string;
  age: number;
  salary: number;
  roles: Role[];
}
