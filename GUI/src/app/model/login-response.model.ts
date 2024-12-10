export interface LoginResponse {
    message: string;
    status: boolean;
    token?: string;
    name?: string;
    id?: number;
    role: 'VENDOR' | 'CUSTOMER';
  }