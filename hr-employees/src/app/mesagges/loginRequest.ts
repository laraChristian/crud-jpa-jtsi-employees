import { User } from "../domain/user";

export class LoginRequest {

    public user: User;

    constructor() {
        this.user = new User();
    }

}