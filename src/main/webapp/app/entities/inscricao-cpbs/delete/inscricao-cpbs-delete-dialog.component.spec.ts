jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { InscricaoCPBSService } from '../service/inscricao-cpbs.service';

import { InscricaoCPBSDeleteDialogComponent } from './inscricao-cpbs-delete-dialog.component';

describe('InscricaoCPBS Management Delete Component', () => {
  let comp: InscricaoCPBSDeleteDialogComponent;
  let fixture: ComponentFixture<InscricaoCPBSDeleteDialogComponent>;
  let service: InscricaoCPBSService;
  let mockActiveModal: NgbActiveModal;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [InscricaoCPBSDeleteDialogComponent],
      providers: [NgbActiveModal],
    })
      .overrideTemplate(InscricaoCPBSDeleteDialogComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(InscricaoCPBSDeleteDialogComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(InscricaoCPBSService);
    mockActiveModal = TestBed.inject(NgbActiveModal);
  });

  describe('confirmDelete', () => {
    it('Should call delete service on confirmDelete', inject(
      [],
      fakeAsync(() => {
        // GIVEN
        jest.spyOn(service, 'delete').mockReturnValue(of(new HttpResponse({ body: {} })));

        // WHEN
        comp.confirmDelete(123);
        tick();

        // THEN
        expect(service.delete).toHaveBeenCalledWith(123);
        expect(mockActiveModal.close).toHaveBeenCalledWith('deleted');
      })
    ));

    it('Should not call delete service on clear', () => {
      // GIVEN
      jest.spyOn(service, 'delete');

      // WHEN
      comp.cancel();

      // THEN
      expect(service.delete).not.toHaveBeenCalled();
      expect(mockActiveModal.close).not.toHaveBeenCalled();
      expect(mockActiveModal.dismiss).toHaveBeenCalled();
    });
  });
});
