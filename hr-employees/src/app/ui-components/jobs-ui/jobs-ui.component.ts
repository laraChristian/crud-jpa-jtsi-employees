import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { JobsModel } from './jobs.model';
import { AdministrativeApiService } from '../../services/administrative-api.service';
import { CommonsUtil } from '../ui-utilities/commons-util';
import { MessageService } from '../../../../node_modules/primeng/components/common/messageservice';
import { JobRequest } from '../../mesagges/jobRequest';
import { JobDTO } from '../../domain/jobDTO';
import { ConfirmationService } from '../../../../node_modules/primeng/api';

@Component({
  selector: 'jobs-ui',
  templateUrl: './jobs-ui.component.html',
  styleUrls: ['./jobs-ui.component.css'],
  providers: [AdministrativeApiService, ConfirmationService],
  encapsulation: ViewEncapsulation.None
})
export class JobsUiComponent extends CommonsUtil implements OnInit {

  private _model: JobsModel;

  constructor(private _jobService: AdministrativeApiService, private _messageService: MessageService, private confirmationService: ConfirmationService) {
    super();
    this._model = new JobsModel();
  }

  ngOnInit() {
    this.listAllJobs();
  }

  //#region  Data-table
  public listAllJobs(): void {
    console.log('start -- list-all-jobs method')
    this._jobService.listJobs().subscribe(
      resp => {
        if (resp.success == true) {
          this._model.response.jobsDTO = resp.jobsDTO;
        } else {
          console.log('[ERROR] ' + resp.message);
        }
      },
      error => console.log('[ERROR] ' + error),
      () => console.log('end -- list-all-jobs method')
    );
  }
  //#endregion

  //#region core
  public create(): void {
    if (this.validateFields(this._model.request.jobDTO, ["jobId", "minSalary", "jobTitle", "maxSalary"]) == true) {
      if (this._model.isLessThan(this._model.request.jobDTO.minSalary, this._model.request.jobDTO.maxSalary) == true) {
        this._jobService.createJob(this._model.request).subscribe(
          resp => {
            if (resp.success == true) {
              this.showViaService(this._messageService, 'success', 'Attention', resp.message.toString());
              this._model.clean();
              this.listAllJobs();
            } else {
              this.showViaService(this._messageService, 'error', 'Attention', resp.message.toString());
            }
          },
          error => console.log("[ERROR] " + error),
          () => console.log('end -- create-method')
        );
      } else {
        this.showViaService(this._messageService, 'error', 'Attention', 'The min salary not is less than max salary');
      }
    }else{
      this.showViaService(this._messageService, 'error', 'Attention', 'Empty fields aren\'t allowed');
    }
  }

  confirm(jobDTO: JobDTO) {
    console.log(jobDTO)
    this.confirmationService.confirm({
      message: 'Are you sure that you want to perform this action?',
      accept: () => {
        this._model.request.jobDTO = jobDTO;
        this.delete(this._model.request);
      },
      reject: () => {
        console.log('reject')
      }
    });
  }

  private delete(request: JobRequest): void {
    console.log('start -- delete method');
    this._jobService.deleteJob(request).subscribe(
      resp => {
        if (resp.success == true) {
          this.showViaService(this._messageService, 'success', 'Atention', resp.message.toString());
          this.listAllJobs();
          this._model.clean();
        } else {
          this.showViaService(this._messageService, 'warn', 'Atention', resp.message.toString());
        }
      },
      error => console.log('[ERROR] ' + error),
      () => console.log('end -- delete method')
    );
  }

  //#endregion

  onRowSelect(): void {
    this._model.request.update = true;
  }

}
