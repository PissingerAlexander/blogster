export interface CreateUserRequest {
  role: string
  username: string
  password: string
  fullName: string | null
  mailAddress: string
}
