import {User} from "../user/user";
import {Post} from "../post/post";

export interface Comment {
  id: number;
  comment: string;
  author: User;
  post: Post;
  createdAt: Date;
}
