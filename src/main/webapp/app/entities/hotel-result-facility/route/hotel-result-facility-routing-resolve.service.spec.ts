import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IHotelResultFacility } from '../hotel-result-facility.model';
import { HotelResultFacilityService } from '../service/hotel-result-facility.service';

import { HotelResultFacilityRoutingResolveService } from './hotel-result-facility-routing-resolve.service';

describe('HotelResultFacility routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: HotelResultFacilityRoutingResolveService;
  let service: HotelResultFacilityService;
  let resultHotelResultFacility: IHotelResultFacility | null | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            snapshot: {
              paramMap: convertToParamMap({}),
            },
          },
        },
      ],
    });
    mockRouter = TestBed.inject(Router);
    jest.spyOn(mockRouter, 'navigate').mockImplementation(() => Promise.resolve(true));
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRoute).snapshot;
    routingResolveService = TestBed.inject(HotelResultFacilityRoutingResolveService);
    service = TestBed.inject(HotelResultFacilityService);
    resultHotelResultFacility = undefined;
  });

  describe('resolve', () => {
    it('should return IHotelResultFacility returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultHotelResultFacility = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultHotelResultFacility).toEqual({ id: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultHotelResultFacility = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultHotelResultFacility).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<IHotelResultFacility>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultHotelResultFacility = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultHotelResultFacility).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
