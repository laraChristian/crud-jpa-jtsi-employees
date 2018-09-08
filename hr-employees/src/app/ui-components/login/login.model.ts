import { LoginRequest } from "../../mesagges/loginRequest";
import { LoginResponse } from "../../mesagges/loginResponse";
import { CommonsUtil } from "../ui-utilities/commons-util";

export class LoginModel extends CommonsUtil{

    public _loggedIn: boolean;
    public _loginRequest: LoginRequest;
    public _loginResponse: LoginResponse;
    public feedback: boolean;

    constructor() {
        super();
        this._loginResponse = new LoginResponse();
        this.clean();
        this._loggedIn = false;
        this.feedback = false;
    }

    public clean():void{
        this._loginRequest = new LoginRequest();
    }
}