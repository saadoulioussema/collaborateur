import { TestBed } from '@angular/core/testing';

import { ObjectifService } from './objectif.service';

describe('ObjectifService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: ObjectifService = TestBed.get(ObjectifService);
    expect(service).toBeTruthy();
  });
});
