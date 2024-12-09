export interface LoginResponse {
    message: string;
    status: boolean;
    token?: string;
    name?: string;
    role: 'VENDOR' | 'CUSTOMER';
  }