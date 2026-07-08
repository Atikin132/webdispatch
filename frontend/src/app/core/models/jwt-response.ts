export interface JWTResponse {
  token: string;
  id: number;
  login: string;
  roles: string[];
}
