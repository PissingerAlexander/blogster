import {User} from "../../user/user";

export interface UpdateUserInfoRequestType {
  user: User;
  accessToken: string;
}
