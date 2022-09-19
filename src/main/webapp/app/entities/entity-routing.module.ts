import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'agent',
        data: { pageTitle: 'sefinaApp.agent.home.title' },
        loadChildren: () => import('./agent/agent.module').then(m => m.AgentModule),
      },
      {
        path: 'schedule-execution',
        data: { pageTitle: 'sefinaApp.scheduleExecution.home.title' },
        loadChildren: () => import('./schedule-execution/schedule-execution.module').then(m => m.ScheduleExecutionModule),
      },
      {
        path: 'schedule',
        data: { pageTitle: 'sefinaApp.schedule.home.title' },
        loadChildren: () => import('./schedule/schedule.module').then(m => m.ScheduleModule),
      },
      {
        path: 'company',
        data: { pageTitle: 'sefinaApp.company.home.title' },
        loadChildren: () => import('./company/company.module').then(m => m.CompanyModule),
      },
      {
        path: 'source',
        data: { pageTitle: 'sefinaApp.source.home.title' },
        loadChildren: () => import('./source/source.module').then(m => m.SourceModule),
      },
      {
        path: 'task-status',
        data: { pageTitle: 'sefinaApp.taskStatus.home.title' },
        loadChildren: () => import('./task-status/task-status.module').then(m => m.TaskStatusModule),
      },
      {
        path: 'task',
        data: { pageTitle: 'sefinaApp.task.home.title' },
        loadChildren: () => import('./task/task.module').then(m => m.TaskModule),
      },
      {
        path: 'raw-data',
        data: { pageTitle: 'sefinaApp.rawData.home.title' },
        loadChildren: () => import('./raw-data/raw-data.module').then(m => m.RawDataModule),
      },
      {
        path: 'facility',
        data: { pageTitle: 'sefinaApp.facility.home.title' },
        loadChildren: () => import('./facility/facility.module').then(m => m.FacilityModule),
      },
      {
        path: 'hotel-result-facility',
        data: { pageTitle: 'sefinaApp.hotelResultFacility.home.title' },
        loadChildren: () => import('./hotel-result-facility/hotel-result-facility.module').then(m => m.HotelResultFacilityModule),
      },
      {
        path: 'hotel-result',
        data: { pageTitle: 'sefinaApp.hotelResult.home.title' },
        loadChildren: () => import('./hotel-result/hotel-result.module').then(m => m.HotelResultModule),
      },
      {
        path: 'room',
        data: { pageTitle: 'sefinaApp.room.home.title' },
        loadChildren: () => import('./room/room.module').then(m => m.RoomModule),
      },
      {
        path: 'nfse',
        data: { pageTitle: 'sefinaApp.nfse.home.title' },
        loadChildren: () => import('./nfse/nfse.module').then(m => m.NfseModule),
      },
      {
        path: 'inscricao-cpbs',
        data: { pageTitle: 'sefinaApp.inscricaoCPBS.home.title' },
        loadChildren: () => import('./inscricao-cpbs/inscricao-cpbs.module').then(m => m.InscricaoCPBSModule),
      },
      {
        path: 'atividade-cpbs',
        data: { pageTitle: 'sefinaApp.atividadeCPBS.home.title' },
        loadChildren: () => import('./atividade-cpbs/atividade-cpbs.module').then(m => m.AtividadeCPBSModule),
      },
      {
        path: 'tag',
        data: { pageTitle: 'sefinaApp.tag.home.title' },
        loadChildren: () => import('./tag/tag.module').then(m => m.TagModule),
      },
      {
        path: 'tag-text',
        data: { pageTitle: 'sefinaApp.tagText.home.title' },
        loadChildren: () => import('./tag-text/tag-text.module').then(m => m.TagTextModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
