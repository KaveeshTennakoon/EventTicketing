export interface LoginResponse {
    message: string;
    status: boolean;
    token?: string;
    role: 'VENDOR' | 'CUSTOMER';
  }