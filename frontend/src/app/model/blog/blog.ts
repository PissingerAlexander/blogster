import {User} from "../user/user";

export interface Blog {
  id: number;
  blogName: string;
  owner: User;
  createdAt: Date;
}
