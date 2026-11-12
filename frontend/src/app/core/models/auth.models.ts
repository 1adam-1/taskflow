export interface LoginRequest {
  email: string;
  password: string;
}

export interface RegisterRequest {
  firstName: string;
  lastName: string;
  email: string;
  password: string;
}

export interface AuthenticationResponse {
  accessToken: string;
}

export interface UserResponse{
  id: number;
  firstName: string;
  lastName: string;
}