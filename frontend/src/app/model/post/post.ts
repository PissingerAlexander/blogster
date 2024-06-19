import {Blog} from "../blog/blog";

export interface Post {
  id: number;
  postTitle: string;
  blog: Blog;
  content: string;
  createdAt: Date;
  trackId: string;
  trackName: string;
}
