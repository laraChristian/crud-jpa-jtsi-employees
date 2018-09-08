import { Message } from "primeng/components/common/message";
import { MessageService } from "../../../../node_modules/primeng/components/common/messageservice";


export class CommonsUtil {

    msgs: Message[];
    options: {
        weekday: 'long',
        year: 'numeric',
        month: 'short',
        day: 'numeric',
        hour: '2-digit',
        minute: '2-digit'
    }

    constructor() {
        this.clear();
    }

    show(opt: SEVERITY, tittle: string, content: string) {
        this.msgs = [];
        this.msgs.push({ severity: opt.toString(), summary: tittle, detail: content });
    }

    showViaService(messageService: MessageService, level: string, tittle: string, message: string) {
        messageService.add({ severity: level, summary: tittle, detail: message });
        setTimeout(() => {
            this.clear();
        }, 5000);
    }

    clear() {
        this.msgs = [];
    }

    public fillCombos(elementsToLoad: Array<any>, label: string, value: string, itemsToFill: any[]): void {
        console.log('load combo');
        elementsToLoad.forEach(element => {
            itemsToFill.push({ label: element[label], value: element[value] });
        });
    }

    public validateFields(parameter: any, fields: string[]) {
        console.log('start -- validate-fields');
        return fields.every(field => parameter[field] != void 0 && parameter[field] != "");
    }

}

export enum SEVERITY { 'info', 'success', 'warn', 'error' };