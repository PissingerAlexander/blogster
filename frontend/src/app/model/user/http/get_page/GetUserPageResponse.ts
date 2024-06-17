import {CustomResponse} from "../../../CustomResponse";
import {GetPage} from "../../../GetPage";
import {User} from "../../user";

export class GetUserPageResponse extends CustomResponse<GetPage<User>>{
}
