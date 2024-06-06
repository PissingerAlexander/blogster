import {User} from "../user/user";

export interface UpdateUserInfoResponse {
  user: User,
  accessToken: string
}
