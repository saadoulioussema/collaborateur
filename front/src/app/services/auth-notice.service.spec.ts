import { TestBed } from '@angular/core/testing';

import { AuthNoticeService } from './auth-notice.service';

describe('AuthNoticeService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: AuthNoticeService = TestBed.get(AuthNoticeService);
    expect(service).toBeTruthy();
  });
});
