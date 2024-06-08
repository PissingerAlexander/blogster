import {CustomResponse} from "../CustomResponse";

export interface LoginResponse extends CustomResponse<string> {
  accessToken: string;
}
