export interface LoginResponse {
    message: string;
    status: boolean;
    token?: string;
    role: 'vendor' | 'customer';
  }