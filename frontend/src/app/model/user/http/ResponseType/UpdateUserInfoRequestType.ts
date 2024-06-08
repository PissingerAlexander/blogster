import {User} from "../../user";


export interface UpdateUserInfoRequestType {
  user: User;
  accessToken: string;
}
