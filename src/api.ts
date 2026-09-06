import axios from 'axios';
export const api = axios.create({ baseURL: import.meta.env.VITE_API_URL || 'http://localhost:8080/api' });
api.interceptors.request.use(config => { const token = localStorage.getItem('govweb_token'); if (token) config.headers.Authorization = `Bearer ${token}`; return config; });
export type Session = { token: string; id: number; fullName: string; email: string; role: 'CITIZEN'|'OFFICER'|'SUPER_ADMIN' };
export const auth = { login: (data: {email:string;password:string}) => api.post<Session>('/auth/login', data), register: (data: Record<string,string>) => api.post<Session>('/auth/register', data) };
export const getSession = () => { const value = localStorage.getItem('govweb_session'); return value ? JSON.parse(value) as Session : null; };
export const saveSession = (session: Session) => { localStorage.setItem('govweb_token', session.token); localStorage.setItem('govweb_session', JSON.stringify(session)); };
export const clearSession = () => { localStorage.removeItem('govweb_token'); localStorage.removeItem('govweb_session'); };
