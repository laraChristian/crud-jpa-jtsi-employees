import { User } from "../domain/user";

export class LoginResponse {

    public loggedIn: boolean;
    public message: string;
    public user: User;

    constructor(){
        this.loggedIn = false;
    }
}